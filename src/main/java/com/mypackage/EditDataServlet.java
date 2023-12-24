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

@WebServlet("/editurl")
public class EditDataServlet extends HttpServlet {
	
	 private static final String query = "Update book_details set bookname=?, bookedition=?,bookprice=? WHERE id=?";

	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	        PrintWriter pw = response.getWriter();
	        response.setContentType("text/html");
	        int id = Integer.parseInt(request.getParameter("id"));
	        String bookName=request.getParameter("bookName");
	        String bookEdition=request.getParameter("bookEdition");
	        float bookPrice=Float.parseFloat(request.getParameter("bookPrice"));
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "12345678");
	             PreparedStatement ps = conn.prepareStatement(query)) {
	        	
	        	ps.setString(1, bookName);
	        	ps.setString(2, bookEdition);
	        	ps.setFloat(3, bookPrice);
	        	ps.setInt(4, id);
	        	int count=ps.executeUpdate();
	        	if(count==1)
	        	{
	        		pw.println("<h2>Record Updated Successefully...</h2>");
	        	}
	        	else
	        	{
	        		pw.println("<h2>Record not Updated</h2>");
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
