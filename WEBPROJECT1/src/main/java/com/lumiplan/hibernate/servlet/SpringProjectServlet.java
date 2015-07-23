package com.lumiplan.hibernate.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lumiplan.hibernate.dao.CallMethods;

public class SpringProjectServlet extends HttpServlet {

	ApplicationContext AC = new ClassPathXmlApplicationContext("application-config.xml");
	CallMethods c = (CallMethods) AC.getBean("app");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter write = response.getWriter();
		c.country();
		for(int i=0;i<c.countryList().size(); i++)
		{
			write.print(c.countryList().get(i).getCountry_id()+" ");
			write.println(c.countryList().get(i).getCountry_name()+"<br>");
		}
	}
	
}
