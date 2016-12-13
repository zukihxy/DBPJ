
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
 * Servlet implementation class MakePlan
 */
@WebServlet("/MakePlan")
public class MakePlan extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MakePlan() {
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
        String courses = request.getParameter("courses");
        JSONObject result = new JSONObject();
        try {
            Cookie[] cookies = request.getCookies();
            String userid = "";
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("id")) {
                    userid = cookie.getValue();
                }
            }
            JSONArray array = new JSONArray(courses);
            int len = array.length();
            String message;
            Statement statement = connection.getConnection().createStatement();
            String query = "SELECT department FROM chief WHERE person_id='" + userid + "'";
            ResultSet rs = statement.executeQuery(query);
            String department = "";
            if (rs.next()) {
                department = rs.getString("department");
            }
            if (len <= 0){
                result.put("message", "you must add a course to your plan");
                out.print(result);
                return;
            }
            JSONObject item = array.getJSONObject(0);
            boolean m;
            if (item.getString("mandatory").equals("madatory")) {
                m = true;
            } else {
                m = false;
            }
            query = "INSERT INTO plan VALUES ('" + item.getString("id") + "'," + m + ",'" + department + "')";
            for (int i = 0; i < len; i++) {
                item = array.getJSONObject(i);
                if (item.getString("mandatory").equals("madatory")) {
                    m = true;
                } else {
                    m = false;
                }
                query += ",('" + item.getString("id") + "'," + m + ",'" + department + "')";
            }
            if (statement.executeUpdate(query) != 0) {
                message = "succeed!";
            } else {
                message = "fail!";
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

}
