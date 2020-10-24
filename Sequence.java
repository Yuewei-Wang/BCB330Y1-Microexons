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
    private String Name;
    private String Type;
    private String Seq;
    private double Energy;
    private int A_index;
    private int A_length;

    public Sequence(String title, String seq){
        String[] titleline = title.split("  ");
        Name = title.substring(title.indexOf("  ")+2,title.lastIndexOf(' '));
        Type = title.substring(title.lastIndexOf(' ')+1);
        Seq = seq;
        String energy = title.substring(title.indexOf('-')+1,title.indexOf("  "));
        Energy = Double.parseDouble(energy)*-1;
    }

    public void setSeq(String seq) {
        Seq = seq;
    }

    public void setEnergy(double energy) {
        Energy = energy;
    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }

    /**public Sequence(String filepath) throws IOException{
        DotBracketReader reader = new DotBracketReader(filepath);
        List<String> collector = reader.readBractket();
        this.seq = collector.get(0);
        this.seqTitle = collector.get(2);
        String energy = collector.get(1);
        this.energy = Double.parseDouble(energy.substring(1))*(-1);
    }
    **/



    public String getSeq() {
        return Seq;
    }

    public double getEnergy() {
        return Energy;
    }
}
