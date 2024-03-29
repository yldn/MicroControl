
import com.google.gson.annotations.Expose;
import com.pi4j.io.gpio.*;

import static com.pi4j.io.gpio.RaspiPin.*;
import static com.pi4j.system.SystemInfo.BoardType.RaspberryPi_3B_Plus;
import com.pi4j.util.Console;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftPwm;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Pump  {
    /////////RASPERRYPI ALL PINS /////////////
    @Expose
    private String name;
    @Expose
    private PumpType type;
    @Expose
    int speed = 100 ;


    @Expose
    int pinNumber1;
    @Expose
    int pinNumber2;
    //每个pump上的两个针脚
    //默认Pin1高电位 Pin2低电位
    transient boolean  isActive = false;
    
    transient private PriorityQueue<Action> aq;
    transient private PriorityQueue<Action> eq;
    
    public PriorityQueue<Action> getAq() {
        return aq;
    }

    public void setAq(PriorityQueue<Action> aq) {
        this.aq = aq;
    }

    public void addAction(Action a){
        this.getAq().add(a);
    }
    public void removeAction(Action a ){
        if(aq.contains(a)){
            aq.remove(a);
        }
        else
            return;
    }


    public PriorityQueue<Action> getEq() {
        return eq;
    }

    public void setEq(PriorityQueue<Action> eq) {
        this.eq = eq;
    }

    //GPIO控制单元
//    GpioController gpio = GpioFactory.getInstance();
//    transient GpioController gpio ;
    
    //For Debugging
    transient final Console console = new Console();

    public Pump(String name, PumpType type, int speed, int pinNumber1, int pinNumber2) {
        this.name = name;
        this.type = type;
//        this.speed = (int)(type.getMinSpeed()+(type.getRange()- type.getMinSpeed() ) * ((double)speed/100.0));
        this.speed = speed ; 
        this.pinNumber1 = pinNumber1;
        this.pinNumber2 = pinNumber2;
        ///comment out for stackoverflow by constructor
        aq = new PriorityQueue<Action>(actionComparator());
    }

    Comparator<Action> actionComparator(){
        Comparator<Action> actionComparator = (Action a1, Action a2) -> {
            if(a1.getStartTime() - a2.getStartTime() < 0){
                return -1;
            }else if (a1.getStartTime() - a2.getStartTime() == 0){
                return 0;
            }else {
                return 1;
            }
        };
        return  actionComparator;
    }
    void initEq(){
        eq = new PriorityQueue<>(aq);
    }
    
////////////getter /////////////////////
    public String getName() {
        return name;
    }

    public PumpType getType() {
        return type;
    }

    public int getPinNumber1() {
        return pinNumber1;
    }

    public int getPinNumber2() {
        return pinNumber2;
    }
   ////////////////////setter/////////////////


    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setPinNumber1(int pinNumber1) {
        this.pinNumber1 = pinNumber1;
    }

    public void setPinNumber2(int pinNumber2) {
        this.pinNumber2 = pinNumber2;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(PumpType type) {
        this.type = type;
    }

    public void run(){
        run(speed);
    }
    public void revertrun(){
        revertrun(speed);
    }
    
    public void run(int speed){
        initServo();
        stop(pinNumber2);
        provisionPin(pinNumber1, type.getMinSpeed() , type.getRange());
        provisionPin(pinNumber2, type.getMinSpeed() , type.getRange());
        
        System.out.println("provision pin:"+ pinNumber1 + "now running ");
            try{
                
                start(pinNumber2,0 );
                start(pinNumber1,type.getRange()*speed /100 );
                isActive = true;
            }catch(Exception e ){
//                e.printStackTrace();
                isActive = false;
            }
    }
//    fundmebtal functions
    public void provisionPin(int pin ,int initvalue , int range){
        
        SoftPwm.softPwmCreate(pin, initvalue , range);
        System.out.println("provision pin :"+pin+"["+initvalue+";"+range+ "]" );
    }
    public void start(int pinNumber , int value){
        SoftPwm.softPwmWrite(pinNumber,value);
        System.out.println("pwm write pin :"+pinNumber+"["+value+ "]" );
    }
    public void stop(int pinNumber){
        SoftPwm.softPwmStop(pinNumber);
        System.out.println("stop now "+ pinNumber);
    }
    public void initServo(){
        Gpio.wiringPiSetup();
        System.out.println("InitServi :" );
    }
    
    public void shutdown(){
        stop(pinNumber1);
        stop(pinNumber2);
        isActive = false;
        System.out.println("now pump "+ name + "stopped ! ");
    }



    public void revertrun(int speed ){
        initServo();
        stop(pinNumber1);
        provisionPin(pinNumber2, type.getMinSpeed() , type.getRange());
        provisionPin(pinNumber1, type.getMinSpeed() , type.getRange());
        System.out.println("REVERSED :: provision pin:"+ pinNumber2 + "now running ");
        try{
             start(pinNumber1,0 );
             start(pinNumber2 , (type.getRange() * speed) /100 );
             isActive = true;
        }catch(Exception e ){
            e.printStackTrace();
            isActive = false;
        }
    }
    
    //Pump as String Output
    public String toString(){
        
        return ("Name : "+this.getName()+"/"+type.toString()+"/"+"pin1:" + pinNumber1+"/"+"pin2:" + pinNumber2+"/actualMaxSpeed"+this.speed+"%");
    }


    
    
    
}
