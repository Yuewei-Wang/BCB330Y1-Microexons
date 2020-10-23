public class SequenceWithMicroexon extends Sequence{
    private int A_index;
    private int A_length;

    public SequenceWithMicroexon(String title, String seq, int[] locationInfo) {
        super(title, seq);
        this.A_length = locationInfo[0];
        this.A_index = locationInfo[1];
    }
}
