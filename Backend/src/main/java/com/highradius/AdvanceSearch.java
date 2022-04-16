package com.highradius;
//import com.xadmin.backened.bean.User_hrc;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.jasper.tagplugins.jstl.core.If;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@WebServlet("/AdvanceSearch")
public class AdvanceSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String cust_num;
	String doc_Id;
	String invoice_Id;
	String business_year;
       
    
    public AdvanceSearch() {
        super();
        
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		try {            			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/shivansh?allowPublicKeyRetrieval=true&useSSL=false","root","shivanshsingh328");
		
			String sql = "SELECT * FROM winter_internship WHERE ";

		  
			if (cust_num != "") {
				int new_cust_num = Integer.parseInt(cust_num);
				sql += "cust_number = "+new_cust_num+" AND ";
				cust_num="";
			}
			if (doc_Id != "") {
				sql += "doc_id = " + doc_Id + " AND ";
			}
			
			if (invoice_Id != "")
			{
				int new_inv_id = Integer.parseInt(invoice_Id);
				sql += "invoice_id = " + new_inv_id + " AND ";
			}
			
			if (business_year != "")
			{
				sql += "buisness_year = " + business_year + " AND ";
			}
			
			sql += " is_deleted = 0";
			
			Statement st = connection.createStatement();
			System.out.println(sql);
			
			List<Pojo> AllCustomer = new ArrayList<Pojo>();
			ResultSet rs = st.executeQuery(sql);
			
			while (rs.next()) {
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
				
				AllCustomer.add(tempHrc);

			}
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String invoices = gson.toJson(AllCustomer);
			response.setContentType("application/json");
			response.getWriter().write(invoices.toString());
			
			connection.close();
			st.close();
			rs.close();

		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			 cust_num = request.getParameter("cust_number");
			 doc_Id = request.getParameter("document_id");
			 invoice_Id = request.getParameter("invoice_id");
			 business_year = request.getParameter("buisness_year");
			 
				System.out.println(cust_num );
				System.out.println(doc_Id);
				System.out.println(invoice_Id);
				System.out.println(business_year);
			 
			 doGet(request, response);
		} catch (Exception e) {
		  e.printStackTrace();
		}
	
	
	}

}