import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

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
}