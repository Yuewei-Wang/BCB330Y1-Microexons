public class Sequence {
    private String Name;
    private String Type;
    private String Seq;
    private double Energy;

    public Sequence(String title, String seq){
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

    public String getSeq() {
        return Seq;
    }

    public double getEnergy() {
        return Energy;
    }
}
