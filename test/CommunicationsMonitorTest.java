import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class CommunicationsMonitorTest {

    // Instance variables
    CommunicationsMonitor monitor;

    @Before
    public void initialize() {
        // Start each test with an empty CommunicationsMonitor
        monitor = new CommunicationsMonitor();
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
    public void createGraphExampleOne() {
        // Create graph from example 1
        monitor = createExampleOne();

        // Test that array was sorted
        List<CommunicationTriple> commList =
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

        // Test C1 HashMap
        List<ComputerNode> c1Mapping = monitor.getComputerMapping(1);
        assertEquals(2, c1Mapping.size());
        ComputerNode c1Four = c1Mapping.get(0);
        ComputerNode c1Twelve = c1Mapping.get(1);
        assertEquals(1, c1Four.getID());
        assertEquals(4, c1Four.getTimestamp());
        assertEquals(1, c1Twelve.getID());
        assertEquals(12, c1Twelve.getTimestamp());

        // Test (C1, 4) Neighbors
        assertEquals(2, c1Four.getOutNeighbors().size());
        assertEquals(2, c1Four.getOutNeighbors().get(0).getID());
        assertEquals(4, c1Four.getOutNeighbors().get(0).getTimestamp());
        assertEquals(1, c1Four.getOutNeighbors().get(1).getID());
        assertEquals(12, c1Four.getOutNeighbors().get(1).getTimestamp());

        // Test (C1, 12) Neighbors
        assertEquals(1, c1Twelve.getOutNeighbors().size());
        assertEquals(4, c1Twelve.getOutNeighbors().get(0).getID());
        assertEquals(12, c1Twelve.getOutNeighbors().get(0).getTimestamp());

        // Test C2 HashMap
        List<ComputerNode> c2Mapping = monitor.getComputerMapping(2);
        assertEquals(2, c2Mapping.size());
        ComputerNode c2Four = c2Mapping.get(0);
        ComputerNode c2Eight = c2Mapping.get(1);
        assertEquals(2, c2Four.getID());
        assertEquals(4, c2Four.getTimestamp());
        assertEquals(2, c2Eight.getID());
        assertEquals(8, c2Eight.getTimestamp());

        // Test (C2, 4) Neighbors
        assertEquals(2, c2Four.getOutNeighbors().size());
        assertEquals(1, c2Four.getOutNeighbors().get(0).getID());
        assertEquals(4, c2Four.getOutNeighbors().get(0).getTimestamp());
        assertEquals(2, c2Four.getOutNeighbors().get(1).getID());
        assertEquals(8, c2Four.getOutNeighbors().get(1).getTimestamp());

        // Test (C2, 8) Neighbors
        assertEquals(1, c2Eight.getOutNeighbors().size());
        assertEquals(4, c2Eight.getOutNeighbors().get(0).getID());
        assertEquals(8, c2Eight.getOutNeighbors().get(0).getTimestamp());

        // Test C3 HashMap
        List<ComputerNode> c3Mapping = monitor.getComputerMapping(3);
        assertEquals(1, c3Mapping.size());
        ComputerNode c3Eight = c3Mapping.get(0);
        assertEquals(3, c3Eight.getID());
        assertEquals(8, c3Eight.getTimestamp());

        // Test (C3, 8) Neighbors
        assertEquals(1, c3Eight.getOutNeighbors().size());
        assertEquals(4, c3Eight.getOutNeighbors().get(0).getID());
        assertEquals(8, c3Eight.getOutNeighbors().get(0).getTimestamp());

        // Test C4 HashMap
        List<ComputerNode> c4Mapping = monitor.getComputerMapping(4);
        assertEquals(2, c4Mapping.size());
        ComputerNode c4Eight = c4Mapping.get(0);
        ComputerNode c4Twelve = c4Mapping.get(1);
        assertEquals(4, c4Eight.getID());
        assertEquals(8, c4Eight.getTimestamp());
        assertEquals(4, c4Twelve.getID());
        assertEquals(12, c4Twelve.getTimestamp());

        // Test (C4, 8) Neighbors
        assertEquals(3, c4Eight.getOutNeighbors().size());
        assertEquals(2, c4Eight.getOutNeighbors().get(0).getID());
        assertEquals(8, c4Eight.getOutNeighbors().get(0).getTimestamp());
        assertEquals(3, c4Eight.getOutNeighbors().get(1).getID());
        assertEquals(8, c4Eight.getOutNeighbors().get(1).getTimestamp());
        assertEquals(4, c4Eight.getOutNeighbors().get(2).getID());
        assertEquals(12, c4Eight.getOutNeighbors().get(2).getTimestamp());

        // Test (C4, 12) Neighbors
        assertEquals(1, c4Twelve.getOutNeighbors().size());
        assertEquals(1, c4Twelve.getOutNeighbors().get(0).getID());
        assertEquals(12, c4Twelve.getOutNeighbors().get(0).getTimestamp());
    }

    @Test
    public void queryInfectionExampleOne() {
        // Create Example 1 graph
        monitor = createExampleOne();

        // Test that C3 gets infected at time = 8 if C1 is infected at time = 2
        List<ComputerNode> infectedList = monitor.queryInfection(1, 3, 2, 8);
        assertEquals(5, infectedList.size());
        assertEquals(1, infectedList.get(0).getID());
        assertEquals(4, infectedList.get(0).getTimestamp());
        assertEquals(2, infectedList.get(1).getID());
        assertEquals(4, infectedList.get(1).getTimestamp());
        assertEquals(2, infectedList.get(2).getID());
        assertEquals(8, infectedList.get(2).getTimestamp());
        assertEquals(4, infectedList.get(3).getID());
        assertEquals(8, infectedList.get(3).getTimestamp());
        assertEquals(3, infectedList.get(4).getID());
        assertEquals(8, infectedList.get(4).getTimestamp());
    }

    @Test
    public void queryInfectionExampleTwo() {
        // Create Example 2 graph
        monitor = createExampleTwo();

        // If C1 is infected at time = 2, C3 does not get infected in the time
        // observed...
        List<ComputerNode> infectedList = monitor.queryInfection(1, 3, 2, 15);
        assertEquals(null, infectedList);

        // But C2 does
        infectedList = monitor.queryInfection(1, 2, 2, 15);
        assertEquals(3, infectedList.size());
        assertEquals(1, infectedList.get(0).getID());
        assertEquals(12, infectedList.get(0).getTimestamp());
        assertEquals(1, infectedList.get(1).getID());
        assertEquals(14, infectedList.get(1).getTimestamp());
        assertEquals(2, infectedList.get(2).getID());
        assertEquals(14, infectedList.get(2).getTimestamp());
    }

    @Test
    public void getComputerMapping() {
        // Test that empty HashMap is initialized on object creation
        assertEquals(0, monitor.getComputerMapping().size());

        // Insert tuple and create graph
        monitor.addCommunication(1, 2, 4);
        monitor.createGraph();

        // Test that mapping has been updated
        assertEquals(2, monitor.getComputerMapping().size());
    }

    @Test
    public void getComputerMapping1() {
        // Invalid key value should return null list
        assertEquals(null, monitor.getComputerMapping(1));

        // Insert tuple and create graph
        monitor.addCommunication(1, 2, 4);
        monitor.createGraph();

        // Test that mapping has been updated
        assertEquals(1, monitor.getComputerMapping(1).size());
        assertEquals(null, monitor.getComputerMapping(3));
    }

    @Test
    public void queryInfectionBeforeCreateGraph() {
        monitor.addCommunication(2, 4, 8);
        monitor.addCommunication(1, 4, 12);
        monitor.addCommunication(1, 2, 4);
        monitor.addCommunication(3, 4, 8);
        assertEquals(null, monitor.queryInfection(1, 3, 2, 8));
    }

    //--------------------------------------------------------------------------
    // Helper Methods
    //--------------------------------------------------------------------------

    /**
     * Creates and returns the CommunicationsMonitor from Example 1 in the PDF
     * @return Example 1 CommunicationsMonitor
     */
    private CommunicationsMonitor createExampleOne() {
        CommunicationsMonitor example1 = new CommunicationsMonitor();
        example1.addCommunication(2, 4, 8);
        example1.addCommunication(1, 4, 12);
        example1.addCommunication(1, 2, 4);
        example1.addCommunication(3, 4, 8);
        example1.createGraph();
        return example1;
    }

    /**
     * Creates and returns the CommunicationsMonitor from Example 2 in the PDF
     * @return Example 2 CommunicationsMonitor
     */
    private CommunicationsMonitor createExampleTwo() {
        CommunicationsMonitor example2 = new CommunicationsMonitor();
        example2.addCommunication(2, 3, 8);
        example2.addCommunication(1, 4, 12);
        example2.addCommunication(1, 2, 14);
        example2.createGraph();
        return example2;
    }
}