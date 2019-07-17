
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
public class Action {

//   List<Action> listOfPumpAction = new ArrayList<Action>();
    //related Action
    public Action(int seq, int time, Pump pump, int pumpRenge, int pwmValue, boolean isActive) {
        ///有问题，之后重新写constructor
        this.pumpAction = new Action(seq+1, time , pump , pumpRenge , pwmValue , isActive );
        
        this.seq = seq;
        this.time = time;
        this.pump = pump;
        this.pumpRenge = pumpRenge;
        this.pwmValue = pwmValue;
        this.isActive = isActive;
    }
    
    Action pumpAction;

    //sequence number
    int seq;

    //runtime
    int time;
    //cur pump that take action
    Pump pump;
    //speed 阈值 > pumptype.minSpeed ,,,,
    //pwmRange
    int pumpRenge;
    //pwm value 
    int pwmValue;
    
    //检查是否在执行
    boolean isActive;
    
    
    
    
    
    
    
}
