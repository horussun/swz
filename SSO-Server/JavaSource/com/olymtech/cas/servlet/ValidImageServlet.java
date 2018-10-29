package com.olymtech.cas.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.olymtech.cas.util.StringUtil;
import com.olymtech.cas.util.ValidImageCreator;


public class ValidImageServlet extends HttpServlet {
	public static final String _SESSION_VALID_IMG_CONTENT="validContent";
    //Initialize global variables
    public void init() throws ServletException
    {
    	
    }
    
    //Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try{
    		String validtype=StringUtil.transpose_blank(request.getParameter("validtype")).toLowerCase();
    		String validContent=StringUtil.transpose_blank(request.getParameter("validContent")).toLowerCase();
    		if(validtype.equals("1"))
    		{
    			String sessionvalid=(String)request.getSession().getAttribute(ValidImageServlet._SESSION_VALID_IMG_CONTENT);
    			if(StringUtil.transpose_blank(sessionvalid.toLowerCase()).equals(validContent))
    			{
    				response.getWriter().print("success");
    				return;
    			}
    			else{
    				response.getWriter().print("fail");
    				return;}
    		}
    		ValidImageCreator creator=new ValidImageCreator();
    		String imgContent=creator.getContent().toLowerCase();    
    		request.getSession(true).setAttribute(_SESSION_VALID_IMG_CONTENT,imgContent);
    		BufferedImage bufImg=creator.getBufferedImage("  "+imgContent,81,20);
    		creator.response(response,bufImg);    
    		//System.out.println("cur Session img="+request.getSession().getAttribute(_SESSION_VALID_IMG_CONTENT));
    	} catch(Exception e){
    		System.out.println("create valid image error1!");
    	}        	

    }
    //Clean up resources
    public void destroy() {
    	
    }
}