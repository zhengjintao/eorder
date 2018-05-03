package com.bwc.biz.order;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import sun.misc.BASE64Decoder;  
import sun.misc.BASE64Encoder;  

/**
 * Servlet implementation class MakeOrderServlet
 */
public class OrderServicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static List<GoodInfo> goodslist = new ArrayList<GoodInfo>();
	
	static{
		GoodInfo good = new GoodInfo();
		good.setId("0001");
		good.setName("おいしい弁当");
		good.setImgurl("asserts/images/lunch.jpeg");
		good.setPrice("199");
		good.setStatus("0");
		
		GoodInfo good2 = new GoodInfo();
		good2.setId("0002");
		good2.setName("food no2");
		good2.setImgurl("asserts/images/lunch2.jpeg");
		good2.setPrice("299");
		good2.setStatus("0");
		
		GoodInfo good3 = new GoodInfo();
		good3.setId("0003");
		good3.setName("food no3");
		good3.setImgurl("asserts/images/lunch3.jpeg");
		good3.setPrice("399");
		good3.setStatus("0");
		
		GoodInfo good4 = new GoodInfo();
		good4.setId("0004");
		good4.setName("food no4");
		good4.setImgurl("asserts/images/lunch4.jpeg");
		good4.setPrice("499");
		good4.setStatus("0");
		
		GoodInfo good5 = new GoodInfo();
		good5.setId("0005");
		good5.setName("food no5");
		good5.setImgurl("asserts/images/lunch.jpeg");
		good5.setPrice("599");
		good5.setStatus("1");
		
		GoodInfo good6 = new GoodInfo();
		good6.setId("0006");
		good6.setName("food no6");
		good6.setImgurl("asserts/images/lunch6.jpeg");
		good6.setPrice("699");
		good6.setStatus("0");
		
		goodslist.add(good);
		goodslist.add(good2);
		goodslist.add(good3);
		goodslist.add(good4);
		goodslist.add(good5);
		goodslist.add(good6);
	}

    /**
     * Default constructor. 
     */
    public OrderServicServlet() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = request.getParameter("mode");
		if("init".equals(mode)){
			this.init(request, response);
		}else if("order".equals(mode)){
			this.order(request, response);
		}else if("login".equals(mode)){
			this.login(request, response);
		}else if("list".equals(mode)){
			this.list(request, response);
		}else if("listinit".equals(mode)){
			this.listinit(request, response);
		}else if("detail".equals(mode)){
			this.detail(request, response);
		}else if("onsale".equals(mode)){
			this.onsale(request, response);
		}else if("unsale".equals(mode)){
			this.unsale(request, response);
		}else if("detailinit".equals(mode)){
			this.detailinit(request, response);
		}else if("savegood".equals(mode)){
			this.savegood(request, response);
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
		for(GoodInfo good : goodslist){
			if("1".equals(good.getStatus())){
				continue;
			}
			JSONObject subjsonObject = new JSONObject();
			subjsonObject.put("id", good.getId());
			subjsonObject.put("text", good.getName());
			subjsonObject.put("img", good.getImgurl());
			subjsonObject.put("price", good.getPrice());
			list.add(subjsonObject);
		}
		
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
	private void order(HttpServletRequest request, HttpServletResponse response) throws IOException{
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
	
	/**
	 * ログイン処理
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException 
	 */
	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		
		if("admin".equals(userid) && "admin".equals(password)){
			request.getRequestDispatcher("list.html").forward(request, response);
		}else{
			request.getRequestDispatcher("login.html").forward(request, response);
		}
	}
	
	private void listinit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		request.getRequestDispatcher("list.html").forward(request, response);
	}
	
	/**
	 * ログイン処理
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException 
	 */
	private void list(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

		JSONArray onsalearray = new JSONArray();
		JSONArray unsalearray = new JSONArray();
				List<JSONObject> list = new ArrayList<JSONObject>();
				int i=0,j=0;
				for(GoodInfo good : goodslist){
					
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("id", good.getId());
					jsonObject.put("text", good.getName());
					jsonObject.put("img", good.getImgurl());
					jsonObject.put("price", good.getPrice());
					list.add(jsonObject);
					
					if("1".equals(good.getStatus())){
						unsalearray.put(i, jsonObject);
						i++;
					}else{
						onsalearray.put(j, jsonObject);
						j++;
					}
				}
				
				// 最終結果
				JSONObject result = new JSONObject();
				result.put("onsalegoods", onsalearray);
				result.put("unsalegoods", unsalearray);
				
		        response.getWriter().write(result.toString());
	}
	
	/**
	 * ログイン処理
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException 
	 */
	private void detail(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String id = request.getParameter("id");
		if(id==null || id.length()==0){
			id="";
		}
		request.setAttribute("id", id);
		
		request.getRequestDispatcher("detail.jsp").forward(request, response);
	}
	
	/**
	 * ログイン処理
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException 
	 */
	private void detailinit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String id = request.getParameter("id");
		if (id == null || id.length() == 0) {
			id = "";
		}

		JSONObject jsonObject = new JSONObject();
		for (GoodInfo good : goodslist) {
			if (id.equals(good.getId())) {
				jsonObject.put("id", good.getId());
				jsonObject.put("text", good.getName());
				jsonObject.put("img", good.getImgurl());
				jsonObject.put("price", good.getPrice());
			}
		}

		response.getWriter().write(jsonObject.toString());
	}
	
	/**
	 * ログイン処理
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException 
	 */
	private void onsale(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String id = request.getParameter("id");
		
		for(GoodInfo good : goodslist){
			
			if(id.equals(good.getId())){
				good.setStatus("0");
			}
		}
		
		this.list(request, response);
	}
	
	/**
	 * ログイン処理
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException 
	 */
	private void unsale(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String id = request.getParameter("id");
		
        for(GoodInfo good : goodslist){
			
			if(id.equals(good.getId())){
				good.setStatus("1");
			}
		}
        
        this.list(request, response);
		
	}
	
	private void savegood(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String price = request.getParameter("price");
		String img = request.getParameter("img");
		String imgtext = request.getParameter("imgtext").replace("data:image/png;base64,", "");
		
		BASE64Decoder decoder = new BASE64Decoder();  
		//Base64解码  
        byte[] b = decoder.decodeBuffer(imgtext);  
        for(int i=0;i<b.length;++i)  
        {  
            if(b[i]<0)  
            {//调整异常数据  
                b[i]+=256;  
            }  
        }  
            
		String status = "0";
		if (id == null || id.length() == 0) {
			int maxid = 0;
			for (GoodInfo good : goodslist) {
				int goodid = Integer.parseInt(good.getId());
				maxid = maxid > goodid ? maxid : goodid;
			}
			
			id = StringUtil.padLeft(String.valueOf(maxid + 1), 4, '0');
			Date date = new Date();
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddhhmmss");
			String filename= "/"+id + sdf.format(date)+ ".png";
			GoodInfo good = new GoodInfo();
			good.setId(id);
			good.setName(name);
			good.setPrice(price);
			good.setImgurl("asserts/images" + filename);
			good.setStatus("0");
			goodslist.add(good);
			
			String filepath = this.getServletConfig().
			        getServletContext().getRealPath("/asserts/images/"); 
			
			OutputStream out = new FileOutputStream(filepath + filename);      
            out.write(b);  
            out.flush();  
            out.close();
			
			status = "1";
		}else{
			for (GoodInfo good : goodslist) {
				if (id.equals(good.getId())) {
					good.setName(name);
					good.setPrice(price);
					good.setImgurl(img);
					status = "2";
					break;
				}
			}
		}
		
		JSONObject result = new JSONObject();
		result.put("status", status);
		
        response.getWriter().write(result.toString());
	}
}
