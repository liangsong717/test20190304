package com.fineway.httpTest;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

	public static CloseableHttpClient httpClient=HttpClients.createDefault();


	/**
     * 简单使用get请求（同ajax get请求效果）
     * @param url
     * @return
     * @throws IOException 
     * @throws ClientProtocolException 
     */
    public static String sendHttpGet(String url) throws ClientProtocolException, IOException {
    	HttpGet get =new HttpGet(url);
		CloseableHttpResponse response=httpClient.execute(get);
		//System.out.println("token验证接口响应状态:"+response.getStatusLine());
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity, "UTF-8");
	}



	/**
	 * 发送POST请求
	 * application/json;charset=UTF-8
	 * @param url
	 * @param body
	 * @return
	 * @throws IOException
	 */
    public static String sendJSONHttpPost(String url,String body) throws IOException {
		HttpPost post = new HttpPost(url);
		// 解决中文乱码问题
		StringEntity stringEntity = new StringEntity(body, "UTF-8");
		stringEntity.setContentEncoding("UTF-8");
		post.addHeader("Content-Type", "application/json;charset=UTF-8");
		post.setEntity(stringEntity);
		CloseableHttpResponse response = HttpClients.createDefault().execute(post);
		return EntityUtils.toString(response.getEntity(),"UTF-8");
	}
    
    /**
     * 显示请求header
     * @param request
     */
    public void printHttpHeaders(HttpServletRequest request) {
    	Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            System.out.println("Header【"+key+"】【"+value+"】");
        }
	}
    
    /**
     * 以Map形式获取Http请求Header
     * @param request
     * @return
     */
    public Map<String, Object> getHttpHeaders(HttpServletRequest request) {
    	Map<String, Object> map = new HashMap<>();
    	Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            //System.out.println("Header【"+key+"】【"+value+"】");
            map.put(key, value);
        }
        return map;
	}
}
