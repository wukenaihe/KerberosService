package com.cgs.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloAjava extends HttpServlet implements Servlet {
	  
	public HelloAjava() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		PrintWriter writer = response.getWriter();
		writer.println("<html>");
		writer.println("<head><title>Hello Ajava.org</title></head>");
		writer.println("<body>Hello mark! How are you doing?</body>");
		writer.println("</html>");
		writer.close();
	}   	  	    
}
