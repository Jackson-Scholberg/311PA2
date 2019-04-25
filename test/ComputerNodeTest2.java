import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ComputerNodeTest2 {

    @Test
    public void checkID() {
        ComputerNode computerNode = new ComputerNode(3, 5);
        assertEquals(3, computerNode.getID());
    }

    @Test
    public void checkTimestamp() {
        ComputerNode computerNode = new ComputerNode(3, 5);
        assertEquals(5, computerNode.getTimestamp());
    }

    @Test
    public void equal() {

        ComputerNode one = new ComputerNode(3, 5);
        ComputerNode two = new ComputerNode(2, 4);
        ComputerNode three = new ComputerNode(3, 4);

        assertNotEquals(one, two);
        assertNotEquals(one, three);
        assertNotEquals(two, three);


        ComputerNode oneClone = new ComputerNode(3, 5);
        assertEquals(one, oneClone);
    }

    @Test
    public void neighborsTest(){

        ComputerNode node = new ComputerNode(1,1);
        ComputerNode node1 = new ComputerNode(1,1);
        ComputerNode node2 = new ComputerNode(1,1);

        node.addNeighbor(node1);
        node.addNeighbor(node2);

        List<ComputerNode> nodeList = node.getOutNeighbors();

        assertEquals(2,nodeList.size());
        assertTrue(nodeList.contains(node1));
        assertTrue(nodeList.contains(node2));
    }
}