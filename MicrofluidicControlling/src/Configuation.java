
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author liuyang
 */
public class Configuation {
    private String name ;
    List<Pump> pumps = new ArrayList<Pump>();
    
    public Configuation(String name) {
        this.name = name;
    }
    //////getter //////
    public String getName() {
        return name;
    }

    public List<Pump> getPumps() {
        return pumps;
    }
    /////////setter ///////

    public void setName(String name) {
        this.name = name;
    }

    public void setPumps(List<Pump> pumps) {
        this.pumps = pumps;
    }
    
    //add 
    public void addPump(Pump pump){
        pumps.add(pump);
        System.out.println("new Pump added");
    }
    //delete 
    public boolean deletePump(Pump pump){
        if(pumps.contains(pump)){
            boolean res = pumps.remove(pump);
            if(res == true){
                System.out.println(pump.getName()+"pump deleted");
                return true;
            }
            else {
                System.out.println(pump.getName()+"pump can't delete");
                return false;
            }
        }
        else {
             System.out.println(pump.getName()+" pump can't found");
                return false;
        }
    }
    
}
