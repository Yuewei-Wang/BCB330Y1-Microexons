

import javax.sound.midi.Sequence;;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CoordinateReader {
    private String filepath;
    private Integer[] headerFormat;



    public CoordinateReader(String filename){
        this.filepath = "src" + File.separator + "FileReader" + File.separator + filename;
        setHeaderFormat();
    }

    /**
     * find the header format and set the index collection as variable
     */
    private void setHeaderFormat(){
        Integer[] hf = new Integer[6];
        try { List<String> allLines = Files.readAllLines(Paths.get(this.filepath));
            String[] info = allLines.get(0).split(",");
            for (int i = 0; i< info.length; i++){
                if (info[i].equalsIgnoreCase("Transcript stable ID version")){
                    hf[0] = i;
                } else if (info[i].equalsIgnoreCase("Gene name")){
                    hf[1] = i;
                } else if (info[i].equalsIgnoreCase("Exon region start (bp)")){
                    hf[2] = i;
                } else if (info[i].equalsIgnoreCase("Exon region end (bp)")){
                    hf[3] = i;
                } else if (info[i].equalsIgnoreCase("Exon rank in transcript")) {
                    hf[4] = i;
                } else if (info[i].equalsIgnoreCase("Strand")){
                    hf[5] = i;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } this.headerFormat = hf;
    }
    /**
     * Return the map of each gene and its microexon length
     * @param AllGene the list containing SequenceWithMicroexon for all genes
     * @return hashmap containing all genes name and microexons length information
     */
    public HashMap<String, Integer> getLength(List<SequenceWithMicroexon> AllGene){
        HashMap<String, Integer> result = new HashMap<>();
        for (SequenceWithMicroexon each : AllGene){
            result.put(each.getName(), each.getA_length());
        }
        return result;
    }

    /** Load the gene transcripts with microexons included
    */
    public List<ExonsCoordinates> readTranscripts(HashMap<String, Integer> allLength) {
        List<ExonsCoordinates> result = new ArrayList<>();
        try { List<String> allLines = Files.readAllLines(Paths.get(this.filepath));
            for (int j=1; j < allLines.size(); j++){
                String[] eachExon = allLines.get(j).split(",");
                Integer ALength = allLength.get(eachExon[this.headerFormat[1]]);
                Integer exonLength = (Integer.valueOf(eachExon[this.headerFormat[3]])) -
                        (Integer.valueOf(eachExon[this.headerFormat[2]])) + 1;
                if (exonLength.equals(ALength)){
                    int rank = Integer.parseInt(eachExon[this.headerFormat[4]]);
                    ExonsCoordinates transcript = new ExonsCoordinates(eachExon[this.headerFormat[1]],
                            eachExon[this.headerFormat[0]], exonLength, rank,
                            Integer.parseInt(eachExon[this.headerFormat[2]]),
                            Integer.parseInt(eachExon[this.headerFormat[3]]),
                            Integer.parseInt(eachExon[this.headerFormat[5]]));
                    result.add(transcript);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * find the C1 and C2 information according to microexon information for a transcript
     * @return a list collecting the three exons involved in junction regions
     * each elements are:
     * start of C1, end of C1, start of A, end of A, start of C2, end of C2
     */
    public Integer[] get4Junctions(ExonsCoordinates A){
        Integer[] result = {0,0,0,0,0,0};
        int transcriptI = this.headerFormat[0];
        result[2] = A.getStart();
        result[3] = A.getEnd();
        int rankI = this.headerFormat[4];
        try { List<String> allLines = Files.readAllLines(Paths.get(this.filepath));
            for (int i=1; i<allLines.size(); i++){
                String[] eachExon = allLines.get(i).split(",");
                int rank = Integer.parseInt(eachExon[rankI]);
                String transcript = eachExon[transcriptI];
                if (A.getTranscriptID().equalsIgnoreCase(transcript) && A.getExonRank()== rank-1){
                    result[0] = Integer.parseInt(eachExon[this.headerFormat[2]]);
                    result[1] = Integer.parseInt(eachExon[this.headerFormat[3]]);
                } else if (A.getTranscriptID().equalsIgnoreCase(transcript) && A.getExonRank()== rank+1){
                    result[4] = Integer.parseInt(eachExon[this.headerFormat[2]]);
                    result[5] = Integer.parseInt(eachExon[this.headerFormat[3]]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
