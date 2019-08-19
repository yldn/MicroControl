
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import com.pi4j.io.gpio.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.pi4j.io.gpio.RaspiPin.allPins;
import static com.pi4j.system.SystemInfo.BoardType.RaspberryPi_3B_Plus;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author liuyang
 *
 */

public class Util {
    static GpioController gpio = GpioFactory.getInstance();
    

    public static GpioController getGpio() {
        
        return gpio;
    }

    public static Pin[] getAllPins() {
         Pin[] allPins = allPins(RaspberryPi_3B_Plus);
        return allPins;
    }

    //决定按需求在主页面用Gson进行parse
    //parse json --> pump
    static Object jsonStringToObj(String str,Class t){
        Gson json = new Gson();
        Object x = json.fromJson(str,t);
        return x;
    }
    
    //parse pumptype li --> json
    static String objToJsonString(Object o){
        Gson json = new Gson();
        return json.toJson(o);
    }
    static <T> List<T> stringToArray(String s , Class<T[]> clazz){
        T[] arr = new Gson().fromJson(s,clazz);
        return Arrays.asList(arr);

    }
    static <T> T stringToObj(String s , Class<T> classs){{
        T a = new Gson().fromJson(s,classs);
        return a ;

    }}
    
}
