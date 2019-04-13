import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * The CommunicationsMonitor class represents the graph G built to answer
 * infection queries.
 *
 * @author Gabriel Stackhouse
 * @author Jackson Scholberg
 * @author Zachary Gorman
 */
public class CommunicationsMonitor {

//    instance variables
    private ArrayList<ComputerNode> communicationList;
    private HashMap<Integer, ComputerNode> computerMapping;

    /**
     * Constructor with no parameters
     */
    public CommunicationsMonitor() {
        // TODO -- implement
        communicationList = new ArrayList<ComputerNode>();
        computerMapping = new HashMap<Integer, ComputerNode>();
    }

    /**
     * Takes as input two integers c1, c2, and a timestamp. This triple
     * represents the fact that the computers with IDs c1 and c2 have
     * communicated at the given timestamp. This method should run in O(1) time.
     * Any invocation of this method after createGraph() is called will be
     * ignored.
     * @param c1
     * @param c2
     * @param timestamp
     */
    public void addCommunication(int c1, int c2, int timestamp) {
        // TODO -- implement
        ComputerNode x = new ComputerNode(c1, timestamp);
        ComputerNode y = new ComputerNode(c2, timestamp);
        communicationList.add(x);
        communicationList.add(y);
    }

    /**
     * Constructs the data structure as specified in the Section 2.  This
     * method should run in O(n + mlogm) time.
     */
    public void createGraph() {
        // TODO -- implement
    }

    /**
     * Determines whether computer c2 could be infected by time y if computer
     * c1 was infected at time x. If so, the method returns an ordered list of
     * ComputerNode objects that represents the transmission sequence. This
     * sequence is a path in graph G. The first Computer Node object on the
     * path will correspond to c1. Similarly, the lastComputer Node object on
     * the path will correspond to c2. If c2 cannot be infected, return null.
     *
     * Example 3.In Example 1, an infection path would be (C1,4),(C2,4),(C2,8),
     * (C4,8),(C3,8). This method can assume that it will be called only after
     * createGraph()and that x ≤ y. This method must run in O(m) time. This
     * method can also be called multiple times with different inputs once the
     * graph is constructed (i.e., once createGraph() has been invoked).
     * @param c1
     * @param c2
     * @param x
     * @param y
     * @return
     */
    public List<ComputerNode> queryInfection(int c1, int c2, int x, int y) {
        // TODO -- implement
        return null;
    }

    /**
     * Returns a HashMap that represents the mapping between an Integer and a
     * list of ComputerNode objects. The Integer represents the ID of some
     * computer Ci, while the list consists of pairs (Ci,t1),(Ci,t2),...,
     * (Ci,tk), represented by ComputerNode objects, that specify that Ci has
     * communicated with other computers at times t1,t2,...,tk. The list for
     * each computer must be ordered by time; i.e.,t1<t2<···<tk.
     * @return
     */
    public HashMap<Integer, List<ComputerNode>> getComputerMapping() {
        // TODO -- implement
        return null;
    }

    /**
     * Returns the list of ComputerNode objects associated with computer c by
     * performing a lookup in the mapping.
     * @param c
     * @return
     */
    public List<ComputerNode> getComputerMapping(int c) {
        // TODO -- implement
        return null;
    }
}
