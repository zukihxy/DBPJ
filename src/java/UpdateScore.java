
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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class UploadScore
 */
@WebServlet("/UpdateScore")
public class UpdateScore extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateScore() {
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
<<<<<<< HEAD
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
=======
>>>>>>> a52860a1daa78ffdad8808c85d0ea7bf016594f0
        DBConnection connection = new DBConnection();
        PrintWriter out = response.getWriter();
        String scores = request.getParameter("score");
        String message = "";
        JSONObject result = new JSONObject();
        boolean success = true;
        try {
            Statement statement = connection.getConnection().createStatement();
            JSONArray array = new JSONArray(scores);
            String query;
            int len = array.length();
            for (int i = 0; i < len; i++) {
                JSONObject item = array.getJSONObject(i);
                boolean pass = false;
                if (item.getString("score").equals("true")) {
                    pass = true;
                }
                query = "UPDATE attend SET pass = " + pass + " WHERE course_id='" + item.getString("cid") + "' AND employee_id='" + item.getString("eid") + "'";
                if (statement.executeUpdate(query) == 0) {
                    success = false;
                    message += "Fail in upload score for " + item.getString("employee_id");
                }
                query = "SELECT exam_times FROM attend WHERE course_id='" + item.getString("cid") + "' AND employee_id='" + item.getString("eid") + "'";
                ResultSet rs = statement.executeQuery(query);
                int times = 0;
                if (rs.next()) {
                    times = rs.getInt("exam_times");
                    times++;
                }
                query = "UPDATE attend SET exam_times = " + times + " WHERE course_id='" + item.getString("cid") + "' AND employee_id='" + item.getString("eid") + "'";
                if (statement.executeUpdate(query) == 0) {
                    success = false;
                    message += "Fail in upload exam_times for " + item.getString("eid");
                }
                query = "UPDATE course SET score_date = CURDATE() WHERE course_id='" + item.getString("cid") + "'";
                if (statement.executeUpdate(query) == 0) {
                    success = false;
                    message += "Fail in upload score date ";
                }
<<<<<<< HEAD
                query = "SELECT count(pass) FROM attend WHERE employee_id='" + item.getString("eid") + "' AND pass=" + false;
                ResultSet rs1 = statement.executeQuery(query);
                if (rs1.next()) {
                    if (rs1.getInt("count(pass)") == 0) {
                        query = "SELECT * FROM employee WHERE person_id='" + item.getString("eid") + "'";
                        ResultSet rs2 = statement.executeQuery(query);
                        float addition = 0;
                        if (rs2.next()) {
                            addition = rs2.getFloat("addition");
                        }
                        addition += 0.1;
                        query = "UPDATE employee SET addition=" + addition + " WHERE person_id='" + item.getString("eid") + "'";
                        if (statement.executeUpdate(query) == 0) {
                            success = false;
                            message += "Fail in upload employee addition ";
                        }
                    }

                }
=======
>>>>>>> a52860a1daa78ffdad8808c85d0ea7bf016594f0
            }

            if (success) {
                result.put("result", "1");
                result.put("message", "Succeed!");
            } else {
                result.put("result", "0");
                result.put("message", message);
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
