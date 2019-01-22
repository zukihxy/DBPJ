
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
 * Servlet implementation class QueryEmployee
 */
@WebServlet("/QueryEmployee")
public class QueryEmployee extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryEmployee() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        DBConnection connection = new DBConnection();
        PrintWriter out = response.getWriter();
        String type = request.getParameter("type");
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        Cookie[] cookies = request.getCookies();
        String userid = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("id")) {
                userid = cookie.getValue();
            }
        }
        try {
            Statement statement = connection.getConnection().createStatement();
            String query = "SELECT department FROM employee WHERE person_id='" + userid + "'";
            ResultSet rs = statement.executeQuery(query);
            String department = "";
            if (rs.next()) {
                department = rs.getString("department");
            }
            rs.close();
            if (type.equals("plan")) {
                query = "SELECT * FROM plan,teacher,offer,course WHERE course.course_id = offer.course_id AND course.course_id=plan.course_id AND teacher.person_id=offer.teacher_id AND department='"
                        + department + "'";
                ResultSet rs1 = statement.executeQuery(query);
                boolean success = false;
                while (rs1.next()) {
                    success = true;
                    JSONObject item = new JSONObject();
                    item.put("course_id", rs1.getString("plan.course_id"));
                    item.put("course_name", rs1.getString("course_name"));
                    item.put("teacher_name", rs1.getString("name"));
                    item.put("total_time", rs1.getString("total_time"));
                    if (rs1.getBoolean("mandatory")) {
                        item.put("mandatory", "true");
                    } else {
                        item.put("mandatory", "false");
                    }
                    array.put(item);
                }
                int len = array.length();
                for (int i = 0; i < len; i++) {
                    JSONObject item = array.getJSONObject(i);
                    query = "SELECT * FROM attend WHERE course_id='" + item.getString("course_id") + "'";
                    ResultSet rs2 = statement.executeQuery(query);
                    if (rs2.next()) {
                        item.put("choose", "true");
                    } else {
                        item.put("choose", "false");
                    }
                }
                if (success) {
                    result.put("result", "1");
                    result.put("message", "succeed!");
                    result.put("courses", array);
                } else {
                    result.put("department", department);
                    result.put("result", "0");
                    result.put("message", "The plan has not been made yet!");
                }
            } else {
                query = "SELECT * FROM course,attend WHERE course.course_id=attend.course_id AND employee_id='" + userid
                        + "'";
                ResultSet rs1 = statement.executeQuery(query);
                boolean success = false;
                while (rs1.next()) {
                    success = true;
                    JSONObject item = new JSONObject();
                    item.put("course_id", rs1.getString("course.course_id"));
                    item.put("course_name", rs1.getString("course_name"));
                    if (rs1.getInt("exam_times") == 1 && !rs1.getBoolean("pass") && !rs1.getBoolean("apply_retest")) {
                        int money = calculateMoney(rs1.getDate("score_date"));
                        if (money != -1) {
                            item.put("need_retest", "true");
                            item.put("money", money);
                        } else {
                            item.put("need_retest", "false");
                        }
                    } else {
                        item.put("need_retest", "false");
                    }
                    if (rs1.getInt("exam_times") > 0) {
                        if (rs1.getBoolean("pass")) {
                            item.put("score", "pass");
                        } else {
                            item.put("score", "fail");
                        }
                    } else {
                        item.put("score", "no score yet");
                        item.put("pass", "no score yet");
                    }
                    array.put(item);
                }
                if (success) {
                    result.put("message", "succeed!");
                    result.put("courses", array);
                } else {
                    result.put("message", "You have no course");
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

    private int calculateMoney(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date = Date.valueOf(sdf.format(date));
        String Nowtime = sdf.format(Calendar.getInstance().getTime());
        Date bdate = Date.valueOf(Nowtime);
        // bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        int days = Integer.parseInt(String.valueOf(between_days));
        int money = 0;
        if (days <= 7) {
            money = 50;
        } else if (8 <= days && days <= 14) {
            money = 100;
        } else {
            money = -1;
        }
        return money;
    }

}
