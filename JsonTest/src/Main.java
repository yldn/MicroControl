/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author liuyang
 */

import com.google.gson.Gson;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Main {
    
    
    public static void main(String[] args) {
        Relatives rela = new Relatives("Jack","God Father",89);
        Gson gson = new Gson();
        String str = gson.toJson(gson);
        
    
    //Create a list of relatives object.
        List<Relatives> relativeList = new ArrayList<>();
        relativeList.add(new Relatives("John", "Father", 50));
        relativeList.add(new Relatives("Mary", "Mother", 45));
        relativeList.add(new Relatives("Johny", "Brother", 25));
        relativeList.add(new Relatives("Gena", "Sister", 50));
        str += gson.toJson(relativeList);
        
        System.out.println(str);
        
       
        PrintWriter writer;
        try {
             writer = new PrintWriter("out.json","UTF-8");
             writer.println(str);
             writer.close();
             
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
