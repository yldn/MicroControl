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
    private String Name ;
    // 防止短路的最低阈值
    private int minSpeed = 100;

    public PumpType(String Name) {
        this.Name = Name;
        if(Name.equals("QW")){
            minSpeed = 80;
        }
        
    }

    public PumpType(String Name, int minSpeed) {
        this.Name = Name;
        this.minSpeed = minSpeed;
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
        //TODO
        return null;
    }
    
}
