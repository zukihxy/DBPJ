

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
 * Servlet implementation class Delete
 */
@WebServlet("/Delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Delete() {
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
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		DBConnection connection = new DBConnection();
		JSONObject result =new JSONObject();
		PrintWriter out = response.getWriter();
		Cookie[] cookies = request.getCookies();
		String user_type = cookies[1].getValue();
		String query;
		
		try {
			Statement statement = connection.getConnection().createStatement();
			if (type.equals("user")){
				if(request.getCookies()[0].getValue().equals(id)){
					result.put("message", "You can't delete yourself!");
				}
				else{
					query = "DELETE FROM person WHERE person_id='" + id +"'";
					if(statement.executeUpdate(query) != 0)
						result.put("message", "Success!");
					else result.put("message", "Fail in deleting!");
				}
				
			}
				
			if (type.equals("employee")){
				query = "DELETE FROM attend WHERE employee_id='" + id +"'";
				statement.execute(query);
				query = "DELETE FROM employee WHERE person_id='" + id +"'";
				statement.execute(query);
				query = "UPDATE person SET user_type = 'delete' WHERE person_id ='" + id +"'";
				statement.execute(query);
				String success = "Fail in deleting. Please delete it again";
				query = "SELECT * FROM attend WHERE employee_id='" + id +"'";
				ResultSet rs1 = statement.executeQuery(query);
				if (!rs1.next()){
					query = "SELECT * FROM employee WHERE person_id='" + id +"'";
					ResultSet rs2 = statement.executeQuery(query);
					if (!rs2.next()){
						query = "SELECT user_type FROM person WHERE person_id='" + id +"'";
						ResultSet rs3 = statement.executeQuery(query);
						if (rs3.next()){
							if(rs3.getString("user_type").equals("delete")) success = "Success!";
						}
					}
				}
				result.put("message", success);			
			}
			
			if (type.equals("course")) {
				if (user_type.equals("teacher")){
					query = "SELECT is_in_plan FROM course WHERE course_id='" + id + "'";
					ResultSet rs = statement.executeQuery(query);
					if (rs.next()){
						if (rs.getBoolean("is_in_plan"))
							result.put("message", "The course cannot be deleted as it has already been added to a plan of department!");
						
						else {
							query = "DELETE FROM attend WHERE course_id='" + id +"'";
							statement.execute(query);
							query = "DELETE FROM offer WHERE course_id='" + id +"'";
							statement.execute(query);
							query = "DELETE FROM course WHERE course_id='" + id +"'";
							statement.execute(query);
							
							String success = "Fail in deleting. Please delete it again";
							query = "SELECT * FROM attend WHERE course_id='" + id +"'";
							ResultSet rs1 = statement.executeQuery(query);
							if (!rs1.next()){
								query = "SELECT * FROM offer WHERE course_id='" + id +"'";
								ResultSet rs2 = statement.executeQuery(query);
								if (!rs2.next()){
									query = "SELECT * FROM course WHERE course_id='" + id +"'";
									ResultSet rs3 = statement.executeQuery(query);
									if (!rs3.next()) success = "Success!";
								}
							}
							result.put("message", success);
						}
					}
				}
				
				else if(user_type.equals("employee")){
					query = "DELETE FROM attend WHERE course_id='" + id +"' AND employee_id='"+ cookies[0].getValue() +"'";
					if(statement.executeUpdate(query) != 0)
						result.put("message", "Success!");
					else result.put("message", "Fail in deleting!");
				}
			}
			out.println(result);
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
