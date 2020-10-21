import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Sequence {
    private String seqTitle;
    private String seq;
    private String energy;

    public Sequence(String filepath) throws IOException{
        DotBracketReader reader = new DotBracketReader(filepath);
        List<String> collector = reader.readBractket();
        this.seq = collector.get(0);
        this.seqTitle = collector.get(2);
        this.energy = collector.get(1);
    }


    public String getSeq() {
        return this.seq;
    }

    public String getSeqTitle() {
        return this.seqTitle;
    }

    public String getEnergy() {
        return this.energy;
    }
}
