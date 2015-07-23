package com.lumiplan.hibernate.mainMethod;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lumiplan.hibernate.dao.CallMethods;
import com.lumiplan.hibernate.entity.*;
import com.lumiplan.hibernate.dao.*;


	public class MainClass {

		public static void main(String[] args) {
			// TODO Auto-generated method stub

			ApplicationContext AC = new ClassPathXmlApplicationContext("application-config.xml");
			CallMethods c = (CallMethods) AC.getBean("app");
			
			c.country();
		}

	}

