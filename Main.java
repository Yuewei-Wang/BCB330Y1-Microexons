import java.awt.*;
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



    public static void main(String[] args) throws IOException {
        String HumanSequence = "/Users/floraw/Documents/HumanNeuronGenes/allHumanC1+A+C2.txt";
        String HumanTable = "/Users/floraw/Documents/HumanNeuronGenes/HumanALocation.txt";
        String MouseSequence = "/Users/floraw/Documents/MouseNeuronGenes/allMouseC1+A+C2.txt";
        String MouseTable = "/Users/floraw/Documents/MouseNeuronGenes/MouseALocation.txt";
        String ChickenSequence = "/Users/songchenning/Desktop/allChickenC1+A+C2.txt";
        String ChickenTable = "/Users/songchenning/Desktop/ChickenALocation.txt";

        DotBracketReader hs = new DotBracketReader(HumanSequence);
        List<Sequence> allHuman = hs.readBractket(HumanTable);
        System.out.println(allHuman.size()); //58 pairs
        List<SequenceWithMicroexon> exonSeq = new ArrayList<>();
        for (Sequence sequence : allHuman) {
            if (sequence.getType().equals("C1+A+C2")) {
                exonSeq.add((SequenceWithMicroexon) sequence);
            }
        }
        //System.out.println(exonSeq.size()); //58 pairs

        DotBracketReader ms = new DotBracketReader(MouseSequence);
        List<Sequence> allMouse = ms.readBractket(MouseTable);
        // System.out.println(allMouse.size()); //53 pairs
        List<SequenceWithMicroexon> exonSeq2 = new ArrayList<>();
        for (Sequence sequence : allMouse) {
            if (sequence.getType().equals("C1+A+C2")) {
                exonSeq2.add((SequenceWithMicroexon) sequence);
            }
        }

        DotBracketReader cs = new DotBracketReader(ChickenSequence);
        List<Sequence> allChicken = cs.readBractket(ChickenTable);
        // System.out.println(allChicken.size());
        List<SequenceWithMicroexon> exonSeq3 = new ArrayList<>();
        for (Sequence sequence : allChicken) {
            if (sequence.getType().equals("C1+A+C2")) {
                exonSeq3.add((SequenceWithMicroexon) sequence);
            }
        }


        Main m = new Main();
        System.out.println("Human genes");
        System.out.println(m.returnPercentage(exonSeq));
        System.out.println(m.exonPercentage(exonSeq));
        System.out.println("Mouse genes");
        System.out.println(m.returnPercentage(exonSeq2));
        System.out.println(m.exonPercentage(exonSeq2));
        System.out.println("Chicken genes");
        System.out.println(m.returnPercentage(exonSeq3));
        System.out.println(m.exonPercentage(exonSeq3));

    }
}
