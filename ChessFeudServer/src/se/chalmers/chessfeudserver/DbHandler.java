package se.chalmers.chessfeudserver;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    
	
	private Connection dbcon; 
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DbHandler() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException
    {
        String loginUser = "twister";
        String loginPasswd = "awesomeness";
        String loginUrl = "jdbc:mysql://localhost/cfdb";

        // Load the PostgreSQL driver
        try 
        {
              Class.forName("com.mysql.jdbc.Driver");
              dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
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
	
	private String getGames(String userName) {
		
	}

}
