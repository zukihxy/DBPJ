/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

/**
 *
 * @author apple
 */
@WebServlet(urlPatterns = {"/login"})
public class login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        Cookie[] cookies = request.getCookies();
        String name = "?";
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("username"))
                name = cookies[i].getValue();
        }
        /* TODO output your page here. You may use following sample code. */
//        String user = request.getParameter("username");
//        String passwd = request.getParameter("password");
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            System.out.println("error when loading jdbc");
//            e.printStackTrace();
//        }
//        String url = "jdbc:mysql://localhost:3306/dbpj?useUnicode=true&characterEncoding=UTF-8";
//        String username = "root";
//        String password = "270329zuki";
//        Connection con;
        PrintWriter out = response.getWriter();
        out.print(name);
//        try {
//            con = DriverManager.getConnection(url, username, password);
//            String sql = "select * from `user` where username = '" + user
//                    + "' and password = '" + passwd + "'";
//            Statement stm;
//            stm = con.createStatement();
//            ResultSet rs = stm.executeQuery(sql);
//            PrintWriter out = response.getWriter();
//out.print("1110");
////            if (rs.next()) {                        
////                out.print("0");
////            } else {
////                out.print("Wrong username or password!");
////            }
//        } catch (SQLException se) {
//            System.out.println("error when connecting database");
//            se.printStackTrace();
//        }
        //重定向

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
        processRequest(request, response);
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
