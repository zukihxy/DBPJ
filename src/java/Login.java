import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		DBConnection connection = new DBConnection();
		JSONObject result =new JSONObject();
		PrintWriter out = response.getWriter();
		try {
			Statement statement = connection.getConnection().createStatement();
			String query = "SELECT * FROM person WHERE person_id='" + id + "'";
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()) {
				String psw = rs.getString("password");
				if (psw.equals(password)){
					String type = rs.getString("user_type");
					if (type.equals("add")){
						result.put("success", "0");
						result.put("message", "Wrong id or password");
					}
					else{
						result.put("success", 1);
						result.put("type", type);
						if (type.equals("admin")){
							query = "SELECT * FROM person WHERE user_type='add' OR user_type='delete'";
							ResultSet tmp = statement.executeQuery(query);
							if (tmp.next())
								result.put("new", "1");
							else result.put("new", "0");
						}
					}
					
				}
				else{
					result.put("success", "0");
					result.put("message", "Wrong id or password");
				}
			}
			else{
				result.put("success", "1");
				result.put("message", "Wrong id or password");
			}
			out.print(result);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
