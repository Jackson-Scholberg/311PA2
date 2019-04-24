import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CommunicationsMonitorTest3 {
    CommunicationsMonitor cm;

    @Before
    public void setCm() {
        cm = new CommunicationsMonitor();
    }

    /**
     * This is example 1 form the project description
     */
    @Test
    public void example1() {
        CommunicationsMonitor cm = new CommunicationsMonitor();
        cm.addCommunication(1, 2, 4);
        cm.addCommunication(2, 4, 8);
        cm.addCommunication(3, 4, 8);
        cm.addCommunication(1, 4, 12);
        cm.createGraph();


        assertEquals(3, getLast(cm.queryInfection(1, 3, 2, 8)).getID());

    }
    /**
     * This is example 1 form the project description
     */
    @Test
    public void example1CheekGraph() {
        CommunicationsMonitor cm = new CommunicationsMonitor();
        cm.addCommunication(1, 2, 4);
        cm.addCommunication(2, 4, 8);
        cm.addCommunication(3, 4, 8);
        cm.addCommunication(1, 4, 12);
        cm.createGraph();

        HashMap<Integer, List<ComputerNode>> graph = cm.getComputerMapping();


        ComputerNode one = new ComputerNode(1,4);
        ComputerNode two = new ComputerNode(2,4);
        ComputerNode three = new ComputerNode(2,8);
        ComputerNode four = new ComputerNode(4,8);
        ComputerNode five = new ComputerNode(3,8);
        ComputerNode six = new ComputerNode(1,12);
        ComputerNode seven = new ComputerNode(4,12);
        one.addNeighbor(two);
        one.addNeighbor(six);


        two.addNeighbor(one);
        two.addNeighbor(three);

        three.addNeighbor(four);

        four.addNeighbor(three);
        four.addNeighbor(five);
        four.addNeighbor(seven);

        five.addNeighbor(four);

        six.addNeighbor(seven);
        seven.addNeighbor(six);


        List<ComputerNode> c1 = graph.get(1);
        List<ComputerNode> c2 = graph.get(2);
        List<ComputerNode> c3 = graph.get(3);
        List<ComputerNode> c4 = graph.get(4);

        assertEquals(2, c1.size());
        assertEquals(2, c2.size());
        assertEquals(1, c3.size());
        assertEquals(2, c4.size());

        assertEquals(c1.get(0), one);
        assertEquals(c1.get(1), six);
        assertEquals(c2.get(0), two);
        assertEquals(c2.get(1), three);
        assertEquals(c3.get(0), five);
        assertEquals(c4.get(0), four);
        assertEquals(c4.get(1), seven);


    }

    @Test
    public void example2() {
        cm.addCommunication(2, 3, 8);
        cm.addCommunication(1, 4, 12);
        cm.addCommunication(1, 2, 14);
        assertEquals(null, cm.queryInfection(1, 3, 2, 14));

    }


    @Test
    public void temp() {
        cm.addCommunication(2, 3, 8);
        cm.addCommunication(1, 4, 12);
        cm.addCommunication(1, 2, 14);
        cm.createGraph();

        List<ComputerNode> temp = cm.queryInfection(1, 3, 2, 8);
    }

    @Test
    public void InfectAfterLastCommunication() {
        cm.addCommunication(1, 2, 4);
        cm.createGraph();
        assertEquals(null, cm.queryInfection(1, 2, 5, 5));
    }

    private ComputerNode getLast(List<ComputerNode> computerNodes) {
        return computerNodes.get(computerNodes.size() - 1);
    }
}