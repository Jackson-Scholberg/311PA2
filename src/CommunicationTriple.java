/**
 * Simple container class that holds a communication triple
 */
public class CommunicationTriple implements Comparable<CommunicationTriple>{

    // Instance variables
    private int ci;
    private int cj;
    private int tk;

    public CommunicationTriple(int ci, int cj, int tk) {
        this.ci = ci;
        this.cj = cj;
        this.tk = tk;
    }

    /**
     * Returns the ci value
     * @return
     */
    public int getCi() {
        return ci;
    }

    /**
     * Returns the cj value
     * @return
     */
    public int getCj() {
        return cj;
    }

    /**
     * Returns the tk value
     * @return
     */
    public int getTk() {
        return tk;
    }

    /**
     * A CommunicationTriple gets compared by its timestamp
     * @param other - the CommunicationTriple we are comparing
     * @return
     */
    @Override
    public int compareTo(CommunicationTriple other) {
        return this.getTk() - other.getTk();
    }
}
