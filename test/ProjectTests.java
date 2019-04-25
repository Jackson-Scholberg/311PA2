import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ProjectTests {

    CommunicationsMonitor monitor;
    CommunicationTriple one;

    @Before
    public void initEmpty() {
        monitor = new CommunicationsMonitor();
        one = new CommunicationTriple(1, 2, 101);
    }

    @Before
    public void initFull() {
        initEmpty();
        monitor.addCommunication(1, 2, 156);
        monitor.addCommunication(1, 2, 104);
        monitor.addCommunication(1, 2, 203);
        monitor.addCommunication(1, 2, 336);
        monitor.createGraph();
    }

    @Test
    public void checkOrder() {
        initFull();
        one = monitor.getCommunicationList().iterator().next();
        assertEquals(104, one.getTk());
    }

    @Test
    public void blockConstruct() {
        initEmpty();
        monitor.addCommunication(1, 2, 156);
        monitor.createGraph();
        monitor.addCommunication(1, 2, 104);
        one = monitor.getCommunicationList().iterator().next();
        assertEquals(156, one.getTk());
    }

    @Test
    public void mapExists() {
        initFull();
        assertNotNull(monitor.getComputerMapping());
    }

    @Test
    public void listExists() {
        initFull();
        HashMap<Integer, List<ComputerNode>> map = monitor.getComputerMapping();
        assertNotNull(map.get(new Integer(1)));
    }

    @Test
    public void nodeOneExists() {
        initFull();
        List<ComputerNode> nodeOneList = monitor.getComputerMapping().get(new Integer(1));
        LinkedList<ComputerNode> a = (LinkedList<ComputerNode>) nodeOneList;
        assertNotNull(a.peek());
//System.out.println(a.peek());
//System.out.println(monitor.Ledger.get(0));
    }

    @Test
    public void assertConnEdge() {
        initFull();
        List<ComputerNode> nodeOne = monitor.getComputerMapping().get(new Integer(1));
        ComputerNode a = ((LinkedList<ComputerNode>) nodeOne).peek();
        assertEquals(104, a.getTimestamp());
        ComputerNode b = a.getOutNeighbors().get(0);
        assertEquals(2, b.getID());
    }


    @Before
    public void makeCustom1() {
        initEmpty();
        monitor.addCommunication(1, 3, 12);
        monitor.addCommunication(2, 1, 10);
        monitor.addCommunication(1, 2, 1);
        monitor.addCommunication(1, 2, 5);
        monitor.addCommunication(2, 1, 3);
        monitor.createGraph();
    }

    @Test
    public void testFirstE(){
        makeCustom1();
        List<ComputerNode> VirusPath;
        VirusPath = monitor.queryInfection(1, 3, 0, 12); //time before
        assertEquals(1, VirusPath.get(0).getTimestamp());
        VirusPath = monitor.queryInfection(1, 3, 1, 13); //At
        assertEquals(1, VirusPath.get(0).getTimestamp());
        VirusPath = monitor.queryInfection(1, 3, 2, 13); //During
        assertEquals(3, VirusPath.get(0).getTimestamp());


        VirusPath = monitor.queryInfection(4, 3, 1, 12); //Inexistent node 1
        assertNull(VirusPath);
        VirusPath = monitor.queryInfection(3, 4, 1, 12); //Inexistent node 2
        assertNull(VirusPath);

        VirusPath = monitor.queryInfection(3, 1, 2, 100); //Long
        assertEquals(12, VirusPath.get(0).getTimestamp());

        VirusPath = monitor.queryInfection(1, 3, 9, 100); //Various Iters
        assertEquals(10, VirusPath.get(0).getTimestamp());
    }

    @Test
    public void oneCommunicationTriple() {
        initEmpty();
        monitor.addCommunication(1, 2, 101);
        monitor.addCommunication(1, 2, 99);
        monitor.createGraph();
        List<ComputerNode> nodeOneList = monitor.queryInfection(1, 1, 100, 102);
        assertTrue(!nodeOneList.isEmpty());
        assertEquals(1, nodeOneList.size());
        assertEquals(101, nodeOneList.get(0).getTimestamp());
        nodeOneList = monitor.queryInfection(1, 1, 101, 101);
        assertTrue(!nodeOneList.isEmpty());
        assertEquals(1, nodeOneList.size());
        assertEquals(101, nodeOneList.get(0).getTimestamp());
        nodeOneList = monitor.queryInfection(1, 1, 101, 103);
        assertTrue(!nodeOneList.isEmpty());
        assertEquals(1, nodeOneList.size());
        assertEquals(101, nodeOneList.get(0).getTimestamp());
    }

    @Test
    public void variousSimultaneous() {
        initEmpty();
        monitor.addCommunication(1, 2, 100);
        monitor.addCommunication(3, 5, 100);
        monitor.addCommunication(1, 4, 100);
        monitor.addCommunication(1, 5, 100);
        monitor.addCommunication(4, 6, 100);
        monitor.addCommunication(7, 6, 100);
        monitor.addCommunication(8, 9, 100);
        monitor.createGraph();
        List<ComputerNode> nodeOneList;

//Nodes 1 - 7 are all connected in between them
        for (int i = 1; i < 8; i++) {
            for (int j = 1; j < 8; j++) {
                nodeOneList = monitor.queryInfection(i, j, 100, 100);
                assertTrue(!nodeOneList.isEmpty());
            }
        }
//Nodes 1 - 7 are not connected to neither 8 or 9
        for (int j = 1; j < 8; j++) {
            nodeOneList = monitor.queryInfection(8, j, 100, 100);
            assertNull(nodeOneList);
            nodeOneList = monitor.queryInfection(9, j, 100, 100);
            assertNull(nodeOneList);

            nodeOneList = monitor.queryInfection(j, 8, 100, 100);
            assertNull(nodeOneList);
            nodeOneList = monitor.queryInfection(j, 9, 100, 100);
            assertNull(nodeOneList);
        }
        nodeOneList = monitor.queryInfection(8, 9, 100, 100);
        assertTrue(!nodeOneList.isEmpty());

        nodeOneList = monitor.queryInfection(9, 8, 100, 100);
        assertTrue(!nodeOneList.isEmpty());
    }
}