package com.highradius;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//import com.xadmin.backened.bean.User_hrc;

@WebServlet("/FetchServlet")
public class FetchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public FetchServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/shivansh?allowPublicKeyRetrieval=true&useSSL=false","root","shivanshsingh328");
			Statement st = connection.createStatement();
			String query="Select sl_no,business_code,cust_number,clear_date,buisness_year,doc_id,posting_date,document_create_date,due_in_date,invoice_currency,document_type,posting_id,total_open_amount,baseline_create_date,cust_payment_terms,invoice_id from  winter_internship  WHERE is_deleted!=1";
			ResultSet rs=st.executeQuery(query);
			ArrayList<Pojo> data = new ArrayList<>();
			
			while(rs.next())
			{
				 Pojo tempHrc= new Pojo();
				 tempHrc.setSl_no(rs.getInt("sl_no"));
					tempHrc.setBusiness_code(rs.getString("business_code"));
					tempHrc.setCust_number(rs.getString("cust_number"));
					tempHrc.setClear_date(rs.getString("clear_date"));
					tempHrc.setBuisness_year(rs.getString("buisness_year"));
					tempHrc.setDocument_id(rs.getString("doc_id"));
					tempHrc.setPosting_date(rs.getString("posting_date"));
					tempHrc.setDocument_create_date(rs.getString("document_create_date"));
					tempHrc.setDue_in_date(rs.getString("due_in_date"));
					tempHrc.setInvoice_currency(rs.getString("invoice_currency"));
					tempHrc.setDocument_type(rs.getString("document_type"));
					tempHrc.setPosting_id(rs.getString("posting_id"));
					tempHrc.setTotal_open_amount(rs.getString("total_open_amount"));
					tempHrc.setBaseline_create_date(rs.getString("baseline_create_date"));
					tempHrc.setCust_payment_terms(rs.getString("cust_payment_terms"));
					tempHrc.setInvoice_id(rs.getString("invoice_id"));
					data.add(tempHrc);
//				System.out.println(business_code +" " +clear_date+" "+buisness_year+" "+doc_id+" "+posting_date+" "+document_create_date);
			}
			Gson gson = new GsonBuilder().serializeNulls().create();
			String invoices  = gson.toJson(data);
			response.setContentType("application/json");
			try {
				response.getWriter().write(invoices);//getWriter() returns a PrintWriter object that can send character text to the client. 
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			rs.close();
			st.close();
			connection.close();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}