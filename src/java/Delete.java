
<<<<<<< HEAD
=======

>>>>>>> a52860a1daa78ffdad8808c85d0ea7bf016594f0
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
<<<<<<< HEAD
import java.util.ArrayList;
=======
>>>>>>> a52860a1daa78ffdad8808c85d0ea7bf016594f0

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class Delete
 */
@WebServlet("/Delete")
public class Delete extends HttpServlet {
<<<<<<< HEAD

    private static final long serialVersionUID = 1L;

=======
	private static final long serialVersionUID = 1L;
       
>>>>>>> a52860a1daa78ffdad8808c85d0ea7bf016594f0
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Delete() {
        super();
        // TODO Auto-generated constructor stub
    }

<<<<<<< HEAD
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
        String id = request.getParameter("id");
        String type = request.getParameter("type");
        DBConnection connection = new DBConnection();
        JSONObject result = new JSONObject();
        PrintWriter out = response.getWriter();

        String query;

        try {
            Statement statement = connection.getConnection().createStatement();
            if (type.equals("user")) {
                if (request.getCookies()[0].getValue().equals(id)) {
                    result.put("message", "You can't delete yourself!");
                } else {
                    query = "SELECT user_type FROM person WHERE person_id='" + id + "'";
                    ResultSet rs5 = statement.executeQuery(query);
                    String user_type = "";
                    if (rs5.next()) {
                        user_type = rs5.getString("user_type");
                    }
                    if (user_type.equals("chief")) {
                        boolean success = true;
                        String message = "";
                        query = "DELETE FROM chief WHERE person_id='" + id + "'";
                        if (statement.executeUpdate(query) == 0) {
                            success = false;
                            message += "Fail in delete from chief\n";
                        }
                        query = "DELETE FROM person WHERE person_id='" + id + "'";
                        if (statement.executeUpdate(query) == 0) {
                            success = false;
                            message += "Fail in delete from person\n";
                        }
                        if (success) {
                            message = "Success!";
                        }
                        result.put("message", message);
                    } else if (user_type.equals("employee")) {
                        boolean success = true;
                        String message = "";
//                        query = "DELETE FROM attend WHERE employee_id='" + id + "'";
//                        if (statement.executeUpdate(query) == 0) {
//                            success = false;
//                            message += "Fail in delete from attend\n";
//                        }
                        query = "DELETE FROM employee WHERE person_id='" + id + "'";
                        if (statement.executeUpdate(query) == 0) {
                            success = false;
                            message += "Fail in delete from employee\n";
                        }
                        query = "DELETE FROM person WHERE person_id ='" + id + "'";
                        if (statement.executeUpdate(query) == 0) {
                            success = false;
                            message += "Fail in delete from person\n";
                        }
                        if (success) {
                            message = "Success!";
                        }
                        result.put("message", message);
                    } else if (user_type.equals("teacher")) {
                        boolean success = true;
                        String message = "";
//                        query = "SELECT course_id FROM offer WHERE teacher_id='" + id + "'";
//                        ResultSet rs = statement.executeQuery(query);
//                        ArrayList<String> course = new ArrayList();
//                        while (rs.next()) {
//                            course.add(rs.getString("course_id"));
//                        }
//                        int size = course.size();
//                        for (int i = 0; i < size; i++) {
//                            query = "DELETE FROM attend WHERE course_id ='" + course.get(i) + "'";
//                            statement.executeUpdate(query);
//                            query = "DELETE FROM plan WHERE course_id ='" + course.get(i) + "'";
//                            statement.executeUpdate(query);
//                            query = "DELETE FROM offer WHERE course_id ='" + course.get(i) + "'";
//                            statement.executeUpdate(query);
//                            query = "DELETE FROM course WHERE course_id ='" + course.get(i) + "'";
//                            statement.executeUpdate(query);
//                        }
                        query = "DELETE FROM teacher WHERE person_id ='" + id + "'";
                        statement.executeUpdate(query);
                        query = "DELETE FROM person WHERE person_id ='" + id + "'";
                        statement.executeUpdate(query);
                        if (success) {
                            message = "Success!";
                        }
                        result.put("message", message);
                    } else {
                        query = "DELETE FROM person WHERE person_id='" + id + "'";
                        if (statement.executeUpdate(query) != 0) {
                            result.put("message", "Success!");
                        } else {
                            result.put("message", "Fail in deleting!");
                        }
                    }
                }
            }

            if (type.equals("employee")) {
                boolean success = true;
                String message = "";
//                query = "DELETE FROM attend WHERE employee_id='" + id + "'";
//                if (statement.executeUpdate(query) == 0) {
//                    success = false;
//                    message += "Fail in delete from attend\n";
//                }
                query = "DELETE FROM employee WHERE person_id='" + id + "'";
                if (statement.executeUpdate(query) == 0) {
                    success = false;
                    message += "Fail in delete from employee\n";
                }
//                query = "UPDATE person SET user_type = 'delete' WHERE person_id ='" + id + "'";
//                if (statement.executeUpdate(query) == 0) {
//                    success = false;
//                    message += "Fail in delete from attend\n";
//                }
                if (success) {
                    message = "Success!";
                }
                result.put("message", message);
            }

            if (type.equals("course")) {
                query = "SELECT is_in_plan FROM course WHERE course_id='" + id + "'";
                ResultSet rs = statement.executeQuery(query);
                if (rs.next()) {
                    if (rs.getBoolean("is_in_plan")) {
                        result.put("message", "The course cannot be deleted as it has already been added to a plan of department!");
                    } else {
//                        query = "DELETE FROM attend WHERE course_id='" + id + "'";
//                        statement.execute(query);
//                        query = "DELETE FROM offer WHERE course_id='" + id + "'";
//                        statement.execute(query);
                        query = "DELETE FROM offer WHERE course_id='" + id + "'";
                        statement.execute(query);

                        String success = "Fail in deleting. Please delete it again";
                        query = "SELECT * FROM attend WHERE course_id='" + id + "'";
                        ResultSet rs1 = statement.executeQuery(query);
                        if (!rs1.next()) {
                            query = "SELECT * FROM offer WHERE course_id='" + id + "'";
                            ResultSet rs2 = statement.executeQuery(query);
                            if (!rs2.next()) {
                                query = "SELECT * FROM course WHERE course_id='" + id + "'";
                                ResultSet rs3 = statement.executeQuery(query);
                                if (!rs3.next()) {
                                    success = "Success!";
                                }
                            }
                        }
                        result.put("message", success);
                    }
                }
            }
            out.println(result);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void deleteEmployee(String id) {

    }
=======
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		DBConnection connection = new DBConnection();
		JSONObject result =new JSONObject();
		PrintWriter out = response.getWriter();
		Cookie[] cookies = request.getCookies();
		String user_type = cookies[1].getValue();
		String query;
		
		try {
			Statement statement = connection.getConnection().createStatement();
			if (type.equals("user")){
				if(request.getCookies()[0].getValue().equals(id)){
					result.put("message", "You can't delete yourself!");
				}
				else{
					query = "DELETE FROM person WHERE person_id='" + id +"'";
					if(statement.executeUpdate(query) != 0)
						result.put("message", "Success!");
					else result.put("message", "Fail in deleting!");
				}
				
			}
				
			if (type.equals("employee")){
				query = "DELETE FROM attend WHERE employee_id='" + id +"'";
				statement.execute(query);
				query = "DELETE FROM employee WHERE person_id='" + id +"'";
				statement.execute(query);
				query = "UPDATE person SET user_type = 'delete' WHERE person_id ='" + id +"'";
				statement.execute(query);
				String success = "Fail in deleting. Please delete it again";
				query = "SELECT * FROM attend WHERE employee_id='" + id +"'";
				ResultSet rs1 = statement.executeQuery(query);
				if (!rs1.next()){
					query = "SELECT * FROM employee WHERE person_id='" + id +"'";
					ResultSet rs2 = statement.executeQuery(query);
					if (!rs2.next()){
						query = "SELECT user_type FROM person WHERE person_id='" + id +"'";
						ResultSet rs3 = statement.executeQuery(query);
						if (rs3.next()){
							if(rs3.getString("user_type").equals("delete")) success = "Success!";
						}
					}
				}
				result.put("message", success);			
			}
			
			if (type.equals("course")) {
				if (user_type.equals("teacher")){
					query = "SELECT is_in_plan FROM course WHERE course_id='" + id + "'";
					ResultSet rs = statement.executeQuery(query);
					if (rs.next()){
						if (rs.getBoolean("is_in_plan"))
							result.put("message", "The course cannot be deleted as it has already been added to a plan of department!");
						
						else {
							query = "DELETE FROM attend WHERE course_id='" + id +"'";
							statement.execute(query);
							query = "DELETE FROM offer WHERE course_id='" + id +"'";
							statement.execute(query);
							query = "DELETE FROM course WHERE course_id='" + id +"'";
							statement.execute(query);
							
							String success = "Fail in deleting. Please delete it again";
							query = "SELECT * FROM attend WHERE course_id='" + id +"'";
							ResultSet rs1 = statement.executeQuery(query);
							if (!rs1.next()){
								query = "SELECT * FROM offer WHERE course_id='" + id +"'";
								ResultSet rs2 = statement.executeQuery(query);
								if (!rs2.next()){
									query = "SELECT * FROM course WHERE course_id='" + id +"'";
									ResultSet rs3 = statement.executeQuery(query);
									if (!rs3.next()) success = "Success!";
								}
							}
							result.put("message", success);
						}
					}
				}
				
				else if(user_type.equals("employee")){
					query = "DELETE FROM attend WHERE course_id='" + id +"' AND employee_id='"+ cookies[0].getValue() +"'";
					if(statement.executeUpdate(query) != 0)
						result.put("message", "Success!");
					else result.put("message", "Fail in deleting!");
				}
			}
			out.println(result);
				
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
