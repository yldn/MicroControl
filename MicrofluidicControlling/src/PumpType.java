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
    // 防止短路的最低阈值
    private int range = 1000;
    int clock = 1920;
//    0-1023
    int dutyCycle;


    int frequency = 100;
    int minSpeed = 100;
    /////

    public int getDutyCycle() {
        return dutyCycle;
    }

    public void setDutyCycle(int dutyCycle) {
        this.dutyCycle = dutyCycle;
    }

    public int getFrequency() {
        return frequency;
    }

    ///default = 100;
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }


    public PumpType(String Name) {
        this.Name = Name;
        if(Name.equals("QW")){
            minSpeed = 80;
        }
        
    }

    public PumpType(String Name,int fre , int clock , int minSpeed) {
        this.Name = Name;
        this.frequency = fre;
        this.clock = clock;
        this.minSpeed = minSpeed;
    }

    public int getRange() {
        range = 19200000/clock/frequency;
        return range;
    }

    public void setRenge(int range) {
        this.range = range;
    }

    public int getClock() {
        return clock;
    }

    public void setClock(int clock) {
        this.clock = clock;
    }
    
    
    
    public String getName() {
        return Name;
    }

    public int getMinSpeed() {
        return minSpeed;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setMinSpeed(int minSpeed) {
        this.minSpeed = minSpeed;
    }
    
    //PumpType as String Output
    public String toString(){
        return ("Type:"+ this.getName() + " Range: "+ this.getRange() + " frequency: "+ this.getFrequency() + " minSpeed: "+this.getMinSpeed());
    }
    
}
