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

    public List<String> readBractket(){
        List<String> result = new ArrayList<>();
        try {
            List<String> allLines = Files.readAllLines(Paths.get(this.filepath));
            for (String line : allLines) {
                if (line.contains(">")){
                    result.add(line.substring(line.indexOf("  ")+2));
                    result.add(line.substring(line.indexOf("= ")+2, line.indexOf("  ")));
                } else if (line.contains(".") && line.contains("(") && line.contains(")")){
                    result.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } return result;
    }

}
