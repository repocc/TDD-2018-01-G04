package model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TicketsSystemAdapter {
	
	private static String API_URL = "http://localhost:3000/api/";
	
	public TicketsSystemAdapter()
	{
		
	}
	
	public boolean authenticateUser(User user)
	{
		String response = postHTML(API_URL + "auth", user.getName());
		
		if (response == null)
		{
			return false;
		}
		
		String role = getRoleFromJSON(response);
		user.setRole(role);
		
		return true;
	}
	
	private String getRoleFromJSON(String json)
	{
		String[] role = json.split("\"");
		return (role[role.length-2]);
	}
	
	private static String getHTML(String urlToRead) 
	{
		try {
	      StringBuilder result = new StringBuilder();
	      URL url = new URL(urlToRead);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setRequestMethod("GET");
	      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      String line;
	      while ((line = rd.readLine()) != null)
	      {
	         result.append(line);
	      }
	      rd.close();
	      return result.toString();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
	
	private static String postHTML(String urlToRead, String user) 
	{
		URL url;
	    HttpURLConnection connection = null;  
	    String urlParameters = "";
		try {
			urlParameters = "username=" + URLEncoder.encode(user, "UTF-8");

		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return null;
		}
	            
	    try {
	      //Create connection
	      url = new URL(urlToRead);
	      connection = (HttpURLConnection)url.openConnection();
	      connection.setRequestMethod("POST");
	      connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				
	      connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
	      connection.setRequestProperty("Content-Language", "en-US");  
				
	      connection.setUseCaches (false);
	      connection.setDoInput(true);
	      connection.setDoOutput(true);

	      //Send request
	      DataOutputStream wr = new DataOutputStream (connection.getOutputStream ());
	      wr.writeBytes (urlParameters);
	      wr.flush ();
	      wr.close ();

	      //Get Response	
	      InputStream is = connection.getInputStream();
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	      String line;
	      StringBuffer response = new StringBuffer(); 
	      while((line = rd.readLine()) != null) {
	        response.append(line);
	        response.append('\r');
	      }
	      rd.close();
	      return response.toString();

	    } 
	    catch (Exception e) {
	    	return null;
	    }
	    finally {

	      if(connection != null) {
	        connection.disconnect(); 
	      }
	    }
    }
}
