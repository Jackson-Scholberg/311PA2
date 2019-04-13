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
    private ArrayList<CommunicationTriple> communicationList;
    private HashMap<Integer, List<ComputerNode>> computerMapping;
    private boolean createGraphCalled = false;

    /**
     * Constructor with no parameters
     */
    public CommunicationsMonitor() {
        // TODO -- implement
        communicationList = new ArrayList<CommunicationTriple>();
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
        // Ignore if createGraph() is called
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
        if( !createGraphCalled ) {

            // Sort the communications list
            Collections.sort(communicationList);

            // Add key entries for each computer to HashMap
            for(int i = 0; i < communicationList.size(); i++) {
                CommunicationTriple comm = communicationList.get(i);
                boolean ciExists = false;
                boolean cjExists = false;

                // Initialize computers (keys) in HashMap if they don't exist
                computerMapping.computeIfAbsent(comm.getCi(),
                        k -> new ArrayList<ComputerNode>());
                computerMapping.computeIfAbsent(comm.getCj(),
                        k -> new ArrayList<ComputerNode>());

                // Create new nodes
                ComputerNode ci = new ComputerNode(comm.getCi(), comm.getTk());
                ComputerNode cj = new ComputerNode(comm.getCj(), comm.getTk());

                // If nodes already exist in graph, apply directed edges to
                //  instead of creating new nodes
                List<ComputerNode> ciList = getComputerMapping(comm.getCi());
                for(int j = 0; j < ciList.size(); j++) {
                    ComputerNode cur = ciList.get(j);

                    if( cur.getID() == comm.getCi() &&
                            cur.getTimestamp() == comm.getTk()) {
                        ci = cur;
                        ciExists = true;
                    }
                }
                List<ComputerNode> cjList = getComputerMapping(comm.getCj());
                for(int j = 0; j < cjList.size(); j++) {
                    ComputerNode cur = cjList.get(j);
                    if( cur.getID() == comm.getCj() &&
                            cur.getTimestamp() == comm.getTk()) {
                        cj = cur;
                        cjExists = true;
                    }
                }

                // Add directed edges of triple to nodes
                ci.addNeighbor(cj);
                cj.addNeighbor(ci);

                // Add references of nodes to HashMap
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

        // Find first infected node after given timestamp
        List<ComputerNode> c1List = getComputerMapping(c1);
        ComputerNode infected = null;
        for(int i = 0; i < c1List.size(); i++) {
            if(c1List.get(i).getTimestamp() >= x) {
                infected = c1List.get(i);
                break;
            }
        }

        // If no node is infected, return null
        if(infected == null) {
            return null;
        }

        // Set all nodes to default DFS values
        for (List<ComputerNode> list : computerMapping.values()){
            for(ComputerNode node : list){
                node.setColor(Color.WHITE);
                node.setPrev(null);
            }
        }

        // Run DFS Visit to find connected components
        DFSVisit(infected);

        // Check if each node can be reached from infected
        for (List<ComputerNode> list : computerMapping.values()) {
            for (ComputerNode curNode : list) {
                if( curNode.getColor() == Color.BLACK &&
                        curNode.getID() == c2 &&
                        curNode.getTimestamp() <= y) { // Computer can be
                                                       // infected
                    // Return infected path from c1 to c2
                    return createInfectedPath(infected, curNode);
                }
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
    public ArrayList<CommunicationTriple> getCommunicationList() {
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
    public void DFSVisit(ComputerNode node){

        node.setColor(Color.GREY);
        for(ComputerNode neighbor : node.getOutNeighbors()){
            neighbor.setCC(node.getCC());
            if (node.getColor() == Color.WHITE){
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
    public List<ComputerNode> createInfectedPath(ComputerNode start,
                                                 ComputerNode end) {
        List<ComputerNode> infectedPath = new ArrayList<ComputerNode>();
        ComputerNode curNode = end;
        while(curNode != start) {
            infectedPath.add(0, curNode);
            curNode = curNode.getPrev();
        }
        infectedPath.add(0, start);
        return infectedPath;
    }
}
