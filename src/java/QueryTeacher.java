
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
 * Servlet implementation class QueryTeacher
 */
@WebServlet("/QueryTeacher")
public class QueryTeacher extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryTeacher() {
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
        String type = request.getParameter("type");
        Cookie[] cookies = request.getCookies();
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        String userid = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("id")) {
                userid = cookie.getValue();
            }
        }

        try {
            Statement statement = connection.getConnection().createStatement();
            String query;
            if (type.equals("course")) {
                query = "SELECT * FROM offer,course WHERE offer.course_id=course.course_id AND offer.teacher_id='" + userid + "'";
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    JSONObject item = new JSONObject();
                    item.put("course_id", rs.getString("course_id"));
                    item.put("course_name", rs.getString("course_name"));
                    item.put("total_time", rs.getString("total_time"));
                    array.put(item);
                }
                result.put("courses", array);
            } else if (type.equals("delete")) {
                query = "SELECT * FROM offer,course WHERE offer.course_id=course.course_id AND offer.teacher_id='" + userid + "' AND is_in_plan=" + false;
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    JSONObject item = new JSONObject();
                    item.put("course_id", rs.getString("course_id"));
                    item.put("course_name", rs.getString("course_name"));
                    item.put("total_time", rs.getString("total_time"));
                    array.put(item);
                }
                result.put("courses", array);
            } else if (type.equals("upload")) {//course_id
                String course_id = request.getParameter("cid");
                String employee_id = request.getParameter("eid");
                if (course_id.equals("")) {
                    query = "SELECT * FROM attend,offer WHERE attend.course_id=offer.course_id AND attend.employee_id='" + employee_id + "' AND teacher_id='" + userid + "'";
                } else if (employee_id.equals("")) {
                    query = "SELECT * FROM attend,offer WHERE attend.course_id=offer.course_id AND offer.course_id='" + course_id + "' AND teacher_id='" + userid + "'";
                } else {
                    query = "SELECT * FROM attend,offer WHERE attend.course_id=offer.course_id AND offer.course_id='" + course_id + "' AND teacher_id='" + userid + "' AND attend.employee_id='" + employee_id + "'";
                }
                ResultSet rs = statement.executeQuery(query);
                boolean success = false;
                while (rs.next()) {
                    success = true;
                    if (rs.getInt("exam_times") == 0) {
                        JSONObject item = new JSONObject();
                        item.put("eid", rs.getString("employee_id"));
                        item.put("cid", rs.getString("offer.course_id"));
                        array.put(item);
                    }
                }
                if (success) {
                    result.put("result", "1");
                    result.put("users", array);
                } else {
                    result.put("eid", employee_id);
                    result.put("cid", course_id);
                    result.put("result", "0");
                    result.put("message", "The employee hasn't chosen this course or this is not your course");
                }
            } else if (type.equals("update")) {
                String course_id = request.getParameter("cid");
                String employee_id = request.getParameter("eid");
                if (course_id.equals("")) {
                    query = "SELECT * FROM attend,offer WHERE attend.course_id=offer.course_id AND attend.employee_id='" + employee_id + "' AND teacher_id='" + userid + "'";
                } else if (employee_id.equals("")) {
                    query = "SELECT * FROM attend,offer WHERE attend.course_id=offer.course_id AND offer.course_id='" + course_id + "' AND teacher_id='" + userid + "'";
                } else {
                    query = "SELECT * FROM attend,offer WHERE attend.course_id=offer.course_id AND offer.course_id='" + course_id + "' AND teacher_id='" + userid + "' AND attend.employee_id='" + employee_id + "'";
                }
                ResultSet rs = statement.executeQuery(query);
                boolean success = false;
                while (rs.next()) {
                    success = true;
                    if (rs.getInt("exam_times") == 1 && rs.getBoolean("permit_retest")) {
                        JSONObject item = new JSONObject();
                        item.put("eid", rs.getString("employee_id"));
                        item.put("cid", rs.getString("attend.course_id"));
                        array.put(item);
                    }
                }
                if (success) {
                    result.put("result", "1");
                    result.put("users", array);
                } else {
                    result.put("result", "0");
                    result.put("message", "No one to update or this is not your course");
                }
            } else if (type.equals("retest")) {
                query = "SELECT * FROM attend,offer WHERE attend.course_id=offer.course_id AND teacher_id='" + userid + "'";
                ResultSet rs = statement.executeQuery(query);
                boolean success = false;
                while (rs.next()) {
                    success = true;
                    if (rs.getInt("exam_times") == 1 && !rs.getBoolean("permit_retest") && rs.getBoolean("apply_retest")) {
                        JSONObject item = new JSONObject();
                        item.put("employee_id", rs.getString("employee_id"));
                        item.put("course_id", rs.getString("attend.course_id"));
                        array.put(item);
                    }
                }
                if (success) {
                    result.put("result", "1");
                    result.put("users", array);
                } else {
                    result.put("result", "0");
                    result.put("message", "No one to update or this is not your course");
                }
            }
            else if (type.equals("name")){
                String id = request.getParameter("cid");
                boolean offer = false;
                query = "SELECT * FROM offer WHERE teacher_id='" + userid + "' AND course_id='" + id + "'" ;
                ResultSet rs1 = statement.executeQuery(query);
                if (rs1.next()) offer = true;
                if (offer) {
                    query = "SELECT * FROM attend WHERE course_id ='" + id + "'";
                    ResultSet rs = statement.executeQuery(query);
                    while (rs.next()) {
                    JSONObject item = new JSONObject();
                    item.put("employee_id", rs.getString("employee_id"));
                    array.put(item);
                    }
                    result.put("eid", array);
                    result.put("result", "1");
                }
                else {
                    result.put("result", "0");
                    result.put("message", "You can't get the name list for the course not offered by you");
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

}
