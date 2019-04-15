import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComputerNodeTest {

    // Instance variables
    ComputerNode node;

    @Before
    public void initialize() {
        node = new ComputerNode(1, 4);
    }

    @Test
    public void getID() {
        assertEquals(1, node.getID());
    }

    @Test
    public void getTimestamp() {
        assertEquals(4, node.getTimestamp());
    }

    @Test
    public void getOutNeighbors() {
        // Initial list should be empty
        assertEquals(0, node.getOutNeighbors().size());

        // Add neighbor and test result
        node.addNeighbor(new ComputerNode(2, 8));
        assertEquals(1, node.getOutNeighbors().size());
        assertEquals(2, node.getOutNeighbors().get(0).getID());
        assertEquals(8, node.getOutNeighbors().get(0).getTimestamp());

        // Add one more neighbor, for good measure
        node.addNeighbor(new ComputerNode(62, 52));
        assertEquals(2, node.getOutNeighbors().size());
        assertEquals(62, node.getOutNeighbors().get(1).getID());
        assertEquals(52, node.getOutNeighbors().get(1).getTimestamp());
    }
}