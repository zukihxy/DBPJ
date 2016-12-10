/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author zuki_hxy
 */
@WebServlet(urlPatterns = {"/edit_info"})
public class edit_info extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
// TODO Auto-generated method stub
                response.setCharacterEncoding("utf-8");

        String type = request.getParameter("type");
        String user = request.getParameter("user");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("error when loading jdbc");
            e.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3306/pj2?useUnicode=true&characterEncoding=UTF-8";
        String username = "root";
        String password = "270329zuki";
        Connection con;
        try {
            con = DriverManager.getConnection(url, username, password);
            String sql;
            PrintWriter out = response.getWriter();
            Statement stm;
            stm = con.createStatement();
            
            if (type.equals("pass")) {
                String passwd = request.getParameter("password");
                sql = "select `password` from `user` where (`username` = '" + user + "' and `password` ='" + passwd + "')";
                ResultSet rs = stm.executeQuery(sql);

                if (rs.next()) {
                    out.print("Unchanged password!");
                } else {
                    sql = "update `user` set `password`='" + passwd + "' where `username`='" + user + "'";
                    stm.executeUpdate(sql);
                    out.print("0");
                }
            }
            if (type.equals("intro")) {
                String selfIntro = request.getParameter("selfIntro");
                String context = selfIntro.replace("'", "''");
                sql = "update `user` set `selfIntro`='" + context + "' where `username`='" + user + "'";
                stm.executeUpdate(sql);
                out.print("0");
            }
            if (type.equals("head")) {
                String head = request.getParameter("head");
                sql = "update `user` set `head`='" + head + "' where `username`='" + user + "'";
                stm.executeUpdate(sql);
                out.print("0");
            }

        } catch (SQLException se) {
            System.out.println("error when connecting database");
            se.printStackTrace();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
