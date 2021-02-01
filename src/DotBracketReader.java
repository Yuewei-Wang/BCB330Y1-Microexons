import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class DotBracketReader {
    private String filepath;

    public DotBracketReader(String filepath){
        this.filepath = filepath;
    }

    public String getFilepath() {
        return filepath;
    }

    public int[] readLocation (String name){
        int[] result = new int[2];
        try { List<String> allLines = Files.readAllLines(Paths.get(this.filepath));
            for(String line : allLines){
                if(line.contains(name)){
                    String[] info = line.split("\t");
                    result[0] = Integer.parseInt(info[3]);
                    result[1] = Integer.parseInt(info[4]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } return result;
    }


    public List<Sequence> readBractket(String filepath){
        DotBracketReader locationInfo = new DotBracketReader(filepath);
        List<Sequence> result = new ArrayList<>();
        try { List<String> allLines = Files.readAllLines(Paths.get(this.filepath));
            for (int i = 0; i < allLines.size()-1; i+=3) {
                Sequence gene = new Sequence(allLines.get(i), allLines.get(i+2));
                if (gene.getType().equals("C1+A+C2")){
                    int[] Info = locationInfo.readLocation(gene.getName());
                    gene = new SequenceWithMicroexon(allLines.get(i), allLines.get(i+2), Info);
                } result.add(gene);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } return result;
    }
}
