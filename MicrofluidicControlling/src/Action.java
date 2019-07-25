
import java.util.ArrayList;
import java.util.List;
import java.time.*;
import java.util.*;
import com.pi4j.io.gpio.Pin;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author liuyang
 */
public class Action implements Runnable { 
    //related Action
    private transient Thread t;

    //sequence number
    int seq;
    
    //delayTime;
    int delayTime;
    //runtime
    int runTime;
    //cur pump that take action
    Pump p;
    
    //speed 阈值 > pumptype.minSpeed ,,,,
    //pwmRange
    int pumpRenge;
    //pwm value 
    int pwmValue;
    
    //检查是否在执行
    boolean isActive;

    public Action(int seq, int delayTime, int runTime, Pump p) {
        this.seq = seq;
        this.delayTime = delayTime;
        this.runTime = runTime;
        this.p = p;
    }
    
    public void runWithPin(Pin pin,long runTime){
        ScheduledExecutorService  executor =  Executors.newScheduledThreadPool(1);
        executor.scheduleWithFixedDelay(new Runnable(){
            @Override
                public void run() {
                try {
                    p.run();
                    Thread.sleep(runTime);
                    p.stop();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
        }, delayTime, 0, TimeUnit.SECONDS);
        
        
        executor.shutdown();
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    public int getRunTume() {
        return runTime;
    }

    public void setRunTume(int runTume) {
        this.runTime = runTume;
    }

    public Pump getP() {
        return p;
    }

    public void setP(Pump p) {
        this.p = p;
    }

    @Override
    public void run() {
        this.start();
    }
    public void start(){
        if(t == null){
            t = new Thread(this);
            t.start();
        }
    }

    public String toString(){
        return "seq:"+ seq +"/Delay:"+this.delayTime+ "/RunTime:"+this.runTime+"/Pump:"+this.p.getName();
    }
    
    
}
