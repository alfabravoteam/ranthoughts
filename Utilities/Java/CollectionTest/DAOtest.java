import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Locale;

import oracle.jdbc.driver.OracleCallableStatement;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

/**
 * This class intends to supply an array as input on an Oracle procedure.
 * @author alfabravo
 *
 */
public class Prueba {

	public static void main(String[] args) {

		Locale.setDefault(new Locale("es", "CO"));
		System.setProperty("file.encoding", "ISO-8859-15");

		String procedimiento = "call PAQPRUEBA.insert_object(?)";

		Connection cn = null;

		try {
			// connection = DaoConfig.getSqlMapClient().getDataSource().getConnection();
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			cn = DriverManager
					.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:test",
							"user", "password");

			Department d1 = new Department();
			d1.setDNO(1);
			d1.setName("Accounts");
			d1.setLocation("LHR");
			Department d2 = new Department();
			d2.setDNO(2);
			d2.setName("HR");
			d2.setLocation("ISB");
			Department[] deptArray = { d1, d2 };

			OracleCallableStatement callStatement = null;

			ArrayDescriptor arrayDept = ArrayDescriptor.createDescriptor(
					"DEPT_ARRAY", cn);
			ARRAY deptArrayObject = new ARRAY(arrayDept, cn, deptArray); // I get an SQLException here
			callStatement = (OracleCallableStatement) cn
					.prepareCall(procedimiento);
			((OracleCallableStatement) callStatement).setArray(1,
					deptArrayObject);
			callStatement.executeUpdate();
			cn.commit();
			System.out.println("ok");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}

class Department implements SQLData {
	private double DNO;
	private String Name;
	private String Location;

	public void setDNO(double DNO) {
		this.DNO = DNO;
	}

	public double getDNO() {
		return DNO;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public String getName() {
		return Name;
	}

	public void setLocation(String Creation_Date) {
		this.Location = Creation_Date;
	}

	public String getLocation() {
		return Location;
	}

	public void readSQL(SQLInput stream, String typeName) throws SQLException {

	}

	public void writeSQL(SQLOutput stream) throws SQLException {
		stream.writeDouble(this.DNO);
		stream.writeString(this.Name);
		stream.writeString(this.Location);
	}

	public String getSQLTypeName() throws SQLException {
		return "ITO_DEPARTMENT_TYPE";
	}
}
