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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    public static void main(String[] args) {
        try{
//           String dateTime = "30:44:123";
//           StringTokenizer st = new StringTokenizer(dateTime, ":");
//           long out = 0;
//           int i = 1;
//        while (st.hasMoreTokens()) {
//            if(i == -1){
//                out+= Long.parseLong(st.nextToken());
//                System.out.println(out);
//            }else {
//                out += Long.parseLong(st.nextToken())* ((60*i ==0)?60*i:1)*1000;
//                System.out.println(out);
//            }
//            i--;
        long out = 0;
        String str = "30:44:123"; 
        String[] arrOfStr = str.split(":", 0); 

        out = Long.parseLong(arrOfStr[0])*60000;
        out += Long.parseLong(arrOfStr[1])*1000;
        out += Long.parseLong(arrOfStr[2]);
         System.out.println(out);
        
        
           
//           System.out.println(""+format);
           
//           System.out.println("日期对应毫秒：" +getlongFromString(dateTime));
           
        }catch(Exception e ){
            e.printStackTrace();
        }
    }
    public static void test() throws ParseException {
    String dateTime = "30:44:123";
    Calendar calendar = Calendar.getInstance();
    
    SimpleDateFormat format = new SimpleDateFormat("mm:ss:SSS");
    
    
    calendar.setTime(new SimpleDateFormat("mm:ss SSS").parse(dateTime));
    System.out.println("日期[]对应毫秒：" + calendar.getTimeInMillis());
    }
    
    public static long getlongFromString(String time) throws ParseException{
        if (time == null || time.equals(""))
        {
            return 0;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss:SSS");//24小时制
	return simpleDateFormat.parse(time).getTime();
        
    }
    
//    public static void main(String[] args) {
//        Relatives rela = new Relatives("Jack","God Father",89);
//        Gson gson = new Gson();
//        String str = gson.toJson(gson);
//
//
//    //Create a list of relatives object.
//        List<Relatives> relativeList = new ArrayList<>();
//        relativeList.add(new Relatives("John", "Father", 50));
//        relativeList.add(new Relatives("Mary", "Mother", 45));
//        relativeList.add(new Relatives("Johny", "Brother", 25));
//        relativeList.add(new Relatives("Gena", "Sister", 50));
//        str += gson.toJson(relativeList);
//
//        System.out.println(str);
//
//
//        PrintWriter writer;
//        try {
//             writer = new PrintWriter("out.json","UTF-8");
//             writer.println(str);
//             writer.close();
//
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
