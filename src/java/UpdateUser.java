

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
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
 * Servlet implementation class UpdateUser
 */
@WebServlet("/UpdateUser")
public class UpdateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUser() {
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
		String function = request.getParameter("function");
		JSONObject result = new JSONObject();
		try {
			JSONArray array = new JSONArray(users);
			Statement statement = connection.getConnection().createStatement();
			String query;
			String query1;
			int len = array.length();
			String message = "";
			boolean success = true;
			if (function.equals("update")){
				for (int i = 0; i < len; i++){
					JSONObject item = array.getJSONObject(i);
					query = "UPDATE person SET password = '"+ item.getString("password") +"' WHERE person_id ='" + item.getString("id") +"'";
					if (statement.executeUpdate(query) == 0){
						message += "Fail in updating user " + item.getString("id") + "\n";
						success = false;
					}
					
				}
			}
			else {
				int index = 0;
				while(true){
					JSONObject item = array.getJSONObject(index++);
					query1 = "SELECT * FROM person WHERE person_id='" + item.getString("id") + "'";
					ResultSet rs = statement.executeQuery("query1");
					if (!rs.next()) {
						query = "INSERT INTO person VALUES ('" + item.getString("id") + "', '" + item.getString("password") + "', '" + item.getString("type") + "')";
						break;
					}
					else {
						success = false;
						message += "Fail in adding user " + item.getString("id") + ", as the id has been used \n";
					}
				}
				
				for (int i = index; i < len; i++){
					JSONObject item = array.getJSONObject(i);
					query1 = "SELECT * FROM person WHERE person_id='" + item.getString("id") + "'";
					ResultSet rs = statement.executeQuery("query1");
					if (!rs.next()){
						query += ",('" + item.getString("id") + "', '" + item.getString("password") + "', '" + item.getString("type") + "')";
					}
					else {
						success = false;
						message += "Fail in adding user " + item.getString("id") + ", as the id has been used \n";
					}
				}
				if (statement.executeUpdate(query) == 0){
					success = false;
					message += "Fail in adding user ";
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
