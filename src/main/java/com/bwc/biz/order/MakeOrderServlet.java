package com.bwc.biz.order;

import java.io.IOException;
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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orders = request.getParameter("orders");
		System.out.println(orders);
		// TODO Auto-generated method stub
		JSONArray array = new JSONArray(orders);
		for(int i=0; i < array.length(); i++){
			JSONObject order = (JSONObject)array.get(i);
			System.out.println(orders);
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", "OK");  
		response.setCharacterEncoding("utf-8");  
        response.getWriter().write(jsonObject.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
