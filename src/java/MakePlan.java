
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
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
            String message = "";
            Statement statement = connection.getConnection().createStatement();
            String query = "SELECT department FROM chief WHERE person_id='" + userid + "'";
            ResultSet rs = statement.executeQuery(query);
            String department = "";
            if (rs.next()) {
                department = rs.getString("department");
            }
            rs.close();

            ArrayList<String> employees = new ArrayList<>();
            query = "SELECT person_id FROM employee WHERE department='" + department + "'";
            ResultSet rs1 = statement.executeQuery(query);
            while (rs1.next()) {
                employees.add(rs1.getString("person_id"));
            }
            rs1.close();

            if (len <= 0) {
                result.put("message", "You must add a course to your plan");
                out.print(result);
                return;
            }
            boolean m;
            boolean success = true;
            for (int i = 0; i < len; i++) {
                JSONObject item = array.getJSONObject(i);
                if (item.getString("mandatory").equals("mandatory") || item.getString("mandatory").equals("elective")) {
                    m = item.getString("mandatory").equals("mandatory");
                    query = "SELECT * FROM plan WHERE course_id='" + item.getString("id") + "' AND department='" + department + "'";
                    ResultSet rs2 = statement.executeQuery(query);
                    if (rs2.next()) {
                        query = "UPDATE plan SET mandatory=" + m + " WHERE course_id='" + item.getString("id") + "' AND department='" + department + "'";
                    } else {
                        query = "INSERT INTO plan VALUES ('" + item.getString("id") + "'," + m + ",'" + department + "')";
                    }
                    if (statement.executeUpdate(query) == 0) {
                        success = false;
                        message += "Fail in adding course " + item.getString("id") + " to the plan \n";
                    }

                    if (m) {
                        for (int j = 0; j < employees.size(); j++) {
                            query = "SELECT * FROM attend WHERE course_id='" + item.getString("id") + "' AND employee_id='" + employees.get(j) + "'";
                            ResultSet rs3 = statement.executeQuery(query);
                            if (!rs3.next()) {
                                query = "INSERT INTO attend (course_id,employee_id) VALUES ('" + item.getString("id") + "','" + employees.get(j) + "')";
                                if (statement.executeUpdate(query) == 0) {
                                    success = false;
                                    message += "Fail in adding course " + item.getString("id") + " to the employee's plan \n";
                                }

                            }
                        }
                    }

                } else {
                    query = "SELECT * FROM plan WHERE course_id='" + item.getString("id") + "' AND department='" + department + "'";
                    ResultSet rs2 = statement.executeQuery(query);
                    if (rs2.next()) {
                        query = "DELETE FROM plan WHERE course_id='" + item.getString("id") + "' AND department='" + department + "'";
                        if (statement.executeUpdate(query) == 0) {
                            success = false;
                            message += "Fail in deleting course " + item.getString("id") + " from the plan \n";
                        }

                        for (int j = 0; j < employees.size(); j++) {
                            query = "DELETE FROM attend WHERE course_id='" + item.getString("id") + "' AND employee_id='" + employees.get(j) + "'";
                            statement.executeUpdate(query);
                        }

                    }
                }
                query = "SELECT * FROM plan WHERE course_id='" + item.getString("id") + "'";
                ResultSet rs4 = statement.executeQuery(query);
                if (rs4.next()) {
                    query = "UPDATE course SET is_in_plan=1 WHERE course_id='" + item.getString("id") + "'";
                } else {
                    query = "UPDATE course SET is_in_plan=0 WHERE course_id='" + item.getString("id") + "'";
                }
                if (statement.executeUpdate(query) == 0) {
                    success = false;
                    message += "Fail! \n";
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

}
