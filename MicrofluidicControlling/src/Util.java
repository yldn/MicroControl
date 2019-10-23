
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import com.pi4j.io.gpio.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.pi4j.io.gpio.RaspiPin.allPins;
import static com.pi4j.system.SystemInfo.BoardType.RaspberryPi_3B_Plus;
import java.util.Map;
import java.util.Set;

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
//    static GpioController gpio = GpioFactory.getInstance();
//    public static GpioController getGpio() {
//        
//        return gpio;
//    }

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

    public static Configuation parseJsontoConfig(String json ) throws Exception{

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        ////////
        //parse type classes 
        JsonArray typesjson = jsonObject.getAsJsonArray("types");
        Iterator it = typesjson.iterator();
        ArrayList<PumpType> types = new ArrayList<PumpType>();
        while(it.hasNext()){
            JsonElement e = (JsonElement) it.next();
            JsonObject t1 = e.getAsJsonObject();
            //generate Pumptype class
            String Name = t1.get("Name").getAsString();
            int range = t1.get("range").getAsInt();
            int minSpeed = t1.get("minSpeed").getAsInt();

            PumpType t = new PumpType(Name,range,minSpeed);
            types.add(t);
            System.out.println(t.toString());
        }
        //Error 499--type Empty
        if(types.size()==0){
            throw new Exception("Error : 499");
        }
        ////////
        //parse Pump class
        JsonArray pumpsjson = jsonObject.getAsJsonArray("pumps");
//        Set<Map.Entry<String, JsonElement>> entries = pumpsjson.getAsJsonObject().entrySet();
        ArrayList<Pump> pumps = new ArrayList<Pump>();
//        for (Map.Entry<String, JsonElement> entry: entries) {
//            System.out.println(entry.getKey()+":"+entry.getValue().toString());
//        }
        it = pumpsjson.iterator();
        while(it.hasNext()){
            JsonElement e = (JsonElement) it.next();
            JsonObject p1 = e.getAsJsonObject();

            ///generate Pump class
            String pname = p1.get("name").getAsString();
            JsonObject tt = p1.get("type").getAsJsonObject();
            ///Error 500---no such Type
            if( findTypeByName(types , tt.get("Name").getAsString()) == -1 ){
                throw new Exception("Error : 500");
            }
            PumpType t = types.get(findTypeByName(types , tt.get("Name").getAsString()  )) ;
            int speed = p1.get("speed").getAsInt();
            int pinNumber1 = p1.get("pinNumber1").getAsInt();
            int pinNumber2 = p1.get("pinNumber2").getAsInt();

            Pump p = new Pump(pname,t,speed,pinNumber1,pinNumber2);

            pumps.add(p);
        }
        ///////////
        //parse Action Class
        JsonArray actionsjson = jsonObject.getAsJsonArray("actions");
        ArrayList<Action> actions = new ArrayList<Action>();
        it = actionsjson.iterator();
        while(it.hasNext()){
            JsonElement e = (JsonElement) it.next();
            JsonObject a1 = e.getAsJsonObject();

            int seq =a1.get("seq").getAsInt();
            long startTime=a1.get("startTime").getAsLong();
            long endTime=a1.get("endTime").getAsLong();
            int speed=a1.get("speed").getAsInt();
            boolean reverse=a1.get("reverse").getAsBoolean();
            if( findPumpByName(pumps,a1.get("p").getAsJsonObject().get("name").getAsString()) == -1 ){
                throw new Exception("Error : 500");
            }
            Pump p = pumps.get(findPumpByName(pumps,a1.get("p").getAsJsonObject().get("name").getAsString()));


//            Set<Map.Entry<String, JsonElement>> entries = e.getAsJsonObject().entrySet();
//
//
//            for (Map.Entry<String, JsonElement> entry: entries) {
////            System.out.println(entry.getKey()+":--->"+entry.getValue().toString());
//                switch (entry.getKey()) {
//                    case "seq": seq = entry.getValue().getAsInt();
//                            break;
//                    case "startTime": startTime = entry.getValue().getAsLong();
//                        break;
//                    case "endTime": endTime = entry.getValue().getAsLong();
//                        break;
//                    case "speed": speed = entry.getValue().getAsInt();
//                        break;
//                    case "reverse": reverse = entry.getValue().getAsBoolean();
//                        break;
//                    case "isActive":
//                        isActive = entry.getValue().getAsBoolean();
//                        break;
//
//                    case "p":
////                        p = new Gson().fromJson(entry.getValue().getAsJsonObject(),Pump.class);
//                        int index = findPumpByName(pumps,entry.getValue().getAsJsonObject().get("name").getAsString());
//                                if(index ==-1){
//                                    ///Error 501---no such Type]
//                                    break;
//                                }
//                           p= pumps.get(index) ;
//                        break;
//
//                        default:
//
//                }
//
//            }
//            //Error 498 incompatible Error
//            if(p.equals(null)){
//
//            }

            
            Action a = new Action(seq,startTime,endTime,speed,p,reverse);
            ///Error 501---no such Type
//            if(findPumpByName(pumps , a.getP().getName()) == -1 ){
//
//            }
//            System.out.println(a.getP().getAq().toString());
//            System.out.println("p1");
            actions.add(a);
        }
        
//        System.out.println(Arrays.toString(types.toArray()));
//        System.out.println(Arrays.toString(pumps.toArray()));
//        System.out.println(Arrays.toString(actions.toArray()));
        
        
        Configuation config = new Configuation(name,types,pumps,actions);

        return config;
    }
    private static int findTypeByName(ArrayList<PumpType> typelist, String str){
        if(str == ""|| str == null){
            System.out.println("can't find the pump the given pump name is null");
            return -1;
        }
        for(int i=0 ; i < typelist.size();i++){
            PumpType t = typelist.get(i);
            if( t.getName().equals(str) ){
                return i;
            }
        }
        return -1;
    }
    private static int findPumpByName(ArrayList<Pump> pumpList, String str) throws Exception{
        if(str.equals("")|| str == null){
            System.out.println("can't find the pump the given pump name is null");
            return -1;
        }
        for(int i=0 ; i < pumpList.size();i++){
            Pump p = pumpList.get(i);
            if( p.getName().equals(str) ){
                return i;
            }
        }
        return -1;
    }
}
