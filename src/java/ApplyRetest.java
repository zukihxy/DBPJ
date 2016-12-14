

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class ApplyRetest
 */
@WebServlet("/ApplyRetest")
public class ApplyRetest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApplyRetest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DBConnection connection = new DBConnection();
		PrintWriter out = response.getWriter();
		String courses = request.getParameter("courses");
		Cookie[] cookies = request.getCookies();
		JSONObject result = new JSONObject();
		String userid = "";
		for (Cookie cookie: cookies){
			if(cookie.getName().equals("id")){
				userid = cookie.getValue();
			}
		}
		try {
			Statement statement = connection.getConnection().createStatement();
			String query ; 
			JSONObject item = new JSONObject(courses);
			query = "UPDATE attend SET permit_retest=" + true + " WHERE course_id='" + item.getString("course_id") + "' AND employee_id='" + userid + "'";
			if (statement.executeUpdate(query) == 0)
 				result.put("message",  "Fail!");
 			else 
 				result.put("message",  "Succeed!");
			out.print(result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
