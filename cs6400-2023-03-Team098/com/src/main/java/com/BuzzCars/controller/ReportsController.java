package com.BuzzCars.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("report")
public class ReportsController {

	public ReportsController() {
		// TODO Auto-generated constructor stub
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("pricePerCondition")
	@Consumes("application/json")
	public Response pricePerCondition() {

		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "select distinct name from ChassisType;";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);
			List<String> chassisTypes = new ArrayList();
			while (rs.next())
				chassisTypes.add(rs.getString("name"));

			query = "select distinct vehicle_condition from sold;";
			statement = con.createStatement();
			rs = statement.executeQuery(query);
			List<String> conditions = new ArrayList();
			while (rs.next())
				conditions.add(rs.getString("vehicle_condition"));

			query = "select TRUNCATE(sum(purchase_price) / (select count(*) from sold natural join vehicle  where sold.vehicle_condition = 'CONDITION_INPUT' and chassis_type = 'CHASSIS_TYPE_INPUT'), 2) "
					+ "from sold natural join vehicle where sold.vehicle_condition = 'CONDITION_INPUT' and chassis_type = 'CHASSIS_TYPE_INPUT';";

			Map<String, Map<String, Float>> output = new HashMap();
			for (String chass : chassisTypes) {
				Map<String, Float> m = new HashMap();
				for (String cond : conditions) {
					String newQuery = query.replace("CONDITION_INPUT", cond);
					newQuery = newQuery.replace("CHASSIS_TYPE_INPUT", chass);
					System.out.println(newQuery);
					statement = con.createStatement();
					rs = statement.executeQuery(newQuery);
					rs.next();
					String condKey = cond.replace(" ", "");
					condKey = condKey.toLowerCase();
					m.put(condKey, rs.getFloat(1));
				}

				output.put(chass, m);
			}

			con.close();
			return Response.status(200).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").entity(output)
					.build();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("monthlySalesReport")
	@Consumes("application/json")
	public Response monthlySalesReport() {

		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "SELECT year, month, count(vin) as vehicles_sold, "
				+ "TRUNCATE(sum(salesIncome), 2) as totalSaleIncome, TRUNCATE(sum(netIncome), 2) as totalNetIncome "
				+ "FROM (SELECT v.vin, bought.sell_date, v.model_name, v.model_year, "
				+ "v.description, v.chassis_type, v.manufacturer, EXTRACT(YEAR FROM bought.sell_date) AS year, "
				+ "EXTRACT(MONTH FROM bought.sell_date) AS month, v.fuel_type, v.mileage, "
				+ "(s.purchase_price * 1.25) + (IFNULL(cost_of_parts, 0) * 1.10) as salesIncome, "
				+ "((s.purchase_price * 1.25) + (IFNULL(cost_of_parts, 0) * 1.10) - s.purchase_price) as netIncome, "
				+ "group_concat(c.color) as car_color FROM "
				+ "((vehicle v NATURAL JOIN color c NATURAL JOIN sold s NATURAL JOIN customer cu left join bought on v.vin = bought.vin) "
				+ "LEFT JOIN (SELECT vin, SUM(total_cost_per_order) * 1.10 AS cost_of_parts FROM "
				+ "(SELECT PartsOrder.vin, SUM(part.cost_per_part * part.part_quantity) AS total_cost_per_order FROM "
				+ "PartsOrder JOIN part ON PartsOrder.purchase_order_number = part.purchase_order_number "
				+ "GROUP BY PartsOrder.purchase_order_number, PartsOrder.vin) AS costs_per_order "
				+ "GROUP BY vin) AS parts ON v.vin = parts.vin) GROUP BY vin) as finalResult where year is not NULL and month is not NULL "
				+ "GROUP BY year, month " + "ORDER BY year DESC, month DESC";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);
			statement = con.createStatement();
			rs = statement.executeQuery(query);
			List<Map<String, String>> output = new ArrayList();
			while (rs.next()) {
				Map<String, String> row = new HashMap();
				row.put("year", rs.getString("year"));
				row.put("month", rs.getString("month"));
				row.put("vehicles_sold", rs.getString("vehicles_sold"));
				row.put("totalSaleIncome", rs.getString("totalSaleIncome"));
				row.put("totalNetIncome", rs.getString("totalNetIncome"));
				output.add(row);
			}

			con.close();
			return Response.status(200).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").entity(output)
					.build();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("monthlySalesReportDrillDown")
	@Consumes("application/json")
	public Response monthlySalesReportDrillDown(Map<String, String> input) {
		System.out.println(input);
		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "SELECT u.first_name, u.last_name, COUNT(b.vin) as total_cars_sold, "
				+ "TRUNCATE(SUM(((1.25 * s.purchase_price) + (1.1 * IFNULL(cost.total_part_cost,0)))), 2) as total_sales "
				+ "FROM Bought as b " + "LEFT JOIN Sold as s ON b.vin = s.vin " + "LEFT JOIN ("
				+ "    SELECT  SUM(cost_per_part * part_quantity) as total_part_cost, po.vin "
				+ "    FROM PartsOrder as po "
				+ "    LEFT JOIN Part as p ON po.purchase_order_number = p.purchase_order_number "
				+ "    LEFT JOIN Sold as s1 ON po.vin = s1.vin " + "    GROUP BY po.vin"
				+ ") as cost ON b.vin = cost.vin " + "LEFT JOIN User as u ON b.salespeople = u.username "
				+ "WHERE EXTRACT(YEAR FROM b.sell_date) = 'YEAR_INPUT' "
				+ "AND EXTRACT(MONTH FROM b.sell_date) = 'MONTH_INPUT' " + "GROUP BY b.salespeople "
				+ "ORDER BY total_cars_sold DESC, total_sales DESC";
		query = query.replace("YEAR_INPUT", input.get("year"));
		query = query.replace("MONTH_INPUT", input.get("month"));

		System.out.println(query);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);
			statement = con.createStatement();
			rs = statement.executeQuery(query);

			List<Map<String, String>> output = new ArrayList();
			while (rs.next()) {
				Map<String, String> row = new HashMap();
				row.put("firstName", rs.getString("first_name"));
				row.put("lastName", rs.getString("last_name"));
				row.put("vehiclesSold", rs.getString("total_cars_sold"));
				row.put("totalSales", rs.getString("total_sales"));
				output.add(row);
			}

			con.close();
			return Response.status(200).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").entity(output)
					.build();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("daysInInventory")
	@Consumes("application/json")
	public Response daysInInventory() {

		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "SELECT name, IFNULL(TRUNCATE(AVG(DATEDIFF(Bought.sell_date, Sold.purchase_date) + 1), 0), 'N/A') "
				+ "FROM ChassisType LEFT JOIN Vehicle ON Vehicle.chassis_type = ChassisType.name LEFT JOIN Sold ON Vehicle.vin = Sold.vin "
				+ "LEFT JOIN Bought ON Sold.vin = Bought.vin " + "GROUP BY ChassisType.name "
				+ "ORDER BY ChassisType.name;";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);
			statement = con.createStatement();
			rs = statement.executeQuery(query);
			Map<String, String> output = new HashMap();
			while (rs.next())
				output.put(rs.getString(1), rs.getString(2));

			con.close();
			return Response.status(200).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").entity(output)
					.build();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("partsStatistics")
	@Consumes("application/json")
	public Response partsStatistics() {

		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "Select vendor_name, sum(part_quantity) as quantity, TRUNCATE(sum(part_quantity * cost_per_part), 2) as cost from part group by vendor_name;";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);
			statement = con.createStatement();
			rs = statement.executeQuery(query);
			Map<String, Map<String, String>> output = new HashMap();
			while (rs.next()) {
				Map<String, String> row = new HashMap();
				row.put("cost", rs.getString("cost"));
				row.put("quantity", rs.getString("quantity"));
				output.put(rs.getString("vendor_name"), row);
			}
			con.close();
			return Response.status(200).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").entity(output)
					.build();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("sellerHistory")
	@Consumes("application/json")
	public Response sellerHistory() {

		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "SELECT IFNULL(business.business_name, CONCAT(individual.first_name, ' ', individual.last_name)) AS name, "
				+ "COUNT(sold.vin) AS total_sold, TRUNCATE(AVG(sold.purchase_price), 2) AS purchase_price, "
				+ "IFNULL(AVG(q), 0) AS parts_ordered, IFNULL(TRUNCATE(AVG(c), 2), 0) AS cost_of_parts " + "FROM sold "
				+ "LEFT JOIN (SELECT vin, SUM(quantity) AS q, SUM(cost) AS c FROM partsOrder NATURAL JOIN "
				+ "(SELECT purchase_order_number, SUM(part_quantity) AS quantity, "
				+ "SUM(cost_per_part * part_quantity) AS cost FROM part GROUP BY purchase_order_number) AS partsCost "
				+ "GROUP BY vin) AS cost_of_parts ON sold.vin = cost_of_parts.vin "
				+ "LEFT JOIN business ON sold.business_tax_id = business.business_tax_id "
				+ "AND sold.drivers_license_number = business.drivers_license_number "
				+ "LEFT JOIN individual ON sold.business_tax_id = individual.business_tax_id "
				+ "AND sold.drivers_license_number = individual.drivers_license_number "
				+ "GROUP BY sold.business_tax_id, sold.drivers_license_number "
				+ "ORDER BY total_sold DESC, cost_of_parts ASC;";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);
			statement = con.createStatement();
			rs = statement.executeQuery(query);
			List<Map<String, String>> output = new ArrayList();
			while (rs.next()) {
				Map<String, String> row = new HashMap();
				row.put("name", rs.getString("name"));
				row.put("totalSold", rs.getString("total_sold"));
				row.put("purchasePrice", rs.getString("purchase_price"));
				row.put("partsOrdered", rs.getString("parts_Ordered"));
				row.put("costOfParts", rs.getString("cost_Of_Parts"));
				output.add(row);
			}
			con.close();
			return Response.status(200).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").entity(output)
					.build();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

}
