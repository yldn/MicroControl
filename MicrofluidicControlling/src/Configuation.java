
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
    ArrayList<Action> actions = new ArrayList<Action>();
    ArrayList<PumpType> types=new ArrayList<PumpType>();;
    ArrayList<Pump> pumps = new ArrayList<Pump>();

    public Configuation(String name, ArrayList<Action> actions, ArrayList<PumpType> types, ArrayList<Pump> pumps) {
        this.name = name;
        this.actions = actions;
        this.types = types;
        this.pumps = pumps;
    }

    public Configuation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }

    public ArrayList<PumpType> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<PumpType> types) {
        this.types = types;
    }

    public ArrayList<Pump> getPumps() {
        return pumps;
    }

    public void setPumps(ArrayList<Pump> pumps) {
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
