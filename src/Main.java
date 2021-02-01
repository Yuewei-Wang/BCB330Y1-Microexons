import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;


public class Main {
    public List<Sequence> AllGene;

    public Main(){

    }

    public double returnPercentage(List<SequenceWithMicroexon> AllGene){
        int count = 0;
        if (AllGene.size() == 0){
            throw new IllegalArgumentException("The list has no sequence!");
        }
        for (int i = 0; i < AllGene.size(); i++){
            SequenceWithMicroexon gene = AllGene.get(i);
            int A_index = gene.getA_index();
            int A_length = gene.getA_length();
            int A_end = A_index + A_length;
            String seq = gene.getSeq();
            if (seq.substring(A_index, A_end).contains("(") || seq.substring(A_index, A_end).contains(")")){
                count ++;
            }
        }

        return (double)count / AllGene.size();
    }

    public HashMap<String, Double> exonPercentage(List<SequenceWithMicroexon> AllGene){
        HashMap<String, Double> result = new HashMap<>();
        for (SequenceWithMicroexon sequenceWithMicroexon : AllGene) {
            int count = 0;
            SequenceWithMicroexon gene = sequenceWithMicroexon;
            String title = gene.getName();
            int A_index = gene.getA_index();
            int A_length = gene.getA_length();
            int A_end = A_index + A_length;
            String seq = gene.getSeq();
            for (int j = A_index-1; j < A_end-1; j++) {
                if (seq.charAt(j) == '(' || seq.charAt(j) == ')') {
                    count++;
                }
            }
            double percentage = (double) count / A_length;
            result.put(title, percentage);

        }
        return result;
    }

    public HashMap<ExonsCoordinates, Integer[]> getJunctions(String filename, List<SequenceWithMicroexon> allSeq){
        CoordinateReader cr = new CoordinateReader(filename);
        HashMap<String, Integer> allALength  = cr.getLength(allSeq);
        List<ExonsCoordinates> allAExons = cr.readTranscripts(allALength);
        //System.out.println(allAExons.get(0).getGeneName());
        HashMap<ExonsCoordinates, Integer[]> allTranscriptJunction = new HashMap<>();
        for (ExonsCoordinates each : allAExons){
            Integer[] junctions = cr.get4Junctions(each);
            System.out.println(each.getGeneName());
            System.out.println(each.getTranscriptID());
            System.out.println("(" + junctions[0] + ") + ("+ junctions[1] + ") + ("+ junctions[2]
                    + ") + ("+ junctions[3] + ") + ("+ junctions[4] + ") + ("+ junctions[5] + ")\n");
        }
        return allTranscriptJunction;
    }


    public static void main(String[] args) throws IOException {
        String HumanSequence = "src" + File.separator + "FileReader" + File.separator + "allHumanC1+A+C2.txt";
        String HumanTable = "src" + File.separator + "FileReader" + File.separator + "HumanALocation.txt";
        String MouseSequence = "src" + File.separator + "FileReader" + File.separator + "allMouseC1+A+C2.txt";
        String MouseTable = "src" + File.separator + "FileReader" + File.separator + "MouseALocation.txt";
        String ChickenSequence = "src" + File.separator + "FileReader" + File.separator + "allChickenC1+A+C2.txt";
        String ChickenTable = "src" + File.separator + "FileReader" + File.separator + "ChickenALocation.txt";

        DotBracketReader hs = new DotBracketReader(HumanSequence);
        List<Sequence> allHuman = hs.readBractket(HumanTable);
        System.out.println(allHuman.size()); //58 pairs
        List<SequenceWithMicroexon> exonSeq = new ArrayList<>();
        for (Sequence sequence : allHuman) {
            if (sequence.getType().equals("C1+A+C2")) {
                exonSeq.add((SequenceWithMicroexon) sequence);
            }
        }


        /*DotBracketReader ms = new DotBracketReader(MouseSequence);
        List<Sequence> allMouse = ms.readBractket(MouseTable);
        // System.out.println(allMouse.size()); //53 pairs
        List<SequenceWithMicroexon> exonSeq2 = new ArrayList<>();
        for (Sequence sequence : allMouse) {
            if (sequence.getType().equals("C1+A+C2")) {
                exonSeq2.add((SequenceWithMicroexon) sequence);
            }
        }*/

        /*DotBracketReader cs = new DotBracketReader(ChickenSequence);
        List<Sequence> allChicken = cs.readBractket(ChickenTable);
        // System.out.println(allChicken.size());
        List<SequenceWithMicroexon> exonSeq3 = new ArrayList<>();
        for (Sequence sequence : allChicken) {
            if (sequence.getType().equals("C1+A+C2")) {
                exonSeq3.add((SequenceWithMicroexon) sequence);
            }
        }*/


        Main m = new Main();
        System.out.println("Human genes");
        //System.out.println(m.returnPercentage(exonSeq));
        //System.out.println(m.exonPercentage(exonSeq));
        /*System.out.println("Mouse genes");
        System.out.println(m.returnPercentage(exonSeq2));
        System.out.println(m.exonPercentage(exonSeq2));
        System.out.println("Chicken genes");
        System.out.println(m.returnPercentage(exonSeq3));
        System.out.println(m.exonPercentage(exonSeq3));
         */

        m.getJunctions("mart_export_58genes.txt", exonSeq);

    }
}
