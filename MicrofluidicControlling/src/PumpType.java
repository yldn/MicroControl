/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author liuyang
 */
public class PumpType {
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
    //PumpType as String Output
//    public String toString(){
//        return ("Type:"+ this.getName() + " Range: "+ this.getRange() + " frequency: "+ this.getFrequency() + " minSpeed: "+this.getMinSpeed());
//    }

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
