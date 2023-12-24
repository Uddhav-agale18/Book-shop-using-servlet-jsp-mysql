package com.mypackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    private static final String query = "SELECT bookname, bookedition, bookprice FROM book_details WHERE id=?";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter pw = response.getWriter();
        response.setContentType("text/html");
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "12345678");
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            rs.next();

            pw.println("<style>");
            pw.println("table {");
            pw.println("  width: 50%;");
            pw.println("  border-collapse: collapse;");
            pw.println("  margin: 20px auto;");
            pw.println("}");

            pw.println("td, input {");
            pw.println("  padding: 8px;");
            pw.println("}");

            pw.println("input[type='submit'], input[type='reset'] {");
            pw.println("  background-color: #4caf50;");
            pw.println("  color: white;");
            pw.println("  border: none;");
            pw.println("  padding: 10px 20px;");
            pw.println("  text-align: center;");
            pw.println("  text-decoration: none;");
            pw.println("  display: inline-block;");
            pw.println("  font-size: 16px;");
            pw.println("  margin: 4px 2px;");
            pw.println("  cursor: pointer;");
            pw.println("}");

            pw.println("</style>");

            pw.println("<form action='editurl?id=" + id + "' method='post'>");
            pw.println("<table align='center'>");

            // Book Name
            pw.println("<tr>");
            pw.println("<td>Book Name:</td>");
            pw.println("<td><input type='text' name='bookName' value='" + rs.getString(1) + "'></td>");
            pw.println("</tr>");

            // Book Edition
            pw.println("<tr>");
            pw.println("<td>Book Edition:</td>");
            pw.println("<td><input type='text' name='bookEdition' value='" + rs.getString(2) + "'></td>");
            pw.println("</tr>");

            // Book Price
            pw.println("<tr>");
            pw.println("<td>Book Price:</td>");
            pw.println("<td><input type='text' name='bookPrice' value='" + rs.getFloat(3) + "'></td>");
            pw.println("</tr>");

            // Submit and Reset Buttons
            pw.println("<tr>");
            pw.println("<td colspan='2' align='center'>");
            pw.println("<input type='submit' value='Edit'>");
            pw.println("<input type='reset' value='Cancel'>");
            pw.println("</td>");
            pw.println("</tr>");

            pw.println("</table>");
            pw.println("</form>");

        } catch (SQLException e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h1>");
        } catch (Exception ex) {
            ex.printStackTrace();
            pw.println("<h1>" + ex.getMessage() + "</h1>");
        }
        pw.println("<a href='home.html'>Home</a>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
