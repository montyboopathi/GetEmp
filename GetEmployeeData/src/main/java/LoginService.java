
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.JSONObject;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginService extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		String employeeName = request.getParameter("employeeName");
		String employeePassword = request.getParameter("employeePassword");
		String departmentID = request.getParameter("departmentID");

		Connection con = null;
		Statement stat = null;
		ResultSet res = null;
		SessionValidation d = new SessionValidation();
		try {
			if (d.validate(employeeName, employeePassword, departmentID)) {
				HttpSession ses = request.getSession();
				ses.setAttribute("departmentID", departmentID);
			}
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee", "root", "123456789");
			stat = con.createStatement();
			
		

			
				if (departmentID.equals(11)) {
					// login person is user means this block of code will execute
					res = stat.executeQuery("select * from employee_table");

					JSONObject jObj = new JSONObject();

					while (res.next()) {
						JSONObject employee = new JSONObject();

						employee.put("employeeID", res.getInt("employeeID"));
						employee.put("employeeName", res.getString("employeeName"));
						employee.put("employeePassword", res.getString("employeePassword"));
						employee.put("departmentID", res.getInt("departmentID"));

					}
					res = stat.executeQuery("select * from department");
					JSONObject jObj1 = new JSONObject();
					while (res.next()) {
						JSONObject department = new JSONObject();

						department.put("departmentID", res.getInt("departmentID"));
						department.put("departmentName", res.getString("departmentName"));

					}

				} else {
					PreparedStatement st = con.prepareStatement("SELECT * FROM employee  where employeeName=?");
					st.setString(1, employeeName);
					res = st.executeQuery();
					while (res.next()) {
						JSONObject employee = new JSONObject();

						employee.put("employeeID", res.getInt("employeeID"));
						employee.put("employeeName", res.getString("employeeName"));
						employee.put("employeePassword", res.getString("employeePassword"));
						employee.put("departmentID", res.getInt("departmentID"));
						PrintWriter prt = response.getWriter();
						
						prt.print(employee);

					}
					res = stat.executeQuery("select * from department");
					JSONObject jObj1 = new JSONObject();
					while (res.next()) {
						JSONObject department = new JSONObject();

						department.put("departmentID", res.getInt("departmentID"));
						department.put("departmentName", res.getString("departmentName"));
					}
					con.close();
				}
			
		}

	catch(

	Exception e)
	{
		e.printStackTrace();
	}
	}
}
