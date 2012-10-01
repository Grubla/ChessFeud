package se.chalmers.chessfeudserver;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation for handling request from the andriod-app.
 */
public class DbHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
	private Connection dbConnection; 
	
	
	private ResultSet rs;
	private Statement s;
	private HashMap tags;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DbHandler() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * Loads the jdbc driver.
     */
    public void init(ServletConfig config) throws ServletException
    {
        String loginUser = "root";
        String loginPw = "awesomeness";
        String loginURL = "jdbc:mysql://localhost/cfdb";
        tags.put("login", 0);
        tags.put("newmove", 1);
        tags.put("getGames", 2);
        tags.put("getStats", 3);
        tags.put("addUser", 4);
        tags.put("incWins", 5);
        tags.put("incLosses", 6);
        tags.put("incDraws", 7);
        tags.put("", 8);
        tags.put("", 9);
        
        try 
        {
              Class.forName("com.mysql.jdbc.Driver");
              dbConnection = DriverManager.getConnection(loginURL, loginUser, loginPw);
              
              s = dbConnection.createStatement();
              
              rs = s.executeQuery("select * from auth");
              
              writeResultSet(rs);
              
        }
        catch (ClassNotFoundException ex)
        {
               System.err.println("ClassNotFoundException: " + ex.getMessage());
               throw new ServletException("Class not found Error");
        }
        catch (SQLException ex)
        {
               System.err.println("SQLException: " + ex.getMessage());
        }
        
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text");
		final ServletOutputStream out = response.getOutputStream();			
		switch(tags.get(request.getParameter("tag"))) {
		case 0:
			out.print(authenticate(request.getParameter("username"), request.getParameter("password")));
			break;
		case 1:
			;
			break;
		case 2:
			;
			break;
		case 3:
			;
			break;
		}
		try {
			String tag = request.getParameter("tag");
			} catch(Exception e) {
			e.printStackTrace();
			
			} 

		out.flush();
		out.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!request.getParameter("tag").equals("login")) {
			this.doGet(request, response);			
		} else {
			//TODO secure login
		}
		
	}
	
	private void writeResultSet(ResultSet resultSet) throws SQLException {
	    while (resultSet.next()) {

	    	System.out.println(resultSet.getString(1));
	    	System.out.println(resultSet.getString(2));
	    	System.out.println(resultSet.getString(3));
	    }
	}
	
	private boolean authenticate(String userName, String password) throws SQLException {
		
		rs = s.executeQuery("select password from auth where userName='"+userName+"'");
		rs.first();
		if(rs.getString(0).equals(password)) {
			return true;
		} 
		
		return false;
		
	}
	/**
	 * Checks for games in the database where the user is participating and adds them to an arraylist to return.
	 * @param userName
	 * @return
	 * @throws SQLException
	 */
	private List<String> getGamesInProgress(String userName) throws SQLException {
		List<String> games = new ArrayList<String>();
		rs = s.executeQuery("select * from game where user1='"+userName+"' or user2='"+userName+"'");
		while(rs.next()) {
			games.add(rs.getString(1)+"/"+rs.getString(2)+"/"+rs.getString(3)+"/"+rs.getString(4));
		}
		return games;
	}
	/**
	 * Returns wins losses and draws as well as number of moves done all with a / between each other.
	 * @param userName
	 * @return
	 * @throws SQLException
	 */
	private String getStatistics(String userName) throws SQLException {
		rs = s.executeQuery("select * from statistics where username='"+userName+"'");
		return(rs.getString(2)+"/"+rs.getString(3));
	}
	/**
	 * Adds a user to the database.
	 * @param email
	 * @param userName
	 * @param password
	 * @throws SQLException
	 */
	private void addUser(String email, String userName, String password) throws SQLException {
		s.executeQuery("insert into auth(email, username, password) values('"+email+"','"+userName+"','"+password+"')");
	}
	
	private void incWins(String userName) throws SQLException {
		rs = s.executeQuery("select * from statistics where username='"+userName+"'");
		String[] wld = rs.getString(1).split("/");
		int w = Integer.parseInt(wld[0])+1;
		//s.executeQuery("update statistics set )
		// TODO
	}
	
	private void incLosses(String userName) {
		
	}
	
	private void incDraws(String userName) {
		
	}
	
	
	
	

}
