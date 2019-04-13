import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CommunicationsMonitorTest {

    // Instance variables
    CommunicationsMonitor monitor;

    @Before
    public void initialize() {
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
    public void createGraph() {
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