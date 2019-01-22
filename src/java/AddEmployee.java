
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
 * Servlet implementation class AddEmployee
 */
@WebServlet("/AddEmployee")
public class AddEmployee extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddEmployee() {
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
        String users = request.getParameter("users");
        JSONObject result = new JSONObject();
        Cookie[] cookies = request.getCookies();
        String userid = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("id")) {
                userid = cookie.getValue();
            }
        }

        try {
            Statement statement = connection.getConnection().createStatement();
            JSONArray array = new JSONArray(users);
            int len = array.length();
            boolean success = true;
            String message = "";
            String query = "SELECT department FROM chief WHERE person_id='" + userid + "'";
            String query2 = "";
            ResultSet rs = statement.executeQuery(query);
            String department = "";
            if (rs.next()) {
                department = rs.getString("department");
            }

            int index = 0;
            ArrayList<String> add = new ArrayList();
            while (true) {
                if (index < len) {
                    JSONObject item = array.getJSONObject(index++);
                    String query1 = "SELECT * FROM employee WHERE person_id='" + item.getString("id") + "'";
                    ResultSet rs1 = statement.executeQuery(query1);
                    if (!rs1.next()) {
                        query = "INSERT INTO employee VALUES ('" + item.getString("id") + "', '" + item.getString("name") + "', '" + item.getString("sex") + "', '" + item.getString("salary") + "', '" + item.getString("addition") + "', '" + item.getString("work_addr") + "', '" + item.getString("work_age") + "', '" + department + "')";
                        query2 = "INSERT INTO person VALUES ('" + item.getString("id") + "','0000','add')";
                        add.add(item.getString("id"));
                        break;
                    } else {
                        success = false;
                        message += "Fail in adding employee " + item.getString("id") + ", as the id has been used \n";
                    }
                } else {
                    success = false;
                    message += "Fail in adding employee as the id has been used \n";
                }
            }

            for (int i = index; i < len; i++) {
                JSONObject item = array.getJSONObject(i);
                String query1 = "SELECT * FROM employee WHERE person_id='" + item.getString("id") + "'";
                ResultSet rs1 = statement.executeQuery(query1);
                if (!rs1.next()) {
                    query += ",('" + item.getString("id") + "', '" + item.getString("name") + "', '" + item.getString("sex") + "', '" + item.getString("salary") + "', '" + item.getString("addition") + "', '" + item.getString("work_addr") + "', '" + item.getString("work_age") + "', '" + department + "')";
                    query2 += ",('" + item.getString("id") + "','0000','add')";
                    add.add(item.getString("id"));
                } else {
                    success = false;
                    message += "Fail in adding employee " + item.getString("id") + ", as the id has been used \n";
                }

            }
            if (statement.executeUpdate(query2) == 0) {
                success = false;
                message += "Fail in adding users ";
            } else if (statement.executeUpdate(query) == 0) {
                success = false;
                message += "Fail in adding users ";
            }
            query = "SELECT course_id FROM plan WHERE mandatory=" + true + " AND department = '" + department + "'";
            ResultSet rs5 = statement.executeQuery(query);
            int size1 = add.size();
            ArrayList<String> lessons = new ArrayList();
            while (rs5.next()) {
                lessons.add(rs5.getString("course_id"));
            }
            int size2 = lessons.size();
            for (int i = 0; i < size1; i++) {
                for (int j = 0; j < size2; j++) {
                    query = "INSERT INTO attend (course_id,employee_id) VALUES ('" + lessons.get(j) + "','" + add.get(i) + "')";
                    if (statement.executeUpdate(query) == 0) {
                        success = false;
                        message += "Fail in adding courses to new employee's plan ";
                    }
                }

            }

            if (success) {
                message = "succeed";
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
