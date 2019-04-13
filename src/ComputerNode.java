import java.util.List;
import java.util.ArrayList;

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
    private ArrayList<ComputerNode> neighbors;
    private Color color;
    private ComputerNode prev;

    /**
     * ComputerNode constructor.
     * @param ID - The ID associated with this node.
     * @param timestamp - The timestamp associated with this node.
     */
    public ComputerNode(int ID, int timestamp) {
        this.ID = ID;
        this.timestamp = timestamp;
        this.neighbors = new ArrayList<>();
        this.color = Color.WHITE;
        this.prev = null;
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
        return neighbors;
    }

    //-------------------------------------------------------------------------
    // Helper Methods
    //-------------------------------------------------------------------------

    public void addNeighbor(ComputerNode n) {
        neighbors.add(n);
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void setPrev(ComputerNode prev){
        this.prev = prev;
    }

    public Color getColor(){
        return color;
    }

    public ComputerNode getPrev(){
        return prev;
    }
}
