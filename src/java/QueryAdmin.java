

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
 * Servlet implementation class QueryAdmin
 */
@WebServlet("/QueryAdmin")
public class QueryAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryAdmin() {
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
		String type = request.getParameter("type");
		JSONObject result = new JSONObject();
		try {
			Statement statement = connection.getConnection().createStatement();
			String query;
			if (type.equals("all")){
				query = "SELECT * FROM person";
				ResultSet rs = statement.executeQuery(query);
				JSONArray array = new JSONArray();
				while (rs.next()){
					if (!rs.getString("user_type").equals("add")){
						JSONObject item = new JSONObject();
						item.put("id", rs.getString("person_id"));
						item.put("password", rs.getString("password"));
						array.put(item);
					}
				}
				result.put("result", "1");
				result.put("users", array);
			}
			else {
				String id = request.getParameter("id");
				query = "SELECT * FROM person WHERE person_id='" + id + "'";
				ResultSet rs = statement.executeQuery(query);
				if (rs.next()){
					if (!rs.getString("user_type").equals("add")){
						JSONObject item = new JSONObject();
						item.put("id", id);
						item.put("password", rs.getString("password"));
						result.put("result", "1");
						result.put("users", item);
					}
					else {
						result.put("result", "0");
						result.put("error", "No such user");
					}
				}
				else {
					result.put("result", "0");
					result.put("error", "No such user");
				}
			}
			out.print(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String query;
	}

}
