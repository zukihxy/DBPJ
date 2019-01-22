
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
 * Servlet implementation class QueryCEO
 */
@WebServlet("/QueryCEO")
public class QueryCEO extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryCEO() {
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
        String key = request.getParameter("key");
        String type = request.getParameter("type");
        JSONObject result = new JSONObject();
        try {
            Statement statement = connection.getConnection().createStatement();
            String query;
            if (key.equals("All")) {
                if (type.equals("teacher")) {
                    queryTeacher(statement, "", result);
                } else if (type.equals("course")) {
                    queryCourse(statement, "SELECT * FROM course", result, false, "");
                } else if (type.equals("employee")) {
                    queryEmployee(statement, "", result);
                } else {
                    queryChief(statement, "", result);
                }
            } else if (key.equals("Name")) {
                String name = request.getParameter("id");
                if (type.equals("teacher")) {
                    queryTeacher(statement, " WHERE name='" + name + "'", result);
                } else if (type.equals("employee")) {
                    queryEmployee(statement, " WHERE name='" + name + "'", result);
                } else if (type.equals("course")) {
                    queryCourse(statement, "SELECT * FROM course WHERE course_name='" + name + "'", result, false, "");
                } else {
                    queryChief(statement, " WHERE name='" + name + "'", result);
                }
            } else {
                String id = request.getParameter("id");
                if (type.equals("teacher")) {
                    queryTeacher(statement, " WHERE person_id='" + id + "'", result);
                } else if (type.equals("employee")) {
                    queryEmployee(statement, " WHERE person_id='" + id + "'", result);
                } else if (type.equals("course")) {
                    queryCourse(statement, "SELECT * FROM course,attend WHERE course.course_id=attend.course_id AND course.course_id='" + id + "'", result, true, id);
                } else {
                    queryChief(statement, " WHERE person_id='" + id + "'", result);
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

    }

    private void queryTeacher(Statement statement, String constraint, JSONObject result) throws SQLException, JSONException {
        String query = "SELECT * FROM teacher" + constraint;
        ResultSet rs = statement.executeQuery(query);
        JSONArray array = new JSONArray();
        boolean success = false;
        while (rs.next()) {
            success = true;
            JSONObject item = new JSONObject();
            item.put("person_id", rs.getString("person_id"));
            item.put("name", rs.getString("name"));
            item.put("email", rs.getString("email"));
            item.put("phone", rs.getString("phone"));
            item.put("sex", rs.getString("sex"));
            array.put(item);
        }
        if (success) {
            result.put("result", "1");
            result.put("users", array);
        } else {
            result.put("result", "0");
            result.put("error", "Fail in searching");
        }
    }

    private void queryEmployee(Statement statement, String constraint, JSONObject result) throws SQLException, JSONException {
        String query = "SELECT * FROM employee" + constraint;
        ResultSet rs = statement.executeQuery(query);
        JSONArray array = new JSONArray();
        boolean success = false;
        while (rs.next()) {
            success = true;
            JSONObject item = new JSONObject();
            item.put("person_id", rs.getString("person_id"));
            item.put("name", rs.getString("name"));
            item.put("sex", rs.getString("sex"));
            item.put("salary", rs.getInt("salary"));
            item.put("addition", rs.getString("addition"));
            item.put("work_addr", rs.getString("work_addr"));
            item.put("work_age", rs.getInt("work_age"));
            item.put("department", rs.getString("department"));
            array.put(item);
        }
        if (success) {
            result.put("result", "1");
            result.put("users", array);
        } else {
            result.put("result", "0");
            result.put("error", "Fail in searching");
        }
    }

    private void queryChief(Statement statement, String constraint, JSONObject result) throws SQLException, JSONException {
        String query = "SELECT * FROM chief" + constraint;
        ResultSet rs = statement.executeQuery(query);
        JSONArray array = new JSONArray();
        boolean success = false;
        while (rs.next()) {
            success = true;
            JSONObject item = new JSONObject();
            item.put("person_id", rs.getString("person_id"));
            item.put("name", rs.getString("name"));
            item.put("sex", rs.getString("sex"));
            item.put("phone", rs.getFloat("phone"));
            item.put("department", rs.getString("department"));
            item.put("email", rs.getString("email"));
            item.put("work_addr", rs.getString("work_addr"));
            array.put(item);
        }
        if (success) {
            result.put("result", "1");
            result.put("users", array);
        } else {
            result.put("result", "0");
            result.put("error", "Fail in searching");
        }
    }

    private void queryCourse(Statement statement, String query, JSONObject result, boolean isID, String id) throws SQLException, JSONException {
        ResultSet rs = statement.executeQuery(query);
        JSONArray array = new JSONArray();
        boolean success = false;
        if (!isID) {
            while (rs.next()) {
                success = true;
                JSONObject item = new JSONObject();
                item.put("course_id", rs.getString("course_id"));
                item.put("name", rs.getString("course_name"));
                item.put("total_time", rs.getString("total_time"));
                array.put(item);
            }
        } else {
            while (rs.next()) {
                success = true;
                JSONObject item = new JSONObject();
                item.put("course_id", rs.getString("course_id"));
                item.put("name", rs.getString("course_name"));
                item.put("total_time", rs.getString("total_time"));
                item.put("employee_id", rs.getString("employee_id"));
                if (rs.getInt("exam_times") == 0) {
                    item.put("score", "no score yet");
                } else {
                    if (rs.getBoolean("pass")) {
                        item.put("score", "pass");
                    } else {
                        item.put("score", "fail");
                    }
                }
                array.put(item);
            }
            if (!success) {
                query = "SELECT * FROM course WHERE course_id='" + id + "'";
                ResultSet rs1 = statement.executeQuery(query);
                while (rs1.next()) {
                    success = true;
                    JSONObject item = new JSONObject();
                    item.put("course_id", rs1.getString("course_id"));
                    item.put("name", rs1.getString("course_name"));
                    item.put("total_time", rs1.getString("total_time"));
                    array.put(item);
                }
                result.put("score", "false");
            } else {
                result.put("score", "true");
            }
        }
        if (success) {
            result.put("result", "1");
            result.put("users", array);
        } else {
            result.put("result", "0");
            result.put("error", "Fail in searching");
        }
    }
}
