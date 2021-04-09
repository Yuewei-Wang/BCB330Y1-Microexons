/**
 * A class that store and classify the information from C1+C2 sequences
 * @version 1.0.0
 */

public class Sequence {
    private String Name;
    private String Type;
    private String Seq;
    private double Energy;

    public Sequence(String title, String seq){
        String[] contents = title.split("  ");
        if (contents.length>1){
            String[] energyInfo = contents[0].split(" ");
            String[] nameInfo = contents[1].split(" ");
            Name = nameInfo[0];
            Type = nameInfo[1];
            String energy = energyInfo[2].substring(1);
            Energy = Double.parseDouble(energy)*-1;
        } else {//junction
            String[] shortContent = title.split(" ");
            Name = shortContent[0].substring(1);
            Type = shortContent[1];
        }
        Seq = seq;
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

    public String getSeq() {
        return Seq;
    }

    public double getEnergy() {
        return Energy;
    }
}
