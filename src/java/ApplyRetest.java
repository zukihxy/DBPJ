
<<<<<<< HEAD
=======

>>>>>>> a52860a1daa78ffdad8808c85d0ea7bf016594f0
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
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
<<<<<<< HEAD

    private static final long serialVersionUID = 1L;

=======
	private static final long serialVersionUID = 1L;
       
>>>>>>> a52860a1daa78ffdad8808c85d0ea7bf016594f0
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApplyRetest() {
        super();
        // TODO Auto-generated constructor stub
    }

<<<<<<< HEAD
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        DBConnection connection = new DBConnection();
        PrintWriter out = response.getWriter();
        String course = request.getParameter("course_id");
        Cookie[] cookies = request.getCookies();
        JSONObject result = new JSONObject();
        String userid = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("id")) {
                userid = cookie.getValue();
            }
        }
        try {
            Statement statement = connection.getConnection().createStatement();
            String query;
            query = "UPDATE attend SET apply_retest=" + true + " WHERE course_id='" + course + "' AND employee_id='" + userid + "'";
            if (statement.executeUpdate(query) == 0) {
                result.put("message", "Fail!");
            } else {
                result.put("message", "Succeed!");
            }
            out.print(result);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
=======
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
		String course = request.getParameter("course_id");
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
			query = "UPDATE attend SET apply_retest=" + true + " WHERE course_id='" + course + "' AND employee_id='" + userid + "'";
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
>>>>>>> a52860a1daa78ffdad8808c85d0ea7bf016594f0

}
