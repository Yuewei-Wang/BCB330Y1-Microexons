public class Sequence {
    private String Name;
    private String Type;
    private String Seq;
    private double Energy;

    public Sequence(String title, String seq){
        String[] contents = title.split("  ");
        String[] energyInfo = contents[0].split(" ");
        String[] nameInfo = contents[1].split(" ");
        Name = nameInfo[0];
        Type = nameInfo[1];
        //Species = nameInfo[2];
        Seq = seq;
        String energy = energyInfo[2].substring(1);
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

    public String getSeq() {
        return Seq;
    }

    public double getEnergy() {
        return Energy;
    }
}
