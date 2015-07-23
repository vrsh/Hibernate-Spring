package com.lumiplan.hibernate.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;

import com.lumiplan.hibernate.mainMethod.*;
import com.lumiplan.hibernate.entity.City;
import com.lumiplan.hibernate.entity.Country;
import com.lumiplan.hibernate.entity.State;

public class CallMethods {
	
	private SessionFactory sessionFactory;
	
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	static List<Country> countryL = new ArrayList<Country>();
	static List<State> stateL = new ArrayList<State>();
	static List<City> cityL = new ArrayList<City>();
	
	public void country()
	{
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		List l = session.createCriteria(Country.class,"country").list();
		Iterator i = l.iterator();
		while(i.hasNext())
		{
			Country c = (Country) i.next();
			System.out.println(c.getCountry_id() +"\t"+c.getCountry_name());
			countryL.add(c);
		}
		t.commit();
	}
	
	public List<Country> countryList()
	{
		return countryL;
	}
	
	public void state(){
		Session session = getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		
		Criteria criteriaState = session.createCriteria(State.class,"st").createAlias("st.country_id", "join", JoinType.INNER_JOIN);
		List stateCL = criteriaState.list();
		Iterator i1 = stateCL.iterator();
		while(i1.hasNext())
		{
			State s = (State) i1.next();
			System.out.println(s.getState_id() +"\t "+s.getState_name()+" \t\t "+ s.getCountry().getCountry_name());
			stateL.add(s);
		}
		t.commit();
		
		/*
		Criteria criteriaCity = session.createCriteria(City.class,"City").createAlias("City.state_id", "join", JoinType.LEFT_OUTER_JOIN);
		List cityCL = criteriaCity.list();
		Iterator i2 = cityCL.iterator();
		while(i2.hasNext())
		{
			City c = (City) i2.next();
			System.out.println(c.getCity_id() +" "+c.getCity_name()+" is in "+ c.getState().getState_name());
			cityL.add(c);
		}
		t.commit();
		*/
	}
	
	public void city()
	{
		Session session = getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteriaCity = session.createCriteria(City.class,"City").createAlias("City.state_id", "join", JoinType.LEFT_OUTER_JOIN);
		List cityCL = criteriaCity.list();
		Iterator i2 = cityCL.iterator();
		while(i2.hasNext())
		{
			City c = (City) i2.next();
			System.out.println(c.getCity_id() +" \t "+c.getCity_name()+" \t\t "+ c.getState().getState_name());
			cityL.add(c);
		}
		t.commit();
	}
	
	
	public void insertCountryData() throws IOException
	{
		int flag=0;
		Session session = getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		System.out.println("Enter the country name\n");
		String countryEntered = br.readLine();
		Country countryInsert = new Country();
		countryInsert.setCountry_name(countryEntered);
		session.save(countryInsert);
		session.flush();
		session.close();
		for(int i=0;i<countryL.size();i++)
		{
			if(countryEntered == countryL.get(i).toString())
			{
				flag = 1;
				System.out.println("Country already present");
			}
		}
		if(flag == 0)
		{ 
			CallMethods call = new CallMethods();
			call.country();
		}
		t.commit();
	}
	
	
	public void insertStateData() throws IOException
	{
		CallMethods call = new CallMethods();
		int flag = 0;
		Country country = new Country() ;
		System.out.println("Enter the state name\n");
		String stateEntered = br.readLine();
		State stateInsert = new State();
		stateInsert.setState_name(stateEntered);
		System.out.println("Enter respective country name");
		String stateCountry = (br.readLine());
		for(int i=0;i<countryL.size();i++)
		{
			if(stateCountry.equals(countryL.get(i).getCountry_name()))
			{
				flag = 1;
				country = countryL.get(i);
			}
		}
		if(flag==0)
		{
			
			System.out.println("Enter Country first\n");
			call.insertCountryData();
			for(int i=0;i<countryL.size();i++)
			{
				if(stateCountry.equals(countryL.get(i).getCountry_name()))
				{
					country = countryL.get(i);
				}
			}
			Session session = getSessionFactory().openSession();
			Transaction t = session.beginTransaction();
			stateInsert.setState_name(stateEntered);
			stateInsert.setCountry(country);
			session.save(stateInsert);
			session.flush();
			session.close();
			t.commit();
			call.country();
			
		}
		if(flag==1)
		{
		Session session = getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		stateInsert.setState_name(stateEntered);
		stateInsert.setCountry(country);
		session.save(stateInsert);
		session.flush();
		session.close();
		t.commit();
		}
		call.state();
	}
	
	public void insertCityData() throws IOException
	{
		CallMethods call = new CallMethods();
		int flag = 0;
		State state = new State() ;
		System.out.println("Enter the cityname\n");
		String cityEntered = br.readLine();
		City cityInsert = new City();
		cityInsert.setCity_name(cityEntered);
		System.out.println("Enter respective state name");
		String cityState = (br.readLine());
		for(int i=0;i<stateL.size();i++)
		{
			if(cityState.equals(stateL.get(i).getState_name()))
			{
				flag = 1;
				state = stateL.get(i);
			}
		}
		if(flag==0)
		{
			
			System.out.println("Enter State first\n");
			call.insertStateData();
			for(int i=0;i<stateL.size();i++)
			{
				if(cityState.equals(stateL.get(i).getState_name()))
				{
					state = stateL.get(i);
				}
			}
			Session session = getSessionFactory().openSession();
			Transaction t = session.beginTransaction();
			cityInsert.setCity_name(cityEntered);
			cityInsert.setState(state);
			session.save(cityInsert);
			session.flush();
			session.close();
			t.commit();
			call.state();
			
		}
		if(flag==1)
		{
		Session session = getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		cityInsert.setCity_name(cityEntered);
		cityInsert.setState(state);
		session.save(cityInsert);
		session.flush();
		session.close();
		t.commit();
		}
		call.city();
	}
	
	public void updateCityName() throws IOException
	{
		Session session = getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		CallMethods ca = new CallMethods();
		System.out.println("Enter cityname you want to update");
		String oldName = br.readLine();
		System.out.println("Enter update name for this city");
		String newName = br.readLine();
		Criteria criteriaCity = session.createCriteria(City.class,"City");
		List cityCL = criteriaCity.list();
		Iterator i2 = cityCL.iterator();
		int i=0;
		while(i2.hasNext())
		{
			while(i<cityL.size())
			{
			City c = (City) i2.next();
			if(oldName.equals(cityL.get(i).getCity_name()))
			{
				cityL.get(i).setCity_name(newName);
				c.setCity_name(newName);
			}
			i++;
			}
		}
		t.commit();
		ca.city();
	}
	
	
	public void updateStateName() throws IOException
	{
		Session session = getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		CallMethods call = new CallMethods();
		System.out.println("Enter state name you want to update");
		String oldName = br.readLine();
		System.out.println("Enter update name for this state");
		String newName = br.readLine();
		Criteria criteriaState = session.createCriteria(State.class,"State");
		List stateCL = criteriaState.list();
		Iterator i2 = stateCL.iterator();
		int i=0;
		while(i2.hasNext())
		{
			while(i<stateL.size())
			{
				State s = (State) i2.next();
				if(oldName.equals(stateL.get(i).getState_name()))
				{
					stateL.get(i).setState_name(newName);
					s.setState_name(newName);
				}
				i++;
			}
		}
		t.commit();
		call.state();
	}
	
	public void updateCountryName() throws IOException
	{
		Session session = getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		CallMethods call = new CallMethods();
		System.out.println("Enter country name you want to update");
		String oldName = br.readLine();
		System.out.println("Enter update name for this country");
		String newName = br.readLine();
		Criteria criteriaCountry = session.createCriteria(Country.class,"Country");
		List countryCL = criteriaCountry.list();
		Iterator i2 = countryCL.iterator();
		int i=0;
		while(i2.hasNext())
		{
			while(i<countryL.size())
			{
			Country c = (Country) i2.next();
			if(oldName.equals(countryL.get(i).getCountry_name()))
			{
				countryL.get(i).setCountry_name(newName);
				c.setCountry_name(newName);
			}
			i++;
			}
		}
		t.commit();
		call.country();
	}
	
	public void deleteCity()throws IOException
	{

		Session session = getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		CallMethods call = new CallMethods();
		System.out.println("Enter ID of city to be deleted");
		int cityid = Integer.parseInt(br.readLine());
		City cityDel = (City) session.createCriteria(City.class).add(Restrictions.eq("city_id", cityid)).uniqueResult();
		session.delete(cityDel);
		t.commit();
		call.city();
	}
	
	public void deleteState() throws IOException
	{
		Session session = getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		CallMethods call = new CallMethods();
		System.out.println("Enter ID of state to be deleted");
		int stateid = Integer.parseInt(br.readLine());
		State stateDel = (State) session.createCriteria(State.class).add(Restrictions.eq("state_id", stateid)).uniqueResult();
		session.delete(stateDel);
		t.commit();
		call.state();
	}
	
	public void deleteCountry() throws IOException
	{
		Session session = getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		CallMethods call = new CallMethods();
		System.out.println("Enter ID of country to be deleted");
		int countryid = Integer.parseInt(br.readLine());
		Country countryDel = (Country) session.createCriteria(Country.class).add(Restrictions.eq("country_id", countryid)).uniqueResult();
		session.delete(countryDel);
		t.commit();
		call.country();
	}
}
