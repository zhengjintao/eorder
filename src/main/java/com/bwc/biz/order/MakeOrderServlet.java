package com.bwc.biz.order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class MakeOrderServlet
 */
public class MakeOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MakeOrderServlet() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = request.getParameter("mode");
		if("init".equals(mode)){
			this.init(request, response);
		}else if("submit".equals(mode)){
			this.submit(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**
	 * 初期化処理
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void init(HttpServletRequest request, HttpServletResponse response) throws IOException{
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject subjsonObject = new JSONObject();
		subjsonObject.put("id", "0001");
		subjsonObject.put("text", "learn AngularJS");
		subjsonObject.put("img", "asserts/images/lunch.jpeg");
		subjsonObject.put("price", "199");
		
		JSONObject subjsonObject2 = new JSONObject();
		subjsonObject2.put("id", "0002");
		subjsonObject2.put("text", "learn AngularJS");
		subjsonObject2.put("img", "asserts/images/lunch.jpeg");
		subjsonObject2.put("price", "299");
		
		JSONObject subjsonObject3 = new JSONObject();
		subjsonObject3.put("id", "0003");
		subjsonObject3.put("text", "learn AngularJS");
		subjsonObject3.put("img", "asserts/images/lunch.jpeg");
		subjsonObject3.put("price", "399");
		
		JSONObject subjsonObject4 = new JSONObject();
		subjsonObject4.put("id", "0004");
		subjsonObject4.put("text", "learn AngularJS");
		subjsonObject4.put("img", "asserts/images/lunch.jpeg");
		subjsonObject4.put("price", "399");
		
		list.add(subjsonObject);
		list.add(subjsonObject2);
		list.add(subjsonObject3);
		list.add(subjsonObject4);
		
		JSONArray array = new JSONArray();
		JSONArray subarray = new JSONArray();
		int i=0,inx=0;
		for(JSONObject obj : list){
			subarray.put(i,obj);
			i++;
			if(i==3){
				i=0;
				
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("row", subarray);
				array.put(inx, jsonObject);
				subarray = new JSONArray();
				inx++;
			}
		}
		
		if(subarray.length() >0){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("row", subarray);
			array.put(inx, jsonObject);
		}
		
		
        response.getWriter().write(array.toString());
	}
	
	/**
	 * 注文
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void submit(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String orders = request.getParameter("orders");
		JSONArray array = new JSONArray(orders);
		for(int i=0; i < array.length(); i++){
			JSONObject order = (JSONObject)array.get(i);
			System.out.println(order);
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", "OK");  
		response.setCharacterEncoding("utf-8");  
        response.getWriter().write(jsonObject.toString());
	}
}
