import java.util.*;

/**
 * The CommunicationsMonitor class represents the graph G built to answer
 * infection queries.
 *
 * @author Gabriel Stackhouse
 * @author Jackson Scholberg
 * @author Zachary Gorman
 */
public class CommunicationsMonitor {

    // Instance variables
    private List<CommunicationTriple> communicationList;
    private HashMap<Integer, List<ComputerNode>> computerMapping;
    private boolean createGraphCalled = false;

    /**
     * Constructor with no parameters
     */
    public CommunicationsMonitor() {
        communicationList = new LinkedList<CommunicationTriple>();
        computerMapping = new HashMap<Integer, List<ComputerNode>>();
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
        // Ignore if createGraph() was called
        if( !createGraphCalled ) {

            // Add new communication to communication list
            communicationList.add(new CommunicationTriple(c1, c2, timestamp));
        }
    }

    /**
     * Constructs the data structure as specified in the Section 2.  This
     * method should run in O(n + mlogm) time.
     */
    public void createGraph() {
        if( !createGraphCalled ) {  // Don't create graph more than once

            // Sort the communications list
            Collections.sort(communicationList);

            // Add key entries for each computer to HashMap
            for(CommunicationTriple comm : communicationList) {
                boolean ciExists = false;
                boolean cjExists = false;

                // Initialize computers (keys) in HashMap if they don't exist
                computerMapping.computeIfAbsent(comm.getCi(),
                        k -> new LinkedList<ComputerNode>());
                computerMapping.computeIfAbsent(comm.getCj(),
                        k -> new LinkedList<ComputerNode>());

                // Create new nodes
                ComputerNode ci = new ComputerNode(comm.getCi(), comm.getTk());
                ComputerNode cj = new ComputerNode(comm.getCj(), comm.getTk());

                // If nodes with duplicate parameters already exist, use
                // existing rather than creating new nodes.
                List<ComputerNode> ciList = getComputerMapping(comm.getCi());
                List<ComputerNode> cjList = getComputerMapping(comm.getCj());
                for(ComputerNode cur : ciList) {
                    if( cur.getID() == comm.getCi() &&
                            cur.getTimestamp() == comm.getTk()) {
                        ci = cur;
                        ciExists = true;
                    }
                }
                for(ComputerNode cur : cjList) {
                    if( cur.getID() == comm.getCj() &&
                            cur.getTimestamp() == comm.getTk()) {
                        cj = cur;
                        cjExists = true;
                    }
                }

                // Add directed edges of triple to nodes
                ci.addNeighbor(cj);
                cj.addNeighbor(ci);

                // Add references of nodes to HashMap, if they don't already
                // exist
                if (!ciExists) computerMapping.get(comm.getCi()).add(ci);
                if (!cjExists) computerMapping.get(comm.getCj()).add(cj);

                // Add analogous edges for Ci
                List<ComputerNode> ciMapping =
                        computerMapping.get(comm.getCi());
                if( ciMapping.size() > 1 ) {
                    ComputerNode prev = ciMapping.get(ciMapping.size() - 2);
                    prev.addNeighbor(ci);
                }

                // Add analogous edges for Cj
                List<ComputerNode> cjMapping =
                        computerMapping.get(comm.getCj());
                if( cjMapping.size() > 1 ) {
                    ComputerNode prev = cjMapping.get(cjMapping.size() - 2);
                    prev.addNeighbor(cj);
                }
            }
            createGraphCalled = true;
        }
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

        // Find first infected node of Computer c1 after given timestamp
        List<ComputerNode> c1List = getComputerMapping(c1);
        ComputerNode infected = null;
        for(ComputerNode curNode : c1List) {

            // Check if computer can be infected
            if(curNode.getTimestamp() >= x) {
                infected = curNode;
                break;  // Found infected computer
            }
        }

        // If no node is infected, return null
        if(infected == null) return null;

        // Set all nodes to default DFS values.
        //  Note: This is the first step in a normal DFS call.
        for (List<ComputerNode> list : computerMapping.values()){
            for(ComputerNode node : list){
                node.setColor(Color.WHITE);
                node.setPrev(null);
            }
        }

        // Run DFS Visit to find connected components
        DFSVisit(infected);

        // Check each node of Computer c2 for infection
        List<ComputerNode> c2List = getComputerMapping(c2);
        for( ComputerNode curNode : c2List) {

            // Check if Computer can be infected
            //  Note: If node is black, it can be reached from infected node
            if (curNode.getColor() == Color.BLACK &&
                    curNode.getTimestamp() <= y) {

                // Return infected path from c1 to c2
                return createInfectedPath(infected, curNode);
            }
        }
        return null;    // c2 cannot be infected
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
        return computerMapping;
    }

    /**
     * Returns the list of ComputerNode objects associated with computer c by
     * performing a lookup in the mapping.
     * @param c
     * @return
     */
    public List<ComputerNode> getComputerMapping(int c) {
        return computerMapping.get(c);
    }

    //-------------------------------------------------------------------------
    // Helper Methods
    //-------------------------------------------------------------------------

    /**
     * Returns the communication list, before it's turned into a graph
     * @return
     */
    public List<CommunicationTriple> getCommunicationList() {
        return communicationList;
    }

    /**
     * DFS taking in HashMap version of our graph
     *
     * @param graph graph of trace data
     */
    public void DFS(HashMap<Integer, List<ComputerNode>> graph){

        for (List<ComputerNode> list : graph.values()){
            for(ComputerNode node : list){
                node.setColor(Color.WHITE);
                node.setPrev(null);
            }
        }

        int ctr = 1;
        for (List<ComputerNode> list : graph.values()){
            for(ComputerNode node : list){
                if (node.getColor() == Color.WHITE){
                    node.setCC(ctr);
                    ctr++;
                    DFSVisit(node);
                }
            }
        }
    }

    /**
     * Recursive helper for DFS
     *
     * @param node node hit at this level of recursion
     */
    private void DFSVisit(ComputerNode node){

        node.setColor(Color.GREY);
        for(ComputerNode neighbor : node.getOutNeighbors()){
            neighbor.setCC(node.getCC());
            if (neighbor.getColor() == Color.WHITE){
                neighbor.setPrev(node);
                DFSVisit(neighbor);
            }
        }
        node.setColor(Color.BLACK);
    }

    /**
     * Creates an infected path from the start node to the infected node
     * @param start
     * @param end
     * @return
     */
    private List<ComputerNode> createInfectedPath(ComputerNode start,
                                                 ComputerNode end) {
        List<ComputerNode> infectedPath = new LinkedList<ComputerNode>();
        ComputerNode curNode = end;
        while(curNode != start) {
            infectedPath.add(0, curNode);
            curNode = curNode.getPrev();
        }
        infectedPath.add(0, start);
        return infectedPath;
    }
}
