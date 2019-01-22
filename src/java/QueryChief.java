
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
import javax.servlet.http.Cookie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class QueryChief
 */
@WebServlet("/QueryChief")
public class QueryChief extends HttpServlet {
<<<<<<< HEAD

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryChief() {
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        DBConnection connection = new DBConnection();
        PrintWriter out = response.getWriter();
        String type = request.getParameter("type");
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            Statement statement = connection.getConnection().createStatement();
            String query;
            Cookie[] cookies = request.getCookies();
            String userid = "";
            boolean success = false;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("id")) {
                    userid = cookie.getValue();
                }
            }
            query = "SELECT department FROM chief WHERE person_id='" + userid + "'";
            ResultSet rs = statement.executeQuery(query);
            String department = "";
            if (rs.next()) {
                department = rs.getString("department");
            }
            if (type.equals("course")) {
                query = "SELECT * FROM course, teacher, offer WHERE course.course_id=offer.course_id AND teacher.person_id=offer.teacher_id";
                ResultSet rs1 = statement.executeQuery(query);
                while (rs1.next()) {
                    JSONObject item = new JSONObject();
                    item.put("course_id", rs1.getString("course_id"));
                    item.put("course_name", rs1.getString("course_name"));
                    item.put("teacher_name", rs1.getString("name"));
                    item.put("total_time", rs1.getString("total_time"));
                    array.put(item);
                }
                int len = array.length();
                for (int i = 0; i < len; i++) {
                    query = "SELECT mandatory FROM plan WHERE course_id='"
                            + array.getJSONObject(i).getString("course_id") + "' AND department='" + department + "'";
                    ResultSet rs2 = statement.executeQuery(query);
                    if (rs2.next()) {
                        if (rs2.getBoolean("mandatory")) {
                            array.getJSONObject(i).put("mandatory", "true");
                        } else {
                            array.getJSONObject(i).put("mandatory", "false");
                        }
                    } else {
                        array.getJSONObject(i).put("mandatory", "null");
                    }
                }
                result.put("result", "1");
                result.put("course", array);
            } else if (type.equals("employee")) {
                String key = request.getParameter("key");
                if (key.equals("ID")) {
                    String id = request.getParameter("id");
                    query = "SELECT * FROM employee WHERE person_id='" + id + "' AND department='" + department + "'";
                } else if (key.equals("Name")) {
                    String name = request.getParameter("id");
                    query = "SELECT * FROM employee WHERE name='" + name + "' AND department='" + department + "'";
                } else {
                    query = "SELECT * FROM employee WHERE department='" + department + "'";
                }
                ResultSet rs1 = statement.executeQuery(query);
                while (rs1.next()) {
                    success = true;
                    JSONObject item = new JSONObject();
                    item.put("person_id", rs1.getString("person_id"));
                    item.put("name", rs1.getString("name"));
                    item.put("sex", rs1.getString("sex"));
                    item.put("salary", rs1.getString("salary"));
                    item.put("addition", rs1.getString("addition"));
                    item.put("work_addr", rs1.getString("work_addr"));
                    item.put("work_age", rs1.getString("work_age"));
                    array.put(item);
                }
                if (success) {
                    result.put("result", "1");
                    result.put("key", key);
                    result.put("users", array);
                } else {
                    result.put("result", "");
                    result.put("message", "The employee is not in your department");

                }
            } else {
                String key = request.getParameter ("key");
                String id = request.getParameter("id");
                if (key.equals("ID")){
                    query = "SELECT * FROM course,attend,employee WHERE course.course_id=attend.course_id AND employee.person_id=attend.employee_id AND employee.person_id='"
                        + id + "' AND department='" + department + "'";
                }
                else {
                    query = "SELECT * FROM course,attend,employee WHERE course.course_id=attend.course_id AND employee.person_id=attend.employee_id AND employee.name='"
                        + id + "' AND department='" + department + "'";
                }
                
                
                ResultSet rs1 = statement.executeQuery(query);
                while (rs1.next()) {
                    success = true;
                    JSONObject item = new JSONObject();
                    item.put("course_id", rs1.getString("course_id"));
                    item.put("course_name", rs1.getString("course_name"));
                    if (rs1.getInt("exam_times") == 0) {
                        item.put("score", "no score");
                    } else {
                        if (rs1.getBoolean("pass")) {
                            item.put("score", "pass");
                        } else {
                            item.put("score", "fail");
                        }
                    }
                    array.put(item);
                }
                if (success) {
                    result.put("result", "1");
                    result.put("course", array);
                } else {
                    result.put("result", "0");
                    result.put("message", "The employee is not in your department");
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
=======
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QueryChief() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DBConnection connection = new DBConnection();
		PrintWriter out = response.getWriter();
		String type = request.getParameter("type");
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		try {
			Statement statement = connection.getConnection().createStatement();
			String query;
			Cookie[] cookies = request.getCookies();
			String userid = "";
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("id")) {
					userid = cookie.getValue();
				}
			}
			query = "SELECT department FROM chief WHERE person_id='" + userid + "'";
			ResultSet rs = statement.executeQuery(query);
			String department = "";
			if (rs.next())
				department = rs.getString("department");
			if (type.equals("course")) {
				query = "SELECT * FROM course, teacher, offer WHERE course.course_id=offer.course_id AND teacher.person_id=offer.teacher_id";
				ResultSet rs1 = statement.executeQuery(query);
				while (rs1.next()) {
					JSONObject item = new JSONObject();
					item.put("course_id", rs1.getString("course_id"));
					item.put("course_name", rs1.getString("course_name"));
					item.put("teacher_name", rs1.getString("name"));
					item.put("total_time", rs1.getString("total_time"));
					array.put(item);
				}
				int len = array.length();
				for (int i = 0; i < len; i++) {
					query = "SELECT mandatory FROM plan WHERE course_id='"
							+ array.getJSONObject(i).getString("course_id") + "' AND department='" + department + "'";
					ResultSet rs2 = statement.executeQuery(query);
					if (rs2.next()) {
						if (rs2.getBoolean("mandatory"))
							array.getJSONObject(i).put("mandatory", "true");
						else
							array.getJSONObject(i).put("mandatory", "false");
					} else
						array.getJSONObject(i).put("mandatory", "null");
				}
				result.put("result", "1");
				result.put("course", array);
			} else if (type.equals("employee")) {
				String key = request.getParameter("key");
				if (key.equals("ID")) {
					String id = request.getParameter("id");
					query = "SELECT * FROM employee WHERE person_id='" + id + "' AND department='" + department + "'";
				} else if (key.equals("Name")) {
					String name = request.getParameter("id");
					query = "SELECT * FROM employee WHERE name='" + name + "' AND department='" + department + "'";
				} else {
					query = "SELECT * FROM employee WHERE department='" + department + "'";
				}
				ResultSet rs1 = statement.executeQuery(query);
				while (rs1.next()) {
					JSONObject item = new JSONObject();
					item.put("person_id", rs1.getString("person_id"));
					item.put("name", rs1.getString("name"));
					item.put("sex", rs1.getString("sex"));
					item.put("salary", rs1.getString("salary"));
					item.put("addition", rs1.getString("addition"));
					item.put("work_addr", rs1.getString("work_addr"));
					item.put("work_age", rs1.getString("work_age"));
					array.put(item);
				}
				result.put("result", "1");
				result.put("key", key);
				result.put("users", array);
			} else {
				String id = request.getParameter("id");
				query = "SELECT * FROM course,attend,employee WHERE course.course_id=attend.course_id AND employee.person_id=attend.employee_id AND employee.person_id='"
						+ id + "' AND department='" + department + "'";
				ResultSet rs1 = statement.executeQuery(query);
				while (rs1.next()) {
					JSONObject item = new JSONObject();
					item.put("course_id", rs1.getString("course_id"));
					item.put("course_name", rs1.getString("course_name"));
					if (rs1.getInt("exam_times") == 0)
						item.put("score", "no score");
					else {
						if (rs1.getBoolean("pass"))
							item.put("score", "pass");
						else
							item.put("score", "fail");
					}
					array.put(item);
				}
				result.put("result", "1");
				result.put("course", array);
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
>>>>>>> a52860a1daa78ffdad8808c85d0ea7bf016594f0

}
