/**
 * Simple container class that holds a communication triple
 */
public class CommunicationTriple {

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
}
