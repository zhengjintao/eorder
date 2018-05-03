package com.bwc.biz.order;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

public class StringUtil {
	public static String padLeft(String src, int len, char ch) {
	    int diff = len - src.length();
        if (diff <= 0) {
            return src;
        }

        char[] charr = new char[len];
        System.arraycopy(src.toCharArray(), 0, charr, diff, src.length());
        for (int i = 0; i < diff; i++) {
            charr[i] = ch;
        }
        return new String(charr);
    }
	
	public static String getParameter(HttpServletRequest request, String name) throws UnsupportedEncodingException{
		String value = request.getParameter(name);
		if(value != null){
			value = new String(value.getBytes("iso-8859-1"), "utf-8");
		}
		
		return value;
	}
}
