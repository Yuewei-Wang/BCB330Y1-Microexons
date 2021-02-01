package src;

public class ExonsCoordinates {
    private String geneName;
    private String transcriptID;
    private int microexonsLength;
    private int exonRank;
    private int start;
    private int end;
    private int strand; //positive or negative

    public ExonsCoordinates(String geneName, String transcriptID,
                            int microexonsLength, int exonRank,
                            int start, int end, int strand){
        this.geneName = geneName;
        this.transcriptID = transcriptID;
        this.microexonsLength = microexonsLength;
        this.exonRank = exonRank;
        this.start = start;
        this.end = end;
        this.strand = strand;
    }

    public String getGeneName() {
        return geneName;
    }

    public String getTranscriptID() {
        return transcriptID;
    }

    public int getExonRank() {
        return exonRank;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getStrand() {
        return strand;
    }

    public int getMicroexonsLength(){ return microexonsLength;}

    @Override
    public String toString(){
        return String.format(this.geneName + "," + this.transcriptID + "," + this.microexonsLength +
        "," + this.exonRank + "," + this.start + "," + this.end + "," + this.strand);
    }
}
