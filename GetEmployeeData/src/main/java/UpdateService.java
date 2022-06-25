
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpServlet;



@WebServlet("/UpdateService")
public class UpdateService extends HttpServlet {



	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String departmentID = request.getParameter("departmentID");
		
		Connection con = null;
		Statement stat = null;
		ResultSet res = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee", "root", "123456789");
			stat = con.createStatement();
			String loginQuery = "SELECT * FROM employee_table WHERE departmentID = '" + departmentID + "';";
			res = stat.executeQuery(loginQuery);
			if (res != null) {
				System.out.println("user logged in successfully");
				System.out.println("enter your new password");
				String employeePass = request.getParameter("employeePassword");
				int employeeID = request.getIntHeader(departmentID);
				String updateQuery = "UPDATE employee_table SET employeePassword='" + employeePass + "', employeeID='"
						+ employeeID + "';";
				res = stat.executeQuery(updateQuery);

				System.out.println("employee");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
