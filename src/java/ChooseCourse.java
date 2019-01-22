
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class ChooseCourse
 */
@WebServlet("/ChooseCourse")
public class ChooseCourse extends HttpServlet {
<<<<<<< HEAD

    private static final long serialVersionUID = 1L;

=======
	private static final long serialVersionUID = 1L;
       
>>>>>>> a52860a1daa78ffdad8808c85d0ea7bf016594f0
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChooseCourse() {
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
        String courses = request.getParameter("courses");
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
            JSONArray array = new JSONArray(courses);
            int len = array.length();
            boolean success = true;
            String message = "";
            for (int i = 0; i < len; i++) {
                JSONObject item = array.getJSONObject(i);
                query = "SELECT * FROM attend WHERE course_id='" + item.getString("course_id") + "' AND employee_id='" + userid + "'";
                ResultSet rs = statement.executeQuery(query);
                if (!rs.next()) {
                    if (item.getString("choose").equals("delete")) {
                        query = "INSERT INTO attend (course_id,employee_id) VALUES ('" + item.getString("course_id") + "','" + userid + "')";
                        if (statement.executeUpdate(query) == 0) {
                            success = false;
                            message += "Fail in adding course " + item.getString("course_id") + " to the plan \n";
                        }

                    }
                } else {
                    if (item.getString("choose").equals("add")) {
                        query = "DELETE FROM attend WHERE course_id='" + item.getString("course_id") + "' AND employee_id='" + userid + "'";
                        if (statement.executeUpdate(query) == 0) {
                            success = false;
                            message += "Fail in deleting course " + item.getString("course_id") + " from the plan \n";
                        }
                    }
                }
            }
            if (success) {
                message = "Succeed!";
            }
            result.put("message", message);
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
			JSONArray array = new JSONArray(courses);
			int len = array.length();
			boolean success = true;
			String message = "";
			for (int i = 0; i < len; i++){
				JSONObject item = array.getJSONObject(i);
				query = "SELECT * FROM attend WHERE course_id='" + item.getString("course_id") + "' AND employee_id='" + userid + "'";
				ResultSet rs = statement.executeQuery(query);
				if (!rs.next()){
					if (item.getString("choose").equals("delete")) {
						query = "INSERT INTO attend (course_id,employee_id) VALUES ('" + item.getString("course_id") + "','" + userid + "')";
	                    if (statement.executeUpdate(query) == 0){
	                    	success = false;
		        			message += "Fail in adding course " + item.getString("course_id") + " to the plan \n";
	                    }
	        				
					}
				}
				else {
					if (item.getString("choose").equals("add")) {
						query = "DELETE FROM attend WHERE course_id='" + item.getString("course_id") + "' AND employee_id='" + userid + "'";
						if (statement.executeUpdate(query) == 0){
							success = false;
		        			message += "Fail in deleting course " + item.getString("course_id") + " from the plan \n";
						}	        				
					}
				}
			}
			if (success) message = "Succeed!";
			result.put("message" , message);
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
