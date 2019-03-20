package com.fineway.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSONObject;

@ServerEndpoint("/webSocket/{username}")
public class WebSocketDemo {

	private static int onlineCount = 0; 
    private static Map<String, WebSocketDemo> clients = new ConcurrentHashMap<String, WebSocketDemo>(); 
    private Session session; 
    private String username; 
       
    @OnOpen 
    public void onOpen(@PathParam("username") String username, Session session) throws IOException { 
   
        this.username = username; 
        this.session = session; 
        System.out.println("用户名:"+username+"\t\tID:"+session.getId());   
        addOnlineCount(); 
        clients.put(username, this);
        System.out.println("已连接");
    } 
   
    @OnClose 
    public void onClose() throws IOException { 
        clients.remove(username); 
        System.out.println("【"+username+"】断开连接");
        subOnlineCount(); 
    } 
   
    @OnMessage 
    public void onMessage(String message) throws IOException { 
   
        JSONObject jsonTo = JSONObject.parseObject(message); 
        String mes = (String) jsonTo.get("message");
         
        if (!jsonTo.get("To").equals("All")){ 
        	System.out.println("发送给【"+jsonTo.get("To").toString()+"】");
            sendMessageTo(mes, jsonTo.get("To").toString()); 
        }else{ 
            sendMessageAll(mes); 
        } 
    } 
   
    @OnError 
    public void onError(Session session, Throwable error) { 
        error.printStackTrace(); 
    } 
   
    public void sendMessageTo(String message, String To) throws IOException { 
        // session.getBasicRemote().sendText(message); 
        //session.getAsyncRemote().sendText(message); 
    	SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (WebSocketDemo item : clients.values()) { 
            if (item.username.equals(To) ) 
                item.session.getAsyncRemote().sendText("【"+s.format(new Date())+"】"+message); 
        } 
    } 
       
    public void sendMessageAll(String message) throws IOException { 
    	SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (WebSocketDemo item : clients.values()) { 
            item.session.getAsyncRemote().sendText("【"+s.format(new Date())+"】"+message); 
        } 
        System.out.println("发送人数:"+clients.values().size());
    } 
   
    public static synchronized int getOnlineCount() { 
        return onlineCount; 
    } 
   
    public static synchronized void addOnlineCount() { 
        WebSocketDemo.onlineCount++; 
    } 
   
    public static synchronized void subOnlineCount() { 
        WebSocketDemo.onlineCount--; 
    } 
   
    public static synchronized Map<String, WebSocketDemo> getClients() { 
        return clients; 
    }  
}
