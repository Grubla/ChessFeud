package se.chalmers.chessfeudserver;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public TestServlet() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml");
		
		final ServletOutputStream out = response.getOutputStream();



			
			try {
				String userName = request.getParameter("userName");
		        String password = request.getParameter("password");
				boolean authenticated = login(userName, password);
				if (authenticated) {
					out.println("<login><status>SUCSESS</status></login>");
				} else {
					out.println("<login><status>FAIL</status></login>");
				}
				} catch(Exception e) {
				out.println("<login><status>ERROR</login></status>");
				
				} 

			out.flush();
			out.close();
	}
		


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doGet(request, response);

	}
	
	protected boolean login(String userName, String password) {
		if (userName.equals("twister") && password.equals("awesomeness")) {
			return true;
		} else {
			return false;
		}
		
	}

}