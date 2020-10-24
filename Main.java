import java.io.IOException;
import java.util.List;


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
        for (int i = 0; i < AllGene.size(); i++){
            SequenceWithMicroexon gene = AllGene.get(i);
            int A_index = gene.getA_index();
            int A_length = gene.getA_length();
            int A_end = A_index + A_length;
            String seq = gene.getSeq();
            for (int j = A_index; j < A_end; j++){
                if (seq.contains("(") || seq.contains(")")){
                    count ++;
                }
            }

            }
        if (AllGene.size() == 0){
            throw new IllegalArgumentException("The list has no sequence!");
        }
        else {
            return (double)count / AllGene.size();
        }

        }


    public static void main(String[] args) throws IOException {
        String sequences = "/Users/floraw/Documents/HumanNeuronGenes/allC1+A+C2.txt";
        String table = "/Users/floraw/Documents/HumanNeuronGenes/microexon-locations.txt";

        DotBracketReader s = new DotBracketReader(sequences);

        List<Sequence> all = s.readBractket(table);
        System.out.println(all.get(0).getSeq());
        System.out.println(all.get(0).getName());
        System.out.println(all.get(0).getType());
        System.out.println(all.get(0).getEnergy());



    }
}
