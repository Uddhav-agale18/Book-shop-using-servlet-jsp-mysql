package com.mypackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deleteurl")
public class DeleteServlet extends HttpServlet {
	 private static final String query = "delete from book_details  WHERE id=?";

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
	        	int count=ps.executeUpdate();
	        	if(count==1)
	        	{
	        		pw.println("<h2>Record Deleted Successefully...</h2>");
	        	}
	        	else
	        	{
	        		pw.println("<h2>Record not Deleted</h2>");
	        	}
	        } 
	        catch (SQLException e) {
	            e.printStackTrace();
	            pw.println("<h1>" + e.getMessage() + "</h1>");
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            pw.println("<h1>" + ex.getMessage() + "</h1>");
	        }
	        pw.println("<a href='home.html'>Home</a>");
	        pw.println("<br>");
			pw.println("<a href='booklist' >Book List</a>");

	    }

	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        doGet(request, response);
	    }
	}
