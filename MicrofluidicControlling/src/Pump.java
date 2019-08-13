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
import static com.pi4j.io.gpio.RaspiPin.allPins;
import static com.pi4j.system.SystemInfo.BoardType.RaspberryPi_3B_Plus;
import com.pi4j.util.Console;
        
public class Pump {
    /////////RASPERRYPI ALL PINS /////////////
    private transient Pin[] allPins = allPins(RaspberryPi_3B_Plus);
    
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
    private transient Pin Pin1;
    private transient Pin Pin2;
    
    //GPIO控制单元
    //GpioController gpio = GpioFactory.getInstance();
    transient GpioController gpio ;
    
    //For Debugging
    transient final Console console = new Console();

    public Pump(String name, String typeName, int pinNumber1, int pinNumber2) {
        this.name = name;
        this.type = new PumpType(typeName);
        
        this.pinNumber1= pinNumber1;
        this.pinNumber2= pinNumber2;
        
        this.Pin1 = allPins[pinNumber1];
        this.Pin2 = allPins[pinNumber2];
        
//        com.pi4j.wiringpi.Gpio.pwmSetMode(com.pi4j.wiringpi.Gpio.PWM_MODE_MS);
//        com.pi4j.wiringpi.Gpio.pwmSetRange(this.type.getRange());
//        com.pi4j.wiringpi.Gpio.pwmSetClock(this.type.getClock());
        
        
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
        this.Pin1 = allPins[pinNumber1];
    }

    public void setPin2(int p2) {
        this.pinNumber2 = p2;
        this.Pin2 = allPins[pinNumber2];
    }
    
    GpioPinPwmOutput pwm;
    //run methord
    public void setPinHigh(Pin pin){
        com.pi4j.wiringpi.Gpio.pwmSetMode(com.pi4j.wiringpi.Gpio.PWM_MODE_MS);
        com.pi4j.wiringpi.Gpio.pwmSetRange(this.type.getRange());
        com.pi4j.wiringpi.Gpio.pwmSetClock(this.type.getClock());
        
        pwm  = gpio.provisionPwmOutputPin(pin);
        // speed = dutycycle
        pwm.setPwm(type.getRange()*(speed /100));
        
        //use Pwm mode
//        pwm.setPwm(500);
//        console.println("PWM rate is: " + pwm.getPwm());
//        console.println("Press ENTER to set the PWM to a rate of 250");
//        System.console().readLine();
//        // set the PWM rate to 250
//        pwm.setPwm(250);
//        console.println("PWM rate is: " + pwm.getPwm());
//        console.println("Press ENTER to set the PWM to a rate to 0 (stop PWM)");
//        System.console().readLine();
//        // set the PWM rate to 0
//        pwm.setPwm(0);
//        console.println("PWM rate is: " + pwm.getPwm());

        
    }
    // stop all GPIO activity/threads by shutting down the GPIO controller
    // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
    //Stop the pump 
    public void setPinLow(Pin pin) {
        gpio.provisionDigitalOutputPin(pin);
        //TODO 设置所有针脚都为低电位 ------- pwm.setPwm(0);

    }
    
    public void run(){
        this.setPinHigh(Pin1);
        this.setPinLow(Pin2);
        
    }
    public void revertrun(){
        this.setPinHigh(Pin1);
        this.setPinLow(Pin2);
        
    }
    public void stop(){
        this.setPinLow(Pin1);
        this.setPinLow(Pin1);
        this.gpio.shutdown();
    }
    //@Override
    //Pump as String Output
    public String toString(){
        
        return (type.getName()+"/"+"pin1:" + pinNumber1+"/"+"pin2:" + pinNumber2+"/"+this.speed+"%");
    }
    
    
    
    
    
}
