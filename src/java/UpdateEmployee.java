

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class UpdateEmployee
 */
@WebServlet("/UpdateEmployee")
public class UpdateEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateEmployee() {
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
		// TODO Auto-generated method stub
		DBConnection connection = new DBConnection();
		PrintWriter out = response.getWriter();
		String users = request.getParameter("users");
		JSONObject result = new JSONObject();
		try {
			JSONArray array = new JSONArray(users);
			Statement statement = connection.getConnection().createStatement();
			String query;
			int len = array.length();
			String message = "";
			boolean success = true;
			for (int i = 0; i < len; i++){
				JSONObject item = array.getJSONObject(i);
				query = "UPDATE employee SET salary = '"+ item.getString("salary") + "', addition='" + item.getString("addition") + "', work_addr='" + item.getString("work_addr") + "', work_age='" + item.getString("work_age") + "' WHERE person_id ='" + item.getString("id") +"'";
				if (statement.executeUpdate(query) == 0){
					message += "Fail in updating user " + item.getString("id") + "\n";
					success = false;
				}
			}
			if (success) message = "succeed";
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

}
