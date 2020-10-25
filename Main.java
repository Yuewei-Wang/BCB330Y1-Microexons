import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;


public class Main {
    public List<Sequence> AllGene;

    public Main(){

    }

    public void readSequence(String filename1, String filename2){
        DotBracketReader sequenceFile = new DotBracketReader(filename1);
        DotBracketReader locationFile = new DotBracketReader(filename2);
        List<Sequence> AllGene = sequenceFile.readBractket(locationFile.getFilepath());
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
        for (int i = 0; i < AllGene.size(); i++){
            int count = 0;
            SequenceWithMicroexon gene = AllGene.get(i);
            String title = gene.getName();
            int A_index = gene.getA_index();
            int A_length = gene.getA_length();
            int A_end = A_index + A_length;
            String seq = gene.getSeq();
            for (int j = A_index; j < A_end; j++){
                if (seq.charAt(j) == '(' || seq.charAt(j) == ')'){
                    count ++;
                }
            }
            double percentage = (double)count/A_length;
            result.put(title, percentage);

        }
        return result;
    }



    public static void main(String[] args) throws IOException {
        String sequences = "/Users/floraw/Documents/HumanNeuronGenes/allC1+A+C2.txt";
        String table = "/Users/floraw/Documents/HumanNeuronGenes/microexon-locations.txt";

        DotBracketReader s = new DotBracketReader(sequences);

        List<Sequence> all = s.readBractket(table);
        //System.out.println(all.get(0).getSeq());
        //System.out.println(all.get(0).getName());
        //System.out.println(all.get(0).getType());
        //System.out.println(all.get(0).getEnergy());
        List<SequenceWithMicroexon> exonSeq = new ArrayList<>();
        for (int i = 0; i < all.size(); i ++){
            if (all.get(i).getType().equals("C1+A+C2")){
                exonSeq.add((SequenceWithMicroexon)all.get(i));
            }
        }

        Main m = new Main();
        HashMap<String, Double> result = m.exonPercentage(exonSeq);
        double percentage = m.returnPercentage(exonSeq);
        System.out.println(percentage);
        System.out.println(result);

    }
}
