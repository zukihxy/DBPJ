
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
 * Servlet implementation class Approve
 */
@WebServlet("/Approve")
public class Approve extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Approve() {
        super();
        // TODO Auto-generated constructor stub
    }

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
        DBConnection connection = new DBConnection();
        PrintWriter out = response.getWriter();
        String course_id = request.getParameter("course_id");
        String employee_id = request.getParameter("employee_id");
        JSONObject result = new JSONObject();
        try {
            Statement statement = connection.getConnection().createStatement();
            String query;
            boolean success = true;
            String message = "";
            query = "UPDATE attend SET permit_retest=" + true + " WHERE course_id='" + course_id + "' AND employee_id='" + employee_id + "'";
            if (statement.executeUpdate(query) == 0) {
                success = false;
                message += "Fail in updateing the score of " + employee_id + "\n";
            }
            if (success) {
                result.put("result", "1");
                result.put("message", "succeed");
            } else {
                result.put("result", "0");
                result.put("message", message);
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

}
