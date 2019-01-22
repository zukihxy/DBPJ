
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
 * Servlet implementation class Offer
 */
@WebServlet("/Offer")
public class Offer extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Offer() {
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
        // TODO Auto-generated method stub
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
            JSONArray array = new JSONArray(courses);
            String query;
            String query2;
            int index = 0;
            boolean success = true;
            String message = "";
            int len = array.length();
            while (true) {
                if (index < len) {
                    JSONObject item = array.getJSONObject(index++);
                    String query1 = "SELECT * FROM course WHERE course_id='" + item.getString("course_id") + "'";
                    ResultSet rs1 = statement.executeQuery(query1);
                    if (!rs1.next()) {
                        query = "INSERT INTO course (course_id,course_name,is_in_plan,total_time) VALUES ('" + item.getString("course_id") + "', '" + item.getString("course_name") + "', " + false + ", '" + item.getString("total_time") + "')";
                        query2 = "INSERT INTO offer VALUES ('" + item.getString("course_id") + "','" + userid + "')";
                        break;
                    } else {
                        success = false;
                        message += "Fail in adding course " + item.getString("course_id") + ", as the id has been used \n";
                    }
                } else {
                    success = false;
                    message += "Fail in adding course as the id has been used \n";
                    result.put("message", message);
                    out.print(result);
                    return;
                }
            }

            for (int i = index; i < len; i++) {
                JSONObject item = array.getJSONObject(i);
                String query1 = "SELECT * FROM course WHERE course_id='" + item.getString("course_id") + "'";
                ResultSet rs1 = statement.executeQuery(query1);
                if (!rs1.next()) {
                    query += ",('" + item.getString("course_id") + "', '" + item.getString("course_name") + "', " + false + ", '" + item.getString("total_time") + "')";
                    query2 += ",('" + item.getString("course_id") + "','" + userid + "')";
                } else {
                    success = false;
                    message += "Fail in adding course " + item.getString("course_id") + ", as the id has been used \n";
                }
            }
            if (statement.executeUpdate(query) == 0) {
                success = false;
                message += "Fail in adding courses ";
            } else if (statement.executeUpdate(query2) == 0) {
                success = false;
                message += "Fail in adding courses ";
            }
            if (success) {
                message = "succeed";
            }
            result.put("message", message);
            out.print(result);

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
