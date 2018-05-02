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
		// テストデータ
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject subjsonObject = new JSONObject();
		subjsonObject.put("id", "0001");
		subjsonObject.put("text", "food no1");
		subjsonObject.put("img", "asserts/images/lunch.jpeg");
		subjsonObject.put("price", "199");
		
		JSONObject subjsonObject2 = new JSONObject();
		subjsonObject2.put("id", "0002");
		subjsonObject2.put("text", "delicious food no2");
		subjsonObject2.put("img", "asserts/images/lunch2.jpeg");
		subjsonObject2.put("price", "299");
		
		JSONObject subjsonObject3 = new JSONObject();
		subjsonObject3.put("id", "0003");
		subjsonObject3.put("text", "food no3");
		subjsonObject3.put("img", "asserts/images/lunch3.jpeg");
		subjsonObject3.put("price", "399");
		
		JSONObject subjsonObject4 = new JSONObject();
		subjsonObject4.put("id", "0004");
		subjsonObject4.put("text", "food no4");
		subjsonObject4.put("img", "asserts/images/lunch4.jpeg");
		subjsonObject4.put("price", "499");
		
		JSONObject subjsonObject5 = new JSONObject();
		subjsonObject5.put("id", "0005");
		subjsonObject5.put("text", "food no5");
		subjsonObject5.put("img", "asserts/images/lunch.jpeg");
		subjsonObject5.put("price", "599");
		
		JSONObject subjsonObject6 = new JSONObject();
		subjsonObject6.put("id", "0006");
		subjsonObject6.put("text", "food no6");
		subjsonObject6.put("img", "asserts/images/lunch6.jpeg");
		subjsonObject6.put("price", "699");
		
		list.add(subjsonObject);
		list.add(subjsonObject2);
		list.add(subjsonObject3);
		list.add(subjsonObject4);
		list.add(subjsonObject5);
		list.add(subjsonObject6);
		list.add(subjsonObject4);
		list.add(subjsonObject3);
		list.add(subjsonObject2);
		list.add(subjsonObject);
		list.add(subjsonObject6);
		list.add(subjsonObject5);
		list.add(subjsonObject2);
		list.add(subjsonObject4);
		list.add(subjsonObject3);
		
		String pageIndex = request.getParameter("pageIndex");
		int totalRecord = list.size();
		int pageSize = 6;
		
		List<JSONObject> resultlist = new ArrayList<JSONObject>();
		int startIndex = (Integer.parseInt(pageIndex) -1 )* 6;
		int endIndex = startIndex+6;
		endIndex = endIndex > totalRecord ? totalRecord : endIndex;
		for(int i=startIndex; i< endIndex; i++){
			resultlist.add(list.get(i));
		}
		
		JSONArray array = new JSONArray();
		JSONArray subarray = new JSONArray();
		int i=0,inx=0;
		for(JSONObject obj : resultlist){
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
		
		// 最終結果
		JSONObject result = new JSONObject();
		result.put("goods", array);  // 商品リスト
		result.put("currentPage", pageIndex);  // カレントページ
		result.put("totalPages", (totalRecord + pageSize - 1)/pageSize); // 総ページ数
		
        response.getWriter().write(result.toString());
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
