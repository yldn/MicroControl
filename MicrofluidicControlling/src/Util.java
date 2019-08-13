
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

    public static void main (String [] args){

        PumpType type = new PumpType("OW");
        PumpType type2 = new PumpType("QC",100,100,100);
        ArrayList<PumpType> pumpTypes = new ArrayList<>();
        pumpTypes.add(type);
        pumpTypes.add(type2);

        String out =  Util.objToJsonString(pumpTypes);
        System.out.println(out);

//        Gson json = new Gson();
//        ArrayList parsedList = json.fromJson(out,ArrayList.class);
        ArrayList parsedList = (ArrayList)jsonStringToObj(out,ArrayList.class);

        System.out.println(parsedList.get(1));

    }

//    class ResponseDataTypeAdaptor extends TypeAdapter<ResponseDataTypeAdaptor> {
//
//        public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
//            @SuppressWarnings("unchecked")
//            @Override
//            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
//                if (type.getRawType() == ResponseDataTypeAdaptor.class) {
//                    return (TypeAdapter<T>) new ResponseDataTypeAdaptor(gson);
//                }
//                return null;
//            }
//        };
//
//        private final Gson gson;
//
//        ResponseDataTypeAdaptor(Gson gson) {
//            this.gson = gson;
//        }
//
//        @Override
//        public void write(JsonWriter out, Response value) throws IOException {
//            if (value == null) {
//                out.nullValue();
//                return;
//            }
//
//            out.beginObject();
//            out.name("status");
//            gson.getAdapter(Integer.class).write(out, value.getStatus());
//            out.name("msg");
//            gson.getAdapter(String.class).write(out, value.getMsg());
//            out.name("data");
//            gson.getAdapter(Object.class).write(out, value.getData());
//            out.endObject();
//        }
//
//        @Override
//        public ResponseData read(JsonReader in) throws IOException {
//            ResponseData data = new ResponseData();
//            Map<String, Object> dataMap = (Map<String, Object>) readInternal(in);
//            data.setStatus((Integer) dataMap.get("status"));
//            data.setMsg((String) dataMap.get("msg"));
//            data.setData(dataMap.get("data"));
//            return data;
//        }
//
//
//        private Object readInternal(JsonReader in) throws IOException {
//            JsonToken token = in.peek();
//            switch (token) {
//                case BEGIN_ARRAY:
//                    List<Object> list = new ArrayList<Object>();
//                    in.beginArray();
//                    while (in.hasNext()) {
//                        list.add(readInternal(in));
//                    }
//                    in.endArray();
//                    return list;
//
//                case BEGIN_OBJECT:
//                    Map<String, Object> map = new LinkedTreeMap<String, Object>();
//                    in.beginObject();
//                    while (in.hasNext()) {
//                        map.put(in.nextName(), readInternal(in));
//                    }
//                    in.endObject();
//                    return map;
//
//                case STRING:
//                    return in.nextString();
//
//                case NUMBER:
//                    String numberStr = in.nextString();
//                    if (numberStr.contains(".") || numberStr.contains("e")
//                            || numberStr.contains("E")) {
//                        return Double.parseDouble(numberStr);
//                    }
//                    if (Long.parseLong(numberStr) <= Integer.MAX_VALUE) {
//                        return Integer.parseInt(numberStr);
//                    }
//                    return Long.parseLong(numberStr);
//
//                case BOOLEAN:
//                    return in.nextBoolean();
//
//                case NULL:
//                    in.nextNull();
//                    return null;
//
//                default:
//                    throw new IllegalStateException();
//            }
//        }
//    }
}
