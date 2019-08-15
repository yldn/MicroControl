
import java.util.ArrayList;
import java.util.List;
import java.time.*;
import java.util.*;

import com.google.gson.annotations.Expose;
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
public class Action  { 
    //related Action
//    private transient Thread t;

    //sequence number
    @Expose
    int seq;

    //delayTime;
    @Expose
    long startTime;
    //runtime
    @Expose
    long endTime;
    @Expose
    int speed;
    @Expose
    boolean reverse;

    public boolean getReverse() {
        return reverse;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    //cur pump that take action
    @Expose
    Pump p;

    //检查是否在执行
    public boolean isActive;

    public Action(int seq, long startTime, long endTime,int speed, Pump p,boolean reverse) {
        this.seq = seq;
        this.startTime = startTime;
        this.endTime = endTime;
        this.speed = (int)(p.getType().getMinSpeed()+(p.getSpeed()-p.getType().getMinSpeed() + ((double)(speed/100.0))) );
        this.p = p;
        this.reverse = reverse;
        p.getAq().add(this);
    }
//    deprecated
//    public void run2(long runTime){
//        ScheduledExecutorService  executor =  Executors.newScheduledThreadPool(1);
//        executor.scheduleWithFixedDelay(new Runnable(){
//            @Override
//                public void run() {
//                try {
//                    p.run();
//                    Thread.sleep(runTime);
//                    p.stop();
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                }
//        }, delayTime, 0, TimeUnit.SECONDS);
//        
//        
//        executor.shutdown();
//    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }



    public Pump getP() {
        return p;
    }

    public void setP(Pump p) {
        this.p = p;
    }


    public void st(){
        //singleton call
//        if(t == null){
//            t = new Thread(this);
//            t.start();
//        }
        //逻辑2
        System.out.println("Action : " + seq + " now Running !"+ "open Pump :"+getP().getName() );
        p.run(100);

//        long startTime = System.currentTimeMillis();
            
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            
            executor.scheduleWithFixedDelay(new Runnable() {
                int it = 0 ;
                @Override
                public void run() {
                    it = it + 1;
//                    long endTime = System.currentTimeMillis();
//                    int currentCollapsedTime = (int) (endTime - startTime) / 1000;
//                    boolean t = currentCollapsedTime == runTime;
                    boolean eq = (it == getEndTime() - getStartTime() );
//                    System.out.println( Thread.currentThread().getName()+"运行 过了"+it+"秒！");
                    if(eq){
                        
                        p.stop(p.pinNumber1);
                        System.out.println("Action : " + seq + " now Stop ! "+ "close Pump :"+getP().getName() );
//                        t.interrupt();
                        executor.shutdown();
                    }
                }
            },0,1000,TimeUnit.MILLISECONDS);
           this.isActive = false;
            //逻辑1
//        try {
//            while (true) {
//                long endTime = System.currentTimeMillis();
//                int currentCollapsedTime = (int) (endTime - startTime) / 1000;
//                boolean t = currentCollapsedTime == runTime;
//                System.out.println(t);
//                if (t) {
//                    p.stop();
//                    System.out.println("Action : " + seq + " now Stop ! " + "close Pump :" + getP().getName());
//                    break;
//                }
//                Thread.sleep(1000);
//            }
//        }catch (Exception e ){
//            e.printStackTrace();
//        }
    }

    public String toString(){
        return "seq:"+ seq +"/StartTime:"+getStartTime()+ "/EndTime:"+getEndTime()+"/Pump:"+this.p.getName()+"/reversed:"+this.reverse+"/Speed:"+ this.p.getSpeed()*this.getSpeed()/100;
    }
    
    
}
