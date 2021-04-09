/**
 * A child class of Sequence class, it stores and extend the information about micro-exons
 * @version 1.0.0
 */

public class SequenceWithMicroexon extends Sequence{
    private int A_index;
    private int A_length;

    public SequenceWithMicroexon(String title, String seq, int[] locationInfo) {
        super(title, seq);
        this.A_length = locationInfo[0];
        this.A_index = locationInfo[1];
    }

    public int getA_index() {
        return A_index;
    }

    public int getA_length() {
        return A_length;
    }
}
