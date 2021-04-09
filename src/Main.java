import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;


public class Main {

    public void exonPercentage(List<SequenceWithMicroexon> AllGene){
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
            System.out.println(title + ": " + String.format("%.2f",percentage));
        }
    }

    public void junctionPercentage(List<Sequence> AllGene){
        for (Sequence sequence : AllGene) {
            int count = 0;
            String title = sequence.getName();
            String seq = sequence.getSeq();
            for (int j = 0; j < 60; j++) {
                if (seq.charAt(j) == '(' || seq.charAt(j) == ')') {
                    count++;
                }
            }
            double percentage = (double) count / 60;
            System.out.println(title + ": " + String.format("%.2f",percentage));
        }
    }


    public void getJunctions(String filename, List<SequenceWithMicroexon> allSeq) throws FileNotFoundException {
        CoordinateReader cr = new CoordinateReader(filename);
        HashMap<String, Integer> allALength  = cr.getLength(allSeq);
        List<ExonsCoordinates> allAExons = cr.readTranscripts(allALength);
        String[] title_line = {"Gene Name", "Transcript ID", "Exon ID",
                                "Junction 1", "Junction 2","Junction 3", "Junction 4"};
        try (PrintWriter out = new PrintWriter(
                "src" + File.separator + "Out" + File.separator +"out_Microsexons.csv")) {
            StringBuilder sb = new StringBuilder();
            for (String column : title_line){
                sb.append(column);
                sb.append(',');
            }
            sb.append("strand");
            sb.append('\n');
            for (ExonsCoordinates each : allAExons){
                Integer[] junctions = cr.get4Junctions(each);
                sb.append(each.getGeneName());
                sb.append(',');
                sb.append(each.getTranscriptID());
                sb.append(',');
                sb.append(each.getExonID());
                sb.append(',');
                sb.append(junctions[1]);
                sb.append(',');
                sb.append(junctions[2]);
                sb.append(',');
                sb.append(junctions[3]);
                sb.append(',');
                sb.append(junctions[4]);
                sb.append(',');
                sb.append(each.getStrand());
                sb.append('\n');
            } out.write(sb.toString());
        } catch (FileNotFoundException o){
            o.fillInStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        String HumanSequence = "src" + File.separator + "FileReader" + File.separator + "allHumanC1+A+C2.txt";
        String HumanTable = "src" + File.separator + "FileReader" + File.separator + "HumanALocation.txt";
        String Junction1 = "src" + File.separator + "FileReader" + File.separator + "junction1.txt";
        String Junction2 = "src" + File.separator + "FileReader" + File.separator + "junction2.txt";
        String Junction3 = "src" + File.separator + "FileReader" + File.separator + "junction3.txt";
        String Junction4 = "src" + File.separator + "FileReader" + File.separator + "junction4.txt";

        DotBracketReader humanSeq = new DotBracketReader(HumanSequence);
        List<Sequence> allHuman =humanSeq.readBractket(HumanTable);
        List<Sequence> junction1 = humanSeq.readJunction(Junction1);
        List<Sequence> junction2 = humanSeq.readJunction(Junction2);
        List<Sequence> junction3 = humanSeq.readJunction(Junction3);
        List<Sequence> junction4 = humanSeq.readJunction(Junction4);


        System.out.println(allHuman.size()); //58 pairs
        List<SequenceWithMicroexon> exonSeq = new ArrayList<>();
        for (Sequence sequence : allHuman) {
            if (sequence.getType().equals("C1+A+C2")) {
                exonSeq.add((SequenceWithMicroexon) sequence);
            }
        }

        Main m = new Main();
        System.out.println("Human genes \nMicro-exon (A) Base Pair Percentages C1+A+C2:");
        m.exonPercentage(exonSeq);

        //result were written into out_Microexons.csv
        m.getJunctions("mart_export_58genes.txt", exonSeq);


        System.out.println("Junction 1 Overall Base Pair Percentage:");
        m.junctionPercentage(junction1);

        System.out.println("Junction 2 Overall Base Pair Percentage:");
        m.junctionPercentage(junction2);

        System.out.println("Junction 3 Overall Base Pair Percentage:");
        m.junctionPercentage(junction3);

        System.out.println("Junction 4 Overall Base Pair Percentage:");
        m.junctionPercentage(junction4);
    }
}
