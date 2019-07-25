
import com.google.gson.Gson;
import com.pi4j.io.gpio.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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




    //决定按需求在主页面用Gson进行parse
    //parse json --> pump
    static void jsonStringToObj(String json){

    }
    
    //parse pumptype li --> json
    static String ObjToJsonString(Object type){
        Gson json = new Gson();
        return json.toJson(type);
    }

    public static void main (String [] args){

        PumpType type = new PumpType("OW",40);
        PumpType type2 = new PumpType("QC",20);
        ArrayList<PumpType> pumpTypes = new ArrayList<>();
        pumpTypes.add(type);
        pumpTypes.add(type2);

        String out =  Util.ObjToJsonString(pumpTypes);
        System.out.println(out);

        Gson json = new Gson();
        ArrayList parsedList = json.fromJson(out,ArrayList.class);
        System.out.println(Arrays.toString(parsedList.toArray()));

    }
    
    
}
