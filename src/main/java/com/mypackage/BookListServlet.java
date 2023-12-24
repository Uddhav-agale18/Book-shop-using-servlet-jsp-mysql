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

@WebServlet("/booklist")
public class BookListServlet extends HttpServlet {
	private static final String query = "Select id,bookname,bookedition,bookprice from book_details";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter pw = response.getWriter();
		response.setContentType("text/html");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "12345678");
				PreparedStatement ps = conn.prepareStatement(query);) {
			ResultSet rs = ps.executeQuery();
			pw.println("<h1 style='text-align: center; background-color: navy; color: white; padding: 10px;'>Book Details</h1>");
			pw.println("<style>");
			pw.println("table {");
			pw.println("  width: 100%;");
			pw.println("  border-collapse: collapse;");
			pw.println("  margin: 20px 0;");
			pw.println("}");

			pw.println("th, td {");
			pw.println("  border: 1px solid #ddd;");
			pw.println("  padding: 8px;");
			pw.println("  text-align: left;");
			pw.println("}");

			pw.println("th {");
			pw.println("  background-color: #f2f2f2;");
			pw.println("}");

			pw.println("@media (max-width: 600px) {");
			pw.println("  th, td {");
			pw.println("    display: block;");
			pw.println("    width: 100%;");
			pw.println("    box-sizing: border-box;");
			pw.println("  }");
			pw.println("}");
			pw.println("</style>");

			pw.println("<table>");
			pw.println("<tr>");
			pw.println("<th>Book Id</th>");
			pw.println("<th>Book Name</th>");
			pw.println("<th>Book Edition</th>");
			pw.println("<th>Book Price</th>");
			pw.println("<th>Edit</th>");
			pw.println("<th>Delete</th>");
			pw.println("</tr>");

			while (rs.next()) {
			    pw.println("<tr>");
			    pw.println("<td>" + rs.getInt(1) + "</td>");
			    pw.println("<td>" + rs.getString(2) + "</td>");
			    pw.println("<td>" + rs.getString(3) + "</td>");
			    pw.println("<td>" + rs.getFloat(4) + "</td>");
			    pw.println("<td><a href='update?id=" + rs.getInt(1) + "'>Edit</a></td>");
			    pw.println("<td><a href='deleteurl?id=" + rs.getInt(1) + "'>Delete</a></td>");
			    pw.println("</tr>");
			}

			pw.println("</table>");


		} catch (SQLException e) {
			e.printStackTrace();
			pw.println("<h1>" + e.getMessage() + "</h1>");
		} catch (Exception ex) {
			ex.printStackTrace();
			pw.println("<h1>" + ex.getMessage() + "</h1>");
		}
		pw.println("<a href='home.html' > Go Back to Home</a>");


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}