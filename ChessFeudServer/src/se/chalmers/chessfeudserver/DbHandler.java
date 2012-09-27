package se.chalmers.chessfeudserver;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation for handlning request from the andriod-app.
 */
public class DbHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
	private Connection dbConnection; 
	
	private ResultSet rs;
	private Statement s;
	
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
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private void writeResultSet(ResultSet resultSet) throws SQLException {
	    while (resultSet.next()) {

	    	System.out.println(resultSet.getString(1));
	    	System.out.println(resultSet.getString(2));
	    	System.out.println(resultSet.getString(3));
	    }
	}

}
