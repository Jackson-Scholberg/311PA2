import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommunicationsMonitorTest {

    // Instance variables
    CommunicationsMonitor monitor;
    CommunicationTriple comm1;
    CommunicationTriple comm2;
    CommunicationTriple comm3;
    CommunicationTriple comm4;

    @Before
    public void initialize() {
        monitor = new CommunicationsMonitor();
        comm1 = new CommunicationTriple(1, 2, 4);
        comm2 = new CommunicationTriple(2, 4, 8);
        comm3 = new CommunicationTriple(3, 4, 8);
        comm4 = new CommunicationTriple(1, 4, 12);
    }

    @Test
    public void addCommunication() {
        // Initial size should be zero
        assertEquals(0, monitor.getCommunicationList().size());

        // Insert a tuple
        monitor.addCommunication(1, 2, 3);
        assertEquals(1, monitor.getCommunicationList().size());

        // Shouldn't work after createGraph() is called
        monitor.createGraph();
        monitor.addCommunication(5, 6, 7);
        assertEquals(1, monitor.getCommunicationList().size());
    }

    @Test
    public void createGraph() {

        // Test that the list gets sorted
        monitor.addCommunication(2, 4, 8);
        monitor.addCommunication(1, 4, 12);
        monitor.addCommunication(1, 2, 4);
        monitor.addCommunication(3, 4, 8);
        monitor.createGraph();

        // Test that array was sorted
        ArrayList<CommunicationTriple> commList =
                monitor.getCommunicationList();
        assertEquals(4, commList.get(0).getTk());
        assertEquals(8, commList.get(1).getTk());
        assertEquals(8, commList.get(2).getTk());
        assertEquals(12, commList.get(3).getTk());

        // Test that all computers are initialized in HashMap
        assertTrue(monitor.getComputerMapping(1) != null);
        assertTrue(monitor.getComputerMapping(2) != null);
        assertTrue(monitor.getComputerMapping(3) != null);
        assertTrue(monitor.getComputerMapping(4) != null);
        assertTrue(monitor.getComputerMapping(5) == null);

        // Test C1 Communications
        List<ComputerNode> c1Mapping = monitor.getComputerMapping(1);
        assertEquals(2, c1Mapping.size());
        ComputerNode c1Four = c1Mapping.get(0);
        ComputerNode c1Twelve = c1Mapping.get(1);
        assertEquals(1, c1Four.getID());
        assertEquals(4, c1Four.getTimestamp());
        assertEquals(1, c1Twelve.getID());
        assertEquals(12, c1Twelve.getTimestamp());

        // Test C1 Neighbors
        assertEquals(2, c1Four.getOutNeighbors().size());
        assertEquals(2, c1Four.getOutNeighbors().get(0).getID());
        assertEquals(4, c1Four.getOutNeighbors().get(0).getTimestamp());
        assertEquals(1, c1Four.getOutNeighbors().get(1).getID());
        assertEquals(12,
                c1Four.getOutNeighbors().get(1).getTimestamp());

        // Test C2 Communications
        List<ComputerNode> c2Mapping = monitor.getComputerMapping(2);
        assertEquals(2, c2Mapping.size());
        ComputerNode c2Four = c2Mapping.get(0);
        ComputerNode c2Eight = c2Mapping.get(1);
        assertEquals(2, c2Four.getID());
        assertEquals(4, c2Four.getTimestamp());
        assertEquals(2, c2Eight.getID());
        assertEquals(8, c2Eight.getTimestamp());

        // Test C2 Neighbors
        assertEquals(2, c2Four.getOutNeighbors().size());
        assertEquals(1, c2Four.getOutNeighbors().get(0).getID());
        assertEquals(4, c2Four.getOutNeighbors().get(0).getTimestamp());
        assertEquals(2, c2Four.getOutNeighbors().get(1).getID());
//        assertEquals(8,
//                c1Four.getOutNeighbors().get(1).getTimestamp());

        // Test C3 Communications
        List<ComputerNode> c3Mapping = monitor.getComputerMapping(3);
        assertEquals(1, c3Mapping.size());
        ComputerNode c3Eight = c3Mapping.get(0);
        assertEquals(3, c3Eight.getID());
        assertEquals(8, c3Eight.getTimestamp());

        // Test C3 Neighbors
        assertEquals(1, c3Eight.getOutNeighbors().size());
        assertEquals(4, c3Eight.getOutNeighbors().get(0).getID());
        assertEquals(8, c3Eight.getOutNeighbors().get(0).getTimestamp());

        // Test C4 Communications
        List<ComputerNode> c4Mapping = monitor.getComputerMapping(4);
//        assertEquals(2, c4Mapping.size());
        ComputerNode c4Eight = c4Mapping.get(0);
        ComputerNode c4Twelve = c4Mapping.get(1);
        assertEquals(4, c4Eight.getID());
        assertEquals(8, c4Eight.getTimestamp());
//        assertEquals(4, c4Twelve);
//        assertEquals(12, c4Twelve);

        // Test C4 Neighbors
//        assertEquals(3, c4Eight.getOutNeighbors().size());
        assertEquals(2, c4Eight.getOutNeighbors().get(0).getID());
        assertEquals(8, c4Eight.getOutNeighbors().get(0).getTimestamp());
//        assertEquals(3, c4Eight.getOutNeighbors().get(1).getID());
        assertEquals(8, c4Eight.getOutNeighbors().get(1).getTimestamp());
//        assertEquals(4, c4Eight.getOutNeighbors().get(2).getID());
//        assertEquals(12, c4Eight.getOutNeighbors().get(2).getTimestamp());




    }

    @Test
    public void queryInfection() {
    }

    @Test
    public void getComputerMapping() {
    }

    @Test
    public void getComputerMapping1() {
    }

    @Test
    public void DFS(){
        monitor.addCommunication(1,2,4);
        monitor.addCommunication(2,4,8);
        monitor.addCommunication(3,4,8);
        monitor.addCommunication(1,4,12);
        monitor.createGraph();

        monitor.DFS(monitor.getComputerMapping());

        for (List<ComputerNode> list : monitor.getComputerMapping().values()){
            for(ComputerNode node : list){
                assertEquals(Color.BLACK, node.getColor());
            }
        }
    }
}