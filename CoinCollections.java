package com.priti.casestudy3;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Scanner;

public class CoinCollections {
    static Scanner sc = new Scanner(System.in);

    static List<Coin> coinCollection = new ArrayList<>();
    static List<String> data = new ArrayList<>();
    

    public static void operations()
    {
        int choice1;
        System.out.println("\n1. Add Coins from database\n2. Add Data from file");
        choice1 = sc.nextInt();
        try {
            switch (choice1) 
            {
                case 1 :
                	{
                		fetchData();
                		break;
                	}
                case 2 :
                	{
                		try {
							bulkData();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                		break;
                	}
                
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        System.out.println("Welcome to coin Collection");
        int choice;
        do {
            System.out.println("1. Add new Coin");
            System.out.println("2. Update coin");
            System.out.println("3. Delete Coin");
            System.out.println("4. Search Coin");
            System.out.println("5. Display Data");
            System.out.println("6.Sort By Cuurent Value");
            System.out.println("0. Exit");
            System.out.println("Enter Your Choice:");
            choice = sc.nextInt();
            switch (choice) {
                case 1 : 
                	{
                		insert();
                		break;
                	}
                case 2 :
                	{
                		update();
                		break;
                	}
                case 3 :
                	{
                		remove();
                		break;
                	}
                case 4 :
                	{
                		search();
                		break;
                	}
                case 5 :
                	{
                		display();
                		break;
                	}
                case 6:
                {
                	sortByCurrentValue();
                	break;
                }
                case 0 :
                {
                	addData();
                    System.out.println("Exit");
                    break;
                   
                }
               
            }
        } while (choice != 0);
    }

    public static void addData() 
    {
    	System.out.println("Data to be added in database");
        try (Connection con = dbConnect()) 
        {
            if (con != null) 
            {
                for (int i = 0; i < data.size(); i++) 
                {
                    if (!data.get(i).equalsIgnoreCase("UC"))
                    {
                        switch (data.get(i)) 
                        {
                            case "Ins" :
                            {
                                String query = "insert into coin values(?,?,?,?,?,?);";
                                try (PreparedStatement pt = con.prepareStatement(query))
                                {
                                    pt.setInt(1, coinCollection.get(i).getId());
                                    pt.setString(2, coinCollection.get(i).getCountry());
                                    pt.setString(3, coinCollection.get(i).getDenomination());
                                    pt.setInt(4, coinCollection.get(i).getYear_of_minting());
                                    pt.setDouble(5, coinCollection.get(i).getCurrent_value());
                                    pt.setDate(6, java.sql.Date.valueOf(coinCollection.get(i).getAcquired_date()));
                                    pt.executeUpdate();
                                    data.set(i, "Ins");
                                    System.out.println("Data added");
                                }
                                break;
                            }
                            case "UP" :
                            {
                                String query = "update coin set current_value=? where coin_id=?;";
                                try (PreparedStatement pt = con.prepareStatement(query)) 
                                {
                                    pt.setFloat(1, coinCollection.get(i).getCurrent_value());
                                    pt.setInt(2, coinCollection.get(i).getId());
                                    pt.executeUpdate();
                                    System.out.println("Coin updated");
                                }
                                break;
                            }
                            case "del" : 
                            {
                                String query = "delete from coin where coin_id=?;";
                                try (PreparedStatement pt = con.prepareStatement(query)) 
                                {
                                    pt.setInt(1, coinCollection.get(i).getId());
                                    pt.executeUpdate();
                                    System.out.println("Deleted Successfully.");
                                }
                                break;
                            }
                        }
                    }
                }
            } 
            else
            {
                System.out.println("Connection failed");
            }
        } catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    public static void fetchData() throws SQLException 
    {
        try (Connection con = dbConnect())
        {
            if (con != null)
            {
                String query = "select * from coin;";
                try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) 
                {
                    while (rs.next()) 
                    {
                        Coin coin = new Coin(rs.getInt(1), rs.getString(2), rs.getString(3),
                                rs.getInt(4), rs.getFloat(5), rs.getString(6));
                        coinCollection.add(coin);
                        data.add("UC");
                    }
                }
            }
            else 
            {
                System.out.println("Connection failed");
            }
        }
    }

    public static void display() 
    {
        for (Coin c : coinCollection)
        {
            System.out.println(c);
        }
    }

    public static void insert()
    {
        System.out.println("Enter ID:");
        int id = sc.nextInt();
        boolean idExists = false;
        for(int i=0;i<coinCollection.size();i++)
        {
        	if (coinCollection.get(i).getId() == id)
        	{
        		idExists = true;
	            break;
        	}
        }
        if (idExists) {
	        System.out.println("Coin with this ID already exists.");
	        return;
	    }
        sc.nextLine();
        System.out.println("Enter Country");
        String country1 = sc.nextLine();
        System.out.println("Enter Denomination");
        String denomination1 = sc.next();
        System.out.println("Enter Year Of minting");
        int year = sc.nextInt();
        System.out.println("Enter current value");
        float value = sc.nextFloat();
        System.out.println("Enter Acquired Date in yyyy-mm-dd format");
        String date = sc.next();
        coinCollection.add(new Coin(id, country1, denomination1, year, value, date));

        data.add("Ins");
        
        System.out.println("Coin added");
    }

    public static void bulkData() throws FileNotFoundException
    {
        try (Scanner sc = new Scanner(new File("Book1.csv"))) 
        {
            while (sc.hasNext()) {
                String coin = sc.nextLine();
                String[] arr = coin.split(",");
                int id = Integer.parseInt(arr[0]);
                int year = Integer.parseInt(arr[3]);
                float value = Float.parseFloat(arr[4]);
                coinCollection.add(new Coin(id, arr[1], arr[2], year, value, arr[5].toString()));
                data.add("ins");
            }

            System.out.println("Data uploaded from file");
        }
    }

    public static Connection dbConnect() 
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/coincollection", "root", "Sudiksha@1");
        } 
        catch (ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static void update()
    {
        System.out.println("Enter id");
        int id = sc.nextInt();
        System.out.println("Enter value to update");
        float value = sc.nextFloat();

        for (int i = 0; i < coinCollection.size(); i++) 
        {
            if (coinCollection.get(i).getId() == id) 
            {
                coinCollection.get(i).setCurrent_value(value);
                if (data.get(i).equalsIgnoreCase("UC")) 
                {
                    data.set(i, "UP");
                } else if (data.get(i).equalsIgnoreCase("Ins"))
                {
                    data.set(i, "Ins");
                }
                System.out.println("Data Updated");
                return;
            }
        }
        System.out.println("Coin not found");
    }

    public static void remove() {
        System.out.println("Enter id");
        int id = sc.nextInt();

        for (int i = 0; i < coinCollection.size(); i++) 
        {
            if (coinCollection.get(i).getId() == id) 
            {
                data.set(i, "del");
                System.out.println("Coin deleted");
                return;
            }
        }
        System.out.println("Coin not found");
    }
    
    public static void search()
	{
		int searchChoice;
		do
		{
			System.out.println("1.By Country");
			System.out.println("2.By Year Of Minitng");
			System.out.println("3.By Current Value");
			System.out.println("4.Country and Denomination");
			System.out.println("5.Country and Year of Minting");
			System.out.println("6.Country , Denomination and Year of Minting");
			System.out.println("7.Acquired Date and Country");
			System.out.println("0.Exit");
			System.out.println("Enter Choice to search");
			searchChoice=sc.nextInt();
			switch(searchChoice)
			{
			case 1:
			{
				searchByCountry();
				break;
			}
			case 2:
			{
				searchByYearOfMinting();
				break;
			}
			case 3:
			{
				searchByCurrentValue();
				break;
			}
			case 4:
			{
				searchByCountryandDenomination();
				break;
			}
			case 5:
			{
				searchByCountryAndYearofMinting();
				break;
			}
			case 6:
			{
				searchByCountryAndDenominationAndYearOfMinting();
				break;
			}
			case 7:
			{
				searchByAcquiredDateAndCountry();
				break;
			}
			}
			
		}
		while(searchChoice!=0);
		
	}
	

	public static void searchByAcquiredDateAndCountry() {
		System.out.println("Enter acquired date in yyyy-mm-dd format");
		String date=sc.next();
		System.out.println("Enter Country");
		String country=sc.next();
	    // LocalDate d1=LocalDate.parse(year);
		 LocalDate acquiredDate = LocalDate.parse(date);

	     int count = 0;
	        for (Coin coin : coinCollection) {
	            if (coin.getAcquired_date().equals(acquiredDate) &&
	                coin.getCountry().equalsIgnoreCase(country))
	            {
	                System.out.println(coin);
	                count++;
	            }
	        }
	        if (count == 0) {
	            System.out.println("Not Found");
	        }
		
	}

	public static void searchByCountryAndDenominationAndYearOfMinting() {
		System.out.println("Enter Country");
		String country=sc.next();
		System.out.println("Enter Denomination");
		String denomination=sc.next();
		System.out.println("Enter Year of minting");
		int year=sc.nextInt();

		int count = 0;
        for (Coin coin : coinCollection) {
            if (coin.getCountry().equalsIgnoreCase(country) &&
                coin.getDenomination().equalsIgnoreCase(denomination) &&
                coin.getYear_of_minting() == year) 
            {
                System.out.println(coin);
                count++;
            }
        }
        if (count == 0) 
        {
            System.out.println("Not Found");
        }
		
	}
	
	public static void searchByCountryAndYearofMinting() {
		System.out.println("Enter Country");
		String country=sc.next();
		System.out.println("Enter Year of minting");
		int year=sc.nextInt();

		 int count = 0;
	        for (Coin coin : coinCollection) 
	        {
	            if (coin.getCountry().equalsIgnoreCase(country) &&
	                coin.getYear_of_minting() == year) 
	            {
	                System.out.println(coin);
	                count++;
	            }
	        }
	        if (count == 0) 
	        {
	            System.out.println("Not Found");
	        }
	}

	public static void searchByCountryandDenomination() {
		System.out.println("Enter Country");
		String country=sc.next();
		System.out.println("Enter Denomination");
		String denomination=sc.next();
		int count=0;

		for (Coin coin : coinCollection) 
		{
            if (coin.getCountry().equalsIgnoreCase(country) &&
                coin.getDenomination().equalsIgnoreCase(denomination)) 
            {
                System.out.println(coin);
                count++;
            }
        }
        if (count == 0) {
            System.out.println("Not Found");
        }
	
		
	}

	public static void searchByCurrentValue() 
	{
		System.out.println("Enter Current Value");
		float value=sc.nextFloat();

		 int count = 0;
	        for (Coin coin : coinCollection)
	        {
	            if (coin.getCurrent_value() == value) 
	            {
	                System.out.println(coin);
	                count++;
	            }
	        }
	        if (count == 0) {
	            System.out.println("Current value Not Found");
	        }

	}

	public static void searchByYearOfMinting() 
	{
		System.out.println("Enter Year of Minting");
		int year=sc.nextInt();
		int count=0;
		for (Coin coin : coinCollection)
		{
            if (coin.getYear_of_minting() == year) 
            {
                System.out.println(coin);
                count++;
            }
        }
        if (count == 0) 
        {
            System.out.println("Year of Minting Not Found");
        }
		
	}

	public static void searchByCountry()
	{
		System.out.println("Enter Country name to search");
		sc.nextLine();
		String country=sc.nextLine();
		int count=0;

		for (Coin coin : coinCollection) 
		{
            if (coin.getCountry().contains(country))
            {
                System.out.println(coin);
                count++;
            }
        }
        if (count == 0) 
        {
            System.out.println("Country Not Found");
        }
		
		
	}
	public static void sortByCurrentValue()
	{
		try
		{
			Collections.sort(coinCollection, new Comparator<Coin>()
					{
						public int compare(Coin o1, Coin o2) {
							// TODO Auto-generated method stub
							return Float.compare(o1.getCurrent_value(), o2.getCurrent_value());
						}
				
					});
			 System.out.println("Sorted collection:");
			for(Coin coin: coinCollection)
			{
				System.out.println(coin);
			}
		}
		catch(ConcurrentModificationException e)
		{
			System.out.println(e.getMessage());
		}
		
	}

}
