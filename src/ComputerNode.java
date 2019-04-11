import java.util.List;

/**
 * The ComputerNode class represents the nodes of the graph G, which are pairs
 * (Ci, t).
 *
 * @author Gabriel Stackhouse
 * @author Jackson Scholberg
 * @author Zachary Gorman
 */
public class ComputerNode {

    // Instance variables
    private int ID;
    private int timestamp;

    /**
     * ComputerNode constructor.
     * @param ID - The ID associated with this node.
     * @param timestamp - The timestamp associated with this node.
     */
    public ComputerNode(int ID, int timestamp) {
        this.ID = ID;
        this.timestamp = timestamp;
    }

    /**
     * Returns the ID of the associated computer.
     * @return
     */
    public int getID() {
        return ID;
    }

    /**
     * Returns the timestamp associated with this node.
     * @return
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Returns a list of ComputerNode objects to which there is outgoing edge
     * from this ComputerNode object.
     * @return
     */
    public List<ComputerNode> getOutNeighbors() {
        // TODO -- implement
        return null;
    }
}
