
public class PumpType  {
    /////
     String Name ;
    //max frequency
    private int range = 100;
    
    private int minSpeed = 100;
    /////


    public PumpType(String Name,int range,int minSpeed ) {
        this.Name = Name;
        if(Name.equals("QW")){
            minSpeed = 80;
        }
        this.range = range; 
        this.minSpeed = minSpeed;
        
    }

    public String toString(){
        return ("Type: "+ this.getName() + " ["+this.getMinSpeed()+","+this.getRange()+"] " );
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getMinSpeed() {
        return minSpeed;
    }

    public void setMinSpeed(int minSpeed) {
        this.minSpeed = minSpeed;
    }
    
}
