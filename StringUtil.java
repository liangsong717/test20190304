package com.fineway.springbootdemo.utils;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class StringUtil {

    public static String createInsertSQL(String tableName,List datas) {
        StringBuilder sqls=new StringBuilder();
        StringBuilder sqlField=null;
        StringBuilder sqlValue=null;
        if(datas==null||datas.size()==0){return null;}
        String sqlModel ="Insert into "+tableName+" (<%FIELDS%>) values (<%RECORDS%>);\n";
        for (Object data:datas) {
            try {
                String sql=sqlModel;
                sqlField= new StringBuilder();
                sqlValue=new StringBuilder();
                Field[] fields = data.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
//                    System.out.print("字段名: "+field.getName()+"\t\t");
//                    System.out.println("值："+field.get(data));
                    sqlField.append(field.getName().toUpperCase()+",");
                    Object value = field.get(data);
                    if(value instanceof String){
                        sqlValue.append(value==null?"null,":("'"+((String) value).trim()+"',"));
                    }else if(value instanceof Date){
                        Date date = (Date) value;
                        sqlValue.append(value==null?"null,":("to_timestamp('"+StringUtil.dateToString(date,"yyyy-MM-dd HH:mm:ss")+"','yyyy-MM-dd HH:mi:ss'),"));
                    }else if(value instanceof BigDecimal){
                        sqlValue.append(value==null?"null,":("to_number('"+((BigDecimal) value).doubleValue()+"'),"));
                    }else{
                        sqlValue.append(value==null?"null,":("'"+value+"',"));
                    }
                }
                if (sqlField.lastIndexOf(",")==(sqlField.length()-1)&&sqlValue.lastIndexOf(",")==(sqlValue.length()-1)) {
                    sqlField.deleteCharAt(sqlField.length()-1);
                    sqlValue.deleteCharAt(sqlValue.length()-1);
                    sql=sql.replaceAll("<%FIELDS%>",sqlField.toString());
                    sql=sql.replaceAll("<%RECORDS%>",sqlValue.toString());
                    //System.out.println("sql:"+sql);
                    sqls.append(sql);
                }
            } catch (IllegalAccessException e) {
                System.out.println("【记录error.txt】反射异常："+ JSON.toJSONString(data)+"");
                FileUtil.writeErrorMsg(JSON.toJSONString(data)+"\n"+e.getMessage());
            }
        }
        //System.out.println(sqls.toString());
        return sqls.toString();
    }

    public static String dateToString(Date date, String format) {
        if(date==null||format==null||"".equals(format)){}
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String dateStr = dateFormat.format(date);
        return dateStr;
    }

    public static String trueFalseToYN(String bool) {
        if(bool==null){
            return null;
        }else if("true".equals(bool.trim())){
            return "是";
        }else if("false".equals(bool.trim())){
            return "否";
        }else{
            return bool.trim();
        }
    }
    public static String YNToTrueFalse(String bool) {
        if(bool==null){
            return null;
        }else if("是".equals(bool.trim())){
            return "true";
        }else if("否".equals(bool.trim())||"无".equals(bool.trim())){
            return "false";
        }else{
            return bool.trim();
        }
    }

    public static String getYearAndMonth(Date date) {
        if (date==null){return null;}
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateFormat = format.format(date);
        return dateFormat.substring(0,7);
    }

    public static String getYearAndMonth(String dateStr) {
        if (dateStr==null||dateStr.length()<7){return null;}
        return dateStr.substring(0,7);
    }

    public static String getEarlyMonth(String yearMonth){
        if(yearMonth==null||yearMonth.length()<7){return null;}
        int year = Integer.parseInt(yearMonth.substring(0,4));
        int month = Integer.parseInt(yearMonth.substring(5,7));
        if(month==1){
            return (year-1)+"-12";
        }else{
            if(month>=11){
                return year+"-"+(month-1);
            }else{
                return year+"-0"+(month-1);
            }
        }
    }

    public static String replaceAToBAndFormat(String string,String a,String b) {
        if (string==null){return null;}
        string=string.trim().replaceAll(a,b);
        String regExp="^[0-9]{2}$";
        if(string.lastIndexOf(b)==-1&&string.matches("^[0-9]{6,}$")){
            if(string.length()==8){
                string=string.substring(0,4)+b+string.substring(4,6)+b+string.substring(6,8);
            }else{
                string=string.substring(0,4)+b+string.substring(4,6);
            }
        }else if (string.lastIndexOf(b)==string.length()-1){
            string=string.substring(0,string.length()-1);
        }
        if(string.length()<7||!string.substring(5,7).matches(regExp)){
            string=string.replaceFirst("-","-0");
        }
        if(string.length()==9||!string.substring(string.length()-2,string.length()).matches(regExp)){
            string=string.substring(0,string.length()-1)+"0"+string.substring(string.length()-1);
        }
        return string;
    }

    public static String getUUID32(){
        return UUID.randomUUID().toString().replaceAll("-","").toUpperCase();
    }

    /**
     * 二次解码
     * @param text
     * @return
     */
    public static String doubleDecode(String text){
        return URLDecoder.decode(URLDecoder.decode(text));
    }

    /**
     * 利用JSON将Map转为Java对象
     * @param map
     * @param clasz
     * @param <T>
     * @return
     */
    public static <T> T mapToObject(Map map,Class<T> clasz){
        T t = JSON.parseObject(JSON.toJSONString(map), clasz);
        return t;
    }
}
