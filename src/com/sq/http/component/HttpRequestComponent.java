package com.sq.http.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

/**
 * Http协议发送请求组件
 * 
 * @User yaowenjie
 * @Date 2016-7-21
 * @Time 下午2:50:05
 */
@Component
@SuppressWarnings("deprecation")
public class HttpRequestComponent {
	
	public static HttpClient client = new DefaultHttpClient();
	
	/** 4.3版本创建HttpClient */
	public static CloseableHttpClient httpClient = HttpClients.createDefault();
	
	/**
	 * 以Get方式发送请求
	 * 
	 * @param url
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static String getRequest(final String url) throws Exception{
		FutureTask<String> future = new FutureTask<String>(
				new Callable<String>(){
					public String call() throws Exception {
						/** 4.3版本后设置请求和传输超时时间 */
						RequestConfig requestConfig = RequestConfig.custom()
								.setSocketTimeout(8000)
								.setConnectTimeout(8000).build();
						HttpGet get = new HttpGet(url);
						get.setConfig(requestConfig);
						/** 发送 get 请求    */
						HttpResponse response = httpClient.execute(get);
						/** 服务器响应请求 */
						if(response.getStatusLine().getStatusCode() == 200){
							/** 获取服务器响应的字段 */
							String result = EntityUtils.toString(response.getEntity());
							return result;
						}
						return "通讯失败";
					}});
		new Thread(future).start();
		return future.get();
	}
	
	/**
	 * post方式发送http请求
	 * 
	 * @param url
	 * @param rawMap
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static String postRequest(final String url,final Map<String,String> rawMap) throws Exception{
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,6000);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,6000);
		FutureTask<String> future = new FutureTask<String>(
				new Callable<String>(){
					public String call() throws Exception {
						HttpPost post = new HttpPost(url);
						//封装传递的参数
						List<NameValuePair> list = new ArrayList<NameValuePair>();
						for(String keys : rawMap.keySet()){
							list.add(new BasicNameValuePair(keys,rawMap.get(keys)));
						}
						post.setEntity(new UrlEncodedFormEntity(list,"gbk"));
						/** 发送post请求 */
						HttpResponse response = client.execute(post);
						/** 服务器响应请求 */
						if(response.getStatusLine().getStatusCode() == 200){
							/** 获取服务器响应的字段 */
							String result = EntityUtils.toString(response.getEntity());
							return result;
						}
						return "通讯失败";
					}});
		new Thread(future).start();
		return future.get();
	}
}
