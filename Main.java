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
