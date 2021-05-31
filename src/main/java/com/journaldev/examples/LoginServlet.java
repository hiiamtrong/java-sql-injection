package com.journaldev.examples;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        boolean success = false;
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // Unsafe query which uses string concatenation
            String query = "select * from tbluser where username='" + username + "' and password = '" + password + "'";
//        password to injection: ' or '1'='1
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/user", "root", "2000");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                // Login Successful if match is found
                success = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (Exception e) {
            }
        }
        if (success) {
            Cookie cookie = new Cookie("myCookie", "myCookieValue");
            response.addCookie(cookie);
//            response.setHeader("Content-Type", "text/plain");
//            response.setHeader("success", "yes");
//            PrintWriter writer = response.getWriter();
//            writer.write(query);
//            writer.write("\nnot secure");
//            writer.close();
              response.sendRedirect("home.html");
        } else {
            response.sendRedirect("error.html");
        }
    }
}
