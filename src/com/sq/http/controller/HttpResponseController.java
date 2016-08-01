package com.sq.http.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Http��Ӧ������
 * 
 * @User yaowenjie
 * @Date 2016-7-21
 * @Time ����3:14:34
 */
@Controller
public class HttpResponseController {
	
	/**
	 * ����Get����
	 * 
	 * @param req
	 * @param resp
	 */
	@ResponseBody
	@RequestMapping("http/httpAcceptGetRequest.do")
	public void httpAcceptGetRequest(HttpServletRequest req, HttpServletResponse resp) {
		try {
			System.out.println("���յ���");
			resp.setContentType("text/html;charset=GBK");
			String str2 = "Http�������˷��͵���Ϣ";
			resp.getWriter().println(str2);
		} catch (Exception e) {
			System.err.println("Exception");
		}
	}
	
	/**
	 * ����Post����
	 * 
	 * @param req
	 * @param resp
	 */
	@ResponseBody
	@RequestMapping("http/httpAcceptPostRequest.do")
	public void httpAcceptPostRequest(HttpServletRequest req, HttpServletResponse resp) {
		try {
			InputStream is = req.getInputStream();  
			BufferedReader br = new BufferedReader(new InputStreamReader(is));  
            String line = null;  
            StringBuilder sb = new StringBuilder();  
            while ((line = br.readLine()) != null) {  
                sb.append(line);  
            } 
            String [] str = sb.toString().split("&");
            Map<String,String> map = new HashMap<String,String>();
            for(String s : str){
            	map.put(s.split("=")[0], s.split("=")[1]);
            }
            System.out.println(map.toString());  
            Thread.sleep(100000);
			resp.setContentType("text/html;charset=GBK");
			String str2 = "Http�������˷��͵���Ϣ-->";
			resp.getWriter().println(str2);
		} catch (Exception e) {
			System.out.println("Exception");
		}
	}
}
