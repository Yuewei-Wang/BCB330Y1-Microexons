/**
 * A class that handle the exon information of micro-exon coordinates, from a file containing BioMart result.
 * @version 1.0.0
 */

public class ExonsCoordinates {
    private String geneName;
    private String transcriptID;
    private int microexonsLength;
    private int exonRank;
    private int start;
    private int end;
    private int strand; //positive or negative
    private String exonID;

    public ExonsCoordinates(String geneName, String transcriptID,
                            int microexonsLength, int exonRank,
                            int start, int end, int strand, String exonID){
        this.geneName = geneName;
        this.transcriptID = transcriptID;
        this.microexonsLength = microexonsLength;
        this.exonRank = exonRank;
        this.start = start;
        this.end = end;
        this.strand = strand;
        this.exonID = exonID;
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

    public String getExonID(){return exonID;}

}
