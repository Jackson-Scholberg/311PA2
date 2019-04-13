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


    }
}