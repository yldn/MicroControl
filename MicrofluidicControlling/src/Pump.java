/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author liuyang
 */
import com.google.gson.annotations.Expose;
import com.pi4j.io.gpio.*;

import static com.pi4j.io.gpio.RaspiPin.*;
import static com.pi4j.system.SystemInfo.BoardType.RaspberryPi_3B_Plus;
import com.pi4j.util.Console;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftPwm;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Pump {
    /////////RASPERRYPI ALL PINS /////////////
    Pump p ;

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
    private  Pin Pin1;
    private  Pin Pin2;

    private PriorityQueue<Action> aq;

    public PriorityQueue<Action> getAq() {
        return aq;
    }

    public void setAq(PriorityQueue<Action> aq) {
        this.aq = aq;
    }

    public PriorityQueue<Action> getEq() {
        return eq;
    }

    public void setEq(PriorityQueue<Action> eq) {
        this.eq = eq;
    }

    private PriorityQueue<Action> eq;
    //GPIO控制单元
    //GpioController gpio = GpioFactory.getInstance();
//    transient GpioController gpio ;
    
    //For Debugging
    transient final Console console = new Console();

    public Pump(String name, PumpType type, int speed, int pinNumber1, int pinNumber2) {
        this.name = name;
        this.type = type;
        this.speed = speed;
        this.pinNumber1 = pinNumber1;
        this.pinNumber2 = pinNumber2;
        Comparator<Action> com = actionComparator();
        aq = new PriorityQueue<Action>(com);
        System.out.println("pump" + name + "loaded !");
    }
    Comparator<Action> actionComparator(){
        Comparator<Action> actionComparator = new Comparator<Action>() {
            @Override
            public int compare(Action a1, Action a2) {
                if(a1.getStartTime() - a2.getStartTime() < 0){
                    return -1;
                }else if (a1.getStartTime() - a2.getStartTime() == 0){
                    return 0;
                }else {
                    return 1;
                }
            }
        };
        return  actionComparator;
    }
    void initEq(){
        eq = new PriorityQueue<>(aq);
    }


    public Pump(String name, String typeName, int pinNumber1, int pinNumber2) {
        this.name = name;
        this.type = new PumpType(typeName);
        
        this.pinNumber1= pinNumber1;
        this.pinNumber2= pinNumber2;
        
        this.Pin1 = Util.getAllPins()[pinNumber1];
        this.Pin2 = Util.getAllPins()[pinNumber2];
        
//        com.pi4j.wiringpi.Gpio.pwmSetMode(com.pi4j.wiringpi.Gpio.PWM_MODE_MS);
//        com.pi4j.wiringpi.Gpio.pwmSetRange(this.type.getRange());
//        com.pi4j.wiringpi.Gpio.pwmSetClock(this.type.getClock());
        Util.getGpio().setShutdownOptions(true, PinState.LOW);

        
//        com.pi4j.wiringpi.Gpio.pwmSetMode(com.pi4j.wiringpi.Gpio.PWM_MODE_MS);
//        com.pi4j.wiringpi.Gpio.pwmSetMode(Gpio.PWM_OUTPUT);
        Gpio.wiringPiSetup();
        com.pi4j.wiringpi.Gpio.pwmSetRange(this.type.getRange());
        com.pi4j.wiringpi.Gpio.pwmSetClock(this.type.getClock());

        SoftPwm.softPwmCreate(pinNumber1,0,100);

        
    }
////////////getter /////////////////////
    public String getName() {
        return name;
    }

    public PumpType getType() {
        return type;
    }

    public Pin getPin1() {
        return Pin1;
    }

    public Pin getPin2() {
        return Pin2;
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

    public void setPin1(int p1) {
        this.pinNumber1 = p1;
        this.Pin1 = Util.getAllPins()[pinNumber1];
    }

    public void setPin2(int p2) {
        this.pinNumber2 = p2;
        this.Pin2 = Util.getAllPins()[pinNumber2];
    }


    ////////////running methord///////////(hardware PWM deprecated )
//    private void  setEnv(){
//        gpio = GpioFactory.getInstance();
//        com.pi4j.wiringpi.Gpio.pwmSetMode(com.pi4j.wiringpi.Gpio.PWM_MODE_MS);
//        com.pi4j.wiringpi.Gpio.pwmSetRange(this.type.getRange());
//        com.pi4j.wiringpi.Gpio.pwmSetClock(this.type.getClock());
//        // PIN1为正PIN2为负
//         pwm = gpio.provisionPwmOutputPin(Pin1);
//         g = gpio.provisionDigitalOutputPin(Pin2,PinState.LOW);
//    }
//    //run methord
//    public void setPinHigh(Pin pin){
//        GpioPinPwmOutput  pwm  = gpio.provisionPwmOutputPin(pin);
//        // speed = dutycycle
//        pwm.setPwm(type.getRange()*(speed /100));
//    }
//    // stop all GPIO activity/threads by shutting down the GPIO controller
//    // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
//    //Stop the pump
//    public void setPinLow(Pin pin) {
//        gpio.provisionDigitalOutputPin(pin);
//
//    }
//    GpioPinPwmOutput pwm;
//    GpioPinDigitalOutput g;
//    public void run(){
//        gpio = GpioFactory.getInstance();
//        gpio.setShutdownOptions(true, PinState.LOW);
//        com.pi4j.wiringpi.Gpio.pwmSetMode(com.pi4j.wiringpi.Gpio.PWM_MODE_MS);
//        com.pi4j.wiringpi.Gpio.pwmSetRange(this.type.getRange());
//        com.pi4j.wiringpi.Gpio.pwmSetClock(this.type.getClock());
//        // PIN1为正PIN2为负
//
//         pwm = gpio.provisionPwmOutputPin(Pin1);
//         g = gpio.provisionDigitalOutputPin(Pin2,PinState.LOW);
//
//        System.out.println("frequency now = "+ 19.2e6/type.getClock()/type.getRange());
//        pwm.setPwm(0);
//        g.low();
//        pwm.setPwm(type.getRange()*(speed/100));
//    }
//    GpioPinPwmOutput pwm ;
//    GpioPinDigitalOutput g ;
    
    public void run(){
        run(speed);
    }
    public void revertrun(){
        revertrun(speed);
    }
    
    public void run(int speed){
        initServo();
        stop(pinNumber2);
        provisionPin(pinNumber1, 0 , type.getRange());
        System.out.println("frequency now = "+ 19.2e6/type.getClock()/type.getRange());
            try{
                start(pinNumber1,type.getRange()*speed /100 );
            }catch(Exception e ){
                e.printStackTrace();
            }
    }
    public void provisionPin(int pin ,int base , int range){
        SoftPwm.softPwmCreate(pin, base , range);
    }
    public void start(int pinNumber , int dutyCycle){
        SoftPwm.softPwmWrite(pinNumber,dutyCycle);
    }
    public void stop(int pinNumber){
        SoftPwm.softPwmStop(pinNumber);
    }
    public void shutdown(){
        stop(pinNumber1);
        stop(pinNumber2);
    }

    void initServo(){
        Gpio.wiringPiSetup();
    }

    
    public void revertrun(int speed ){
        initServo();
        stop(pinNumber1);
        SoftPwm.softPwmCreate(pinNumber2, 0 , type.getRange());
        System.out.println("REVERSED :: frequency now = "+ 19.2e6/type.getClock()/type.getRange());
        try{
            SoftPwm.softPwmWrite(pinNumber2,speed);
        }catch(Exception e ){
            e.printStackTrace();
        }
    }
//    public void stop(){
//        pwm.setPwm(0);
//        g.low();
//        Util.getGpio().shutdown();
//    }
    //@Override
    //Pump as String Output
    public String toString(){
        
        return (type.getName()+"/"+"pin1:" + pinNumber1+"/"+"pin2:" + pinNumber2+"/"+this.speed+"%");
    }


    
    
    
}
