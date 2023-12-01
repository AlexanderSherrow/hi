package com.BuzzCars.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.BuzzCars.entity.Vehicle;

@Path("vehicle")
public class VehicleController {

	public VehicleController() {
		// TODO Auto-generated constructor stub
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("publicVehicleSearch")
	@Consumes("application/json")
	public Response publicVehicleSearch(Map<String, String> map) {
		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "select results.vin, results.model_name, results.model_year, IFNULL(results.description, 'N/A') as description, results.chassis_type, results.manufacturer, results.fuel_type, results.mileage, TRUNCATE(results.SalesPrice, 2) as SalesPrice, results.purchase_date, results.car_color from "
				+ "(select v.vin, v.model_name, v.model_year, v.description, v.chassis_type, v.manufacturer, v.fuel_type, v.mileage,"
				+ " (IFNULL(cost_of_parts, 0) + s.purchase_price * 1.25) as SalesPrice, s.purchase_date, group_concat(c.color) as car_color "
				+ "from " + "((vehicle v natural join color c natural join sold s natural join customer cu) left join "
				+ "(SELECT vin, SUM(total_cost_per_order) * 1.10 AS cost_of_parts " + "FROM "
				+ "(SELECT PartsOrder.vin, SUM(part.cost_per_part * part.part_quantity) AS total_cost_per_order FROM PartsOrder JOIN part ON PartsOrder.purchase_order_number = part.purchase_order_number "
				+ "GROUP BY PartsOrder.purchase_order_number, PartsOrder.vin) AS costs_per_order "
				+ "GROUP BY vin) as parts on v.vin = parts.vin) " + "where v.vin not in (select vin from bought) and "
				+ "((v.chassis_type like 'CHASSIS_INPUT' and v.manufacturer like 'MANUFACTURER_INPUT' and v.model_year like 'YEAR_INPUT' and v.fuel_type like 'GAS_INPUT') or "
				+ "(v.description like 'KEYWORD' or v.model_year like 'KEYWORD' or v.model_name like 'KEYWORD' or v.manufacturer like 'KEYWORD')) "
				+ "and v.vin not in (select vin from PartsOrder natural join (select * from part where part.current_status = 'received' or part.current_status = 'ordered') as unistalled) "
				+ "GROUP BY vin " + "ORDER BY vin asc) as results where results.car_color like '%COLOR_INPUT%';";

		String manufacturerInput = map.get("manufacturer");
		String fuelInput = map.get("fuelType");
		String keywordInput = map.get("keyword");
		String chassisInput = map.get("chassisType");
		String colorInput = map.get("color");

		if (keywordInput.length() == 0) {

			if (manufacturerInput.length() != 0)
				query = query.replace("MANUFACTURER_INPUT", manufacturerInput);
			else
				query = query.replace("MANUFACTURER_INPUT", "%");

			if (map.get("modelYear").length() != 0)
				query = query.replace("'YEAR_INPUT'", map.get("modelYear"));
			else
				query = query.replace("YEAR_INPUT", "%");

			if (fuelInput.length() != 0)
				query = query.replace("GAS_INPUT", fuelInput);
			else
				query = query.replace("GAS_INPUT", "%");

			if (chassisInput.length() != 0)
				query = query.replace("CHASSIS_INPUT", chassisInput);
			else
				query = query.replace("CHASSIS_INPUT", "%");

		}
		if (colorInput.length() != 0)
			query = query.replace("COLOR_INPUT", colorInput);
		else
			query = query.replace("%COLOR_INPUT%", "%");
		if (keywordInput.length() != 0)
			query = query.replace("KEYWORD", keywordInput);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement = con.createStatement();
			System.out.println(query);
			ResultSet rs = statement.executeQuery(query);
			List<Vehicle> output = new ArrayList();
			while (rs.next()) {
				Vehicle vehicle = new Vehicle();
				vehicle.setVin(rs.getString("vin"));
				vehicle.setChassisType(rs.getString("chassis_type"));
				vehicle.setDescription(rs.getString("description"));
				vehicle.setFuelType(rs.getString("fuel_type"));
				vehicle.setManufacturer(rs.getString("manufacturer"));
				vehicle.setMileage(rs.getInt("mileage"));
				vehicle.setModelName(rs.getString("model_name"));
				vehicle.setModelYear(rs.getInt("model_year"));
				vehicle.setSalesPrice(rs.getFloat("SalesPrice"));
				vehicle.setColor(rs.getString("car_color"));
				output.add(vehicle);
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
	@Path("salesVehicleSearch")
	@Consumes("application/json")
	public Response salesVehicleSearch(Map<String, String> map) {
		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "select results.vin, results.model_name, results.model_year, IFNULL(results.description, 'N/A') as description, results.chassis_type, results.manufacturer, results.fuel_type, results.mileage, TRUNCATE(results.SalesPrice, 2) as SalesPrice, results.purchase_date, results.car_color from "
				+ "(select v.vin, v.model_name, v.model_year, v.description, v.chassis_type, v.manufacturer, v.fuel_type, v.mileage,"
				+ " (IFNULL(cost_of_parts, 0) + s.purchase_price * 1.25) as SalesPrice, s.purchase_date, group_concat(c.color) as car_color "
				+ "from " + "((vehicle v natural join color c natural join sold s natural join customer cu) left join "
				+ "(SELECT vin, SUM(total_cost_per_order) * 1.10 AS cost_of_parts " + "FROM "
				+ "(SELECT PartsOrder.vin, SUM(part.cost_per_part * part.part_quantity) AS total_cost_per_order FROM PartsOrder JOIN part ON PartsOrder.purchase_order_number = part.purchase_order_number "
				+ "GROUP BY PartsOrder.purchase_order_number, PartsOrder.vin) AS costs_per_order "
				+ "GROUP BY vin) as parts on v.vin = parts.vin) " + "where v.vin not in (select vin from bought) and "
				+ "((v.vin like 'VIN_INPUT' and v.chassis_type like 'CHASSIS_INPUT' and v.manufacturer like 'MANUFACTURER_INPUT' and v.model_year like 'YEAR_INPUT' and v.fuel_type like 'GAS_INPUT') or "
				+ "(v.description like 'KEYWORD' or v.model_year like 'KEYWORD' or v.model_name like 'KEYWORD' or v.manufacturer like 'KEYWORD')) "
				+ "and v.vin not in (select vin from PartsOrder natural join (select * from part where part.current_status = 'received' or part.current_status = 'ordered') as unistalled) "
				+ "GROUP BY vin " + "ORDER BY vin asc) as results where results.car_color like '%COLOR_INPUT%';";

		String vinInput = map.get("vin");
		String manufacturerInput = map.get("manufacturer");
		String fuelInput = map.get("fuelType");
		String keywordInput = map.get("keyword");
		String chassisInput = map.get("chassisType");
		String colorInput = map.get("color");

		if (keywordInput.length() == 0) {

			if (vinInput.length() != 0)
				query = query.replace("VIN_INPUT", vinInput);
			else
				query = query.replace("VIN_INPUT", "%");

			if (manufacturerInput.length() != 0)
				query = query.replace("MANUFACTURER_INPUT", manufacturerInput);
			else
				query = query.replace("MANUFACTURER_INPUT", "%");

			if (map.get("modelYear").length() != 0)
				query = query.replace("'YEAR_INPUT'", map.get("modelYear"));
			else
				query = query.replace("YEAR_INPUT", "%");

			if (fuelInput.length() != 0)
				query = query.replace("GAS_INPUT", fuelInput);
			else
				query = query.replace("GAS_INPUT", "%");

			if (chassisInput.length() != 0)
				query = query.replace("CHASSIS_INPUT", chassisInput);
			else
				query = query.replace("CHASSIS_INPUT", "%");

		}
		if (colorInput.length() != 0)
			query = query.replace("COLOR_INPUT", colorInput);
		else
			query = query.replace("%COLOR_INPUT%", "%");
		if (keywordInput.length() != 0)
			query = query.replace("KEYWORD", keywordInput);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement = con.createStatement();
			System.out.println(query);
			ResultSet rs = statement.executeQuery(query);
			List<Vehicle> output = new ArrayList();
			while (rs.next()) {
				Vehicle vehicle = new Vehicle();
				vehicle.setVin(rs.getString("vin"));
				vehicle.setChassisType(rs.getString("chassis_type"));
				vehicle.setDescription(rs.getString("description"));
				vehicle.setFuelType(rs.getString("fuel_type"));
				vehicle.setManufacturer(rs.getString("manufacturer"));
				vehicle.setMileage(rs.getInt("mileage"));
				vehicle.setModelName(rs.getString("model_name"));
				vehicle.setModelYear(rs.getInt("model_year"));
				vehicle.setSalesPrice(rs.getFloat("SalesPrice"));
				vehicle.setColor(rs.getString("car_color"));
				output.add(vehicle);
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
	@Path("soldVehicleSearch")
	@Consumes("application/json")
	public Response soldVehicleSearch(Map<String, String> map) {
		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "select results.vin, results.model_name, results.model_year, IFNULL(results.description, 'N/A') as description, results.chassis_type, results.manufacturer, results.fuel_type, results.mileage, TRUNCATE(results.SalesPrice, 2) as SalesPrice, results.purchase_date, results.car_color from "
				+ "(select v.vin, v.model_name, v.model_year, v.description, v.chassis_type, v.manufacturer, v.fuel_type, v.mileage,"
				+ " (IFNULL(cost_of_parts, 0) + s.purchase_price * 1.25) as SalesPrice, s.purchase_date, group_concat(c.color) as car_color "
				+ "from " + "((vehicle v natural join color c natural join sold s natural join customer cu) left join "
				+ "(SELECT vin, SUM(total_cost_per_order) * 1.10 AS cost_of_parts " + "FROM "
				+ "(SELECT PartsOrder.vin, SUM(part.cost_per_part * part.part_quantity) AS total_cost_per_order FROM PartsOrder JOIN part ON PartsOrder.purchase_order_number = part.purchase_order_number "
				+ "GROUP BY PartsOrder.purchase_order_number, PartsOrder.vin) AS costs_per_order "
				+ "GROUP BY vin) as parts on v.vin = parts.vin) " + "where v.vin in (select vin from bought) and "
				+ "((v.vin like 'VIN_INPUT' and v.chassis_type like 'CHASSIS_INPUT' and v.manufacturer like 'MANUFACTURER_INPUT' and v.model_year like 'YEAR_INPUT' and v.fuel_type like 'GAS_INPUT') or "
				+ "(v.description like 'KEYWORD' or v.model_year like 'KEYWORD' or v.model_name like 'KEYWORD' or v.manufacturer like 'KEYWORD')) "
				+ "and v.vin not in (select vin from PartsOrder natural join (select * from part where part.current_status = 'received' or part.current_status = 'ordered') as unistalled) "
				+ "GROUP BY vin " + "ORDER BY vin asc) as results where results.car_color like '%COLOR_INPUT%';";

		String vinInput = map.get("vin");
		String manufacturerInput = map.get("manufacturer");
		String fuelInput = map.get("fuelType");
		String keywordInput = map.get("keyword");
		String chassisInput = map.get("chassisType");
		String colorInput = map.get("color");

		if (keywordInput.length() == 0) {

			if (vinInput.length() != 0)
				query = query.replace("VIN_INPUT", vinInput);
			else
				query = query.replace("VIN_INPUT", "%");

			if (manufacturerInput.length() != 0)
				query = query.replace("MANUFACTURER_INPUT", manufacturerInput);
			else
				query = query.replace("MANUFACTURER_INPUT", "%");

			if (map.get("modelYear").length() != 0)
				query = query.replace("'YEAR_INPUT'", map.get("modelYear"));
			else
				query = query.replace("YEAR_INPUT", "%");

			if (fuelInput.length() != 0)
				query = query.replace("GAS_INPUT", fuelInput);
			else
				query = query.replace("GAS_INPUT", "%");

			if (chassisInput.length() != 0)
				query = query.replace("CHASSIS_INPUT", chassisInput);
			else
				query = query.replace("CHASSIS_INPUT", "%");

		}
		if (colorInput.length() != 0)
			query = query.replace("COLOR_INPUT", colorInput);
		else
			query = query.replace("%COLOR_INPUT%", "%");
		if (keywordInput.length() != 0)
			query = query.replace("KEYWORD", keywordInput);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement = con.createStatement();
			System.out.println(query);
			ResultSet rs = statement.executeQuery(query);
			List<Vehicle> output = new ArrayList();
			while (rs.next()) {
				Vehicle vehicle = new Vehicle();
				vehicle.setVin(rs.getString("vin"));
				vehicle.setChassisType(rs.getString("chassis_type"));
				vehicle.setDescription(rs.getString("description"));
				vehicle.setFuelType(rs.getString("fuel_type"));
				vehicle.setManufacturer(rs.getString("manufacturer"));
				vehicle.setMileage(rs.getInt("mileage"));
				vehicle.setModelName(rs.getString("model_name"));
				vehicle.setModelYear(rs.getInt("model_year"));
				vehicle.setSalesPrice(rs.getFloat("SalesPrice"));
				vehicle.setColor(rs.getString("car_color"));
				output.add(vehicle);
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
	@Path("unsoldVehicleSearch")
	@Consumes("application/json")
	public Response unsoldVehicleSearch(Map<String, String> map) {
		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "select results.vin, results.model_name, results.model_year, IFNULL(results.description, 'N/A') as description, results.chassis_type, results.manufacturer, results.fuel_type, results.mileage, TRUNCATE(results.SalesPrice, 2) as SalesPrice, results.purchase_date, results.car_color from "
				+ "(select v.vin, v.model_name, v.model_year, v.description, v.chassis_type, v.manufacturer, v.fuel_type, v.mileage,"
				+ " (IFNULL(cost_of_parts, 0) + s.purchase_price * 1.25) as SalesPrice, s.purchase_date, group_concat(c.color) as car_color "
				+ "from " + "((vehicle v natural join color c natural join sold s natural join customer cu) left join "
				+ "(SELECT vin, SUM(total_cost_per_order) * 1.10 AS cost_of_parts " + "FROM "
				+ "(SELECT PartsOrder.vin, SUM(part.cost_per_part * part.part_quantity) AS total_cost_per_order FROM PartsOrder JOIN part ON PartsOrder.purchase_order_number = part.purchase_order_number "
				+ "GROUP BY PartsOrder.purchase_order_number, PartsOrder.vin) AS costs_per_order "
				+ "GROUP BY vin) as parts on v.vin = parts.vin) " + "where v.vin not in (select vin from bought) and "
				+ "((v.vin like 'VIN_INPUT' and v.chassis_type like 'CHASSIS_INPUT' and v.manufacturer like 'MANUFACTURER_INPUT' and v.model_year like 'YEAR_INPUT' and v.fuel_type like 'GAS_INPUT') or "
				+ "(v.description like 'KEYWORD' or v.model_year like 'KEYWORD' or v.model_name like 'KEYWORD' or v.manufacturer like 'KEYWORD')) "
				+ "GROUP BY vin " + "ORDER BY vin asc) as results where results.car_color like '%COLOR_INPUT%';";

		String vinInput = map.get("vin");
		String manufacturerInput = map.get("manufacturer");
		String fuelInput = map.get("fuelType");
		String keywordInput = map.get("keyword");
		String chassisInput = map.get("chassisType");
		String colorInput = map.get("color");

		if (keywordInput.length() == 0) {

			if (vinInput.length() != 0)
				query = query.replace("VIN_INPUT", vinInput);
			else
				query = query.replace("VIN_INPUT", "%");

			if (manufacturerInput.length() != 0)
				query = query.replace("MANUFACTURER_INPUT", manufacturerInput);
			else
				query = query.replace("MANUFACTURER_INPUT", "%");

			if (map.get("modelYear").length() != 0)
				query = query.replace("'YEAR_INPUT'", map.get("modelYear"));
			else
				query = query.replace("YEAR_INPUT", "%");

			if (fuelInput.length() != 0)
				query = query.replace("GAS_INPUT", fuelInput);
			else
				query = query.replace("GAS_INPUT", "%");

			if (chassisInput.length() != 0)
				query = query.replace("CHASSIS_INPUT", chassisInput);
			else
				query = query.replace("CHASSIS_INPUT", "%");

		}
		if (colorInput.length() != 0)
			query = query.replace("COLOR_INPUT", colorInput);
		else
			query = query.replace("%COLOR_INPUT%", "%");
		if (keywordInput.length() != 0)
			query = query.replace("KEYWORD", keywordInput);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement = con.createStatement();
			System.out.println(query);
			ResultSet rs = statement.executeQuery(query);
			List<Vehicle> output = new ArrayList();
			while (rs.next()) {
				Vehicle vehicle = new Vehicle();
				vehicle.setVin(rs.getString("vin"));
				vehicle.setChassisType(rs.getString("chassis_type"));
				vehicle.setDescription(rs.getString("description"));
				vehicle.setFuelType(rs.getString("fuel_type"));
				vehicle.setManufacturer(rs.getString("manufacturer"));
				vehicle.setMileage(rs.getInt("mileage"));
				vehicle.setModelName(rs.getString("model_name"));
				vehicle.setModelYear(rs.getInt("model_year"));
				vehicle.setSalesPrice(rs.getFloat("SalesPrice"));
				vehicle.setColor(rs.getString("car_color"));
				output.add(vehicle);
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
	@Path("inventoryVehicleSearch")
	@Consumes("application/json")
	public Response InventoryVehicleSearch(Map<String, String> map) {
		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";

		String query = ""
				+ "select results.vin, results.model_name, results.model_year, IFNULL(results.description, 'N/A') as description, results.chassis_type, results.manufacturer, results.fuel_type, results.mileage, TRUNCATE(results.SalesPrice, 2) as SalesPrice, results.purchase_date, results.car_color from "
				+ "(select v.vin, v.model_name, v.model_year, v.description, v.chassis_type, v.manufacturer, v.fuel_type, v.mileage,"
				+ " (IFNULL(cost_of_parts, 0) + s.purchase_price * 1.25) as SalesPrice, s.purchase_date, group_concat(c.color) as car_color "
				+ "from " + "((vehicle v natural join color c natural join sold s natural join customer cu) left join "
				+ "(SELECT vin, SUM(total_cost_per_order) * 1.10 AS cost_of_parts " + "FROM "
				+ "(SELECT PartsOrder.vin, SUM(part.cost_per_part * part.part_quantity) AS total_cost_per_order FROM PartsOrder JOIN part ON PartsOrder.purchase_order_number = part.purchase_order_number "
				+ "GROUP BY PartsOrder.purchase_order_number, PartsOrder.vin) AS costs_per_order "
				+ "GROUP BY vin) as parts on v.vin = parts.vin) "
				+ "where v.vin not in (select vin from bought) and ((v.vin like 'VIN_INPUT' and v.chassis_type like 'CHASSIS_INPUT' and v.manufacturer like 'MANUFACTURER_INPUT' and v.model_year like 'YEAR_INPUT' and v.fuel_type like 'GAS_INPUT') "
				+ "or (v.description like 'KEYWORD' or v.model_year like 'KEYWORD' or v.model_name like 'KEYWORD' or v.manufacturer like 'KEYWORD')) GROUP BY vin "
				+ "ORDER BY vin asc) as results where results.car_color like '%COLOR_INPUT%';";
		String vinInput = map.get("vin");
		System.out.println(vinInput);
		String manufacturerInput = map.get("manufacturer");
		int yearInput = 0;
		if (map.get("modelYear").length() != 0)
			yearInput = Integer.parseInt(map.get("modelYear"));
		String fuelInput = map.get("fuelType");
		String keywordInput = map.get("keyword");
		String chassisInput = map.get("chassisType");
		String colorInput = map.get("color");

		if (keywordInput.length() == 0) {
			if (vinInput.length() != 0)
				query = query.replace("VIN_INPUT", vinInput);
			else
				query = query.replace("VIN_INPUT", "%");

			if (manufacturerInput.length() != 0)
				query = query.replace("MANUFACTURER_INPUT", manufacturerInput);
			else
				query = query.replace("MANUFACTURER_INPUT", "%");

			if (map.get("modelYear").length() != 0)
				query = query.replace("'YEAR_INPUT'", map.get("modelYear"));
			else
				query = query.replace("YEAR_INPUT", "%");

			if (fuelInput.length() != 0)
				query = query.replace("GAS_INPUT", fuelInput);
			else
				query = query.replace("GAS_INPUT", "%");

			if (chassisInput.length() != 0)
				query = query.replace("CHASSIS_INPUT", chassisInput);
			else
				query = query.replace("CHASSIS_INPUT", "%");

		}
		if (colorInput.length() != 0)
			query = query.replace("COLOR_INPUT", colorInput);
		else
			query = query.replace("%COLOR_INPUT%", "%");

		if (keywordInput.length() != 0)
			query = query.replace("KEYWORD", keywordInput);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement = con.createStatement();
			System.out.println(query);
			ResultSet rs = statement.executeQuery(query);
			List<Vehicle> output = new ArrayList<Vehicle>();
			while (rs.next()) {
				Vehicle vehicle = new Vehicle();
				vehicle.setVin(rs.getString("vin"));
				vehicle.setChassisType(rs.getString("chassis_type"));
				vehicle.setDescription(rs.getString("description"));
				vehicle.setFuelType(rs.getString("fuel_type"));
				vehicle.setManufacturer(rs.getString("manufacturer"));
				vehicle.setMileage(rs.getInt("mileage"));
				vehicle.setModelName(rs.getString("model_name"));
				vehicle.setModelYear(rs.getInt("model_year"));
				vehicle.setSalesPrice(rs.getFloat("SalesPrice"));
				vehicle.setColor(rs.getString("car_color"));
				output.add(vehicle);
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
	@Path("allVehicles")
	@Consumes("application/json")
	public Response allVehicles(Map<String, String> map) {
		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";

		String query = ""
				+ "select results.vin, results.model_name, results.model_year, IFNULL(results.description, 'N/A') as description, results.chassis_type, results.manufacturer, results.fuel_type, results.mileage, TRUNCATE(results.SalesPrice, 2) as SalesPrice, results.purchase_date, results.car_color from "
				+ "(select v.vin, v.model_name, v.model_year, v.description, v.chassis_type, v.manufacturer, v.fuel_type, v.mileage,"
				+ " (IFNULL(cost_of_parts, 0) + s.purchase_price * 1.25) as SalesPrice, s.purchase_date, group_concat(c.color) as car_color "
				+ "from " + "((vehicle v natural join color c natural join sold s natural join customer cu) left join "
				+ "(SELECT vin, SUM(total_cost_per_order) * 1.10 AS cost_of_parts " + "FROM "
				+ "(SELECT PartsOrder.vin, SUM(part.cost_per_part * part.part_quantity) AS total_cost_per_order FROM PartsOrder JOIN part ON PartsOrder.purchase_order_number = part.purchase_order_number "
				+ "GROUP BY PartsOrder.purchase_order_number, PartsOrder.vin) AS costs_per_order "
				+ "GROUP BY vin) as parts on v.vin = parts.vin) "
				+ "where ((v.vin like 'VIN_INPUT' and v.chassis_type like 'CHASSIS_INPUT' and v.manufacturer like 'MANUFACTURER_INPUT' and v.model_year like 'YEAR_INPUT' and v.fuel_type like 'GAS_INPUT') "
				+ "or (v.description like 'KEYWORD' or v.model_year like 'KEYWORD' or v.model_name like 'KEYWORD' or v.manufacturer like 'KEYWORD')) GROUP BY vin "
				+ "ORDER BY vin asc) as results where results.car_color like '%COLOR_INPUT%';";
		String vinInput = map.get("vin");
		System.out.println(vinInput);
		String manufacturerInput = map.get("manufacturer");
		int yearInput = 0;
		if (map.get("modelYear").length() != 0)
			yearInput = Integer.parseInt(map.get("modelYear"));
		String fuelInput = map.get("fuelType");
		String keywordInput = map.get("keyword");
		String chassisInput = map.get("chassisType");
		String colorInput = map.get("color");

		if (keywordInput.length() == 0) {
			if (vinInput.length() != 0)
				query = query.replace("VIN_INPUT", vinInput);
			else
				query = query.replace("VIN_INPUT", "%");

			if (manufacturerInput.length() != 0)
				query = query.replace("MANUFACTURER_INPUT", manufacturerInput);
			else
				query = query.replace("MANUFACTURER_INPUT", "%");

			if (map.get("modelYear").length() != 0)
				query = query.replace("'YEAR_INPUT'", map.get("modelYear"));
			else
				query = query.replace("YEAR_INPUT", "%");

			if (fuelInput.length() != 0)
				query = query.replace("GAS_INPUT", fuelInput);
			else
				query = query.replace("GAS_INPUT", "%");

			if (chassisInput.length() != 0)
				query = query.replace("CHASSIS_INPUT", chassisInput);
			else
				query = query.replace("CHASSIS_INPUT", "%");

		}
		if (colorInput.length() != 0)
			query = query.replace("COLOR_INPUT", colorInput);
		else
			query = query.replace("%COLOR_INPUT%", "%");

		if (keywordInput.length() != 0)
			query = query.replace("KEYWORD", keywordInput);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement = con.createStatement();
			System.out.println(query);
			ResultSet rs = statement.executeQuery(query);
			List<Vehicle> output = new ArrayList<Vehicle>();
			while (rs.next()) {
				Vehicle vehicle = new Vehicle();
				vehicle.setVin(rs.getString("vin"));
				vehicle.setChassisType(rs.getString("chassis_type"));
				vehicle.setDescription(rs.getString("description"));
				vehicle.setFuelType(rs.getString("fuel_type"));
				vehicle.setManufacturer(rs.getString("manufacturer"));
				vehicle.setMileage(rs.getInt("mileage"));
				vehicle.setModelName(rs.getString("model_name"));
				vehicle.setModelYear(rs.getInt("model_year"));
				vehicle.setSalesPrice(rs.getFloat("SalesPrice"));
				vehicle.setColor(rs.getString("car_color"));
				output.add(vehicle);
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
	@Path("numberOfVehiclesForSale")
	public Response numberOfVehiclesForSale() {
		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "select count(*) as carsForSale from("
				+ "select v.vin, v.model_name, v.model_year, v.description, v.chassis_type, v.manufacturer, v.fuel_type, v.mileage, "
				+ "TRUNCATE((IFNULL(cost_of_parts, 0) + s.purchase_price * 1.25), 2) as SalesPrice, s.purchase_date "
				+ "from " + "((vehicle v natural join color c natural join sold s natural join customer cu) left join "
				+ "(SELECT " + "vin, SUM(total_cost_per_order) * 1.10 AS cost_of_parts " + "FROM ( " + "SELECT "
				+ "PartsOrder.vin, " + "SUM(part.cost_per_part * part.part_quantity) AS total_cost_per_order " + "FROM "
				+ "PartsOrder " + "JOIN " + "part ON PartsOrder.purchase_order_number = part.purchase_order_number "
				+ "GROUP BY " + "PartsOrder.purchase_order_number, PartsOrder.vin " + ") AS costs_per_order "
				+ "GROUP BY " + "vin) as parts on v.vin = parts.vin) " + "where "
				+ "v.vin not in (select vin from bought) and "
				+ "v.vin not in (select vin from PartsOrder natural join (select * from part where part.current_status = 'received' or part.current_status = 'ordered') as unistalled) "
				+ "GROUP BY vin) as vehiclesForSale";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement = con.createStatement();
			System.out.println(query);
			ResultSet rs = statement.executeQuery(query);
			rs.next();
			int output = rs.getInt(1);

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
	@Path("numberOfVehiclesNotForSale")
	public Response numberOfVehiclesNotForSale() {
		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "select count(*) as carsForSale from("
				+ "select v.vin, v.model_name, v.model_year, v.description, v.chassis_type, v.manufacturer, v.fuel_type, v.mileage, "
				+ "TRUNCATE((IFNULL(cost_of_parts, 0) + s.purchase_price * 1.25), 2) as SalesPrice, s.purchase_date "
				+ "from " + "((vehicle v natural join color c natural join sold s natural join customer cu) left join "
				+ "(SELECT " + "vin, SUM(total_cost_per_order) * 1.10 AS cost_of_parts " + "FROM ( " + "SELECT "
				+ "PartsOrder.vin, " + "SUM(part.cost_per_part * part.part_quantity) AS total_cost_per_order " + "FROM "
				+ "PartsOrder " + "JOIN " + "part ON PartsOrder.purchase_order_number = part.purchase_order_number "
				+ "GROUP BY " + "PartsOrder.purchase_order_number, PartsOrder.vin " + ") AS costs_per_order "
				+ "GROUP BY " + "vin) as parts on v.vin = parts.vin) " + "where "
				+ "v.vin not in (select vin from bought) and "
				+ "v.vin in (select vin from PartsOrder natural join (select * from part where part.current_status = 'received' or part.current_status = 'ordered') as unistalled) "
				+ "GROUP BY vin) as vehiclesForSale";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement = con.createStatement();
			System.out.println(query);
			ResultSet rs = statement.executeQuery(query);
			rs.next();
			int output = rs.getInt(1);

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
	@Path("save")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Map<String, String> input) throws SQLException {

		System.out.println(input);
		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "insert into Vehicle values ('" + input.get("modelName") + "', '" + input.get("modelYear")
				+ "', '" + input.get("description") + "', '" + input.get("vin") + "', '" + input.get("mileage") + "', '"
				+ input.get("manufacturer") + "', '" + input.get("fuelType") + "', '" + input.get("chassisType")
				+ "');";

		if (input.get("description").equals(""))
			query = "Insert into Vehicle (model_name, model_year, vin, mileage, manufacturer, fuel_type, chassis_type) "
					+ "VALUES " + "( '" + input.get("modelName") + "', '" + input.get("modelYear") + "', '"
					+ input.get("vin") + "', '" + input.get("mileage") + "', '" + input.get("manufacturer") + "', '"
					+ input.get("fuelType") + "', '" + input.get("chassisType") + "' );";

		String colors = input.get("color");
		String[] colorsArr = colors.split(",");
		System.out.println(query);

		String soldQuery = "insert into Sold values ('VIN_INPUT', 'CONDITION_INPUT', PURCHASE_PRICE_INPUT, 'PURCHASE_DATE_INPUT',  'BUSINESS_TAX_ID_INPUT', 'DRIVER_LICENSE_INPUT', 'INVENTORY_CLERK_INPUT');";
		String driversLicense = input.get("driverLicense");
		String businessTaxId = input.get("businessTaxId");
		if (driversLicense.length() == 0)
			driversLicense = "N/A";
		if (businessTaxId.length() == 0)
			businessTaxId = "N/A";
		soldQuery = soldQuery.replace("VIN_INPUT", input.get("vin"));
		soldQuery = soldQuery.replace("CONDITION_INPUT", input.get("condition"));
		soldQuery = soldQuery.replace("PURCHASE_PRICE_INPUT", input.get("purchasePrice"));
		soldQuery = soldQuery.replace("PURCHASE_DATE_INPUT", input.get("purchaseDate"));
		soldQuery = soldQuery.replace("BUSINESS_TAX_ID_INPUT", businessTaxId);
		soldQuery = soldQuery.replace("DRIVER_LICENSE_INPUT", driversLicense);
		soldQuery = soldQuery.replace("INVENTORY_CLERK_INPUT", input.get("inventoryClerk"));

		System.out.println(soldQuery);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection con = DriverManager.getConnection(url, username, password);

		try {
			con.setAutoCommit(false);

			Statement statement = con.createStatement();
			statement.executeUpdate(query);
			for (String color : colorsArr) {
				String colorQuery = "insert into Color values ('VIN_INPUT', 'COLOR_INPUT');";
				colorQuery = colorQuery.replace("VIN_INPUT", input.get("vin"));
				colorQuery = colorQuery.replace("COLOR_INPUT", color);
				System.out.println(colorQuery);
				statement.executeUpdate(colorQuery);
			}

			if (input.containsKey("newCustomer")) {
				String newCustomerQuery = "insert into Customer values ('STATE_INPUT', 'CITY_INPUT', 'STREET_INPUT', 'POSTAL_INPUT',"
						+ " 'PHONE_INPUT', 'EMAIL_INPUT', 'BUSINESS_TAX_ID_INPUT', 'DRIVER_LICENSE_INPUT');";
				newCustomerQuery = newCustomerQuery.replace("STATE_INPUT", input.get("state"));
				newCustomerQuery = newCustomerQuery.replace("CITY_INPUT", input.get("city"));
				newCustomerQuery = newCustomerQuery.replace("STREET_INPUT", input.get("street"));
				newCustomerQuery = newCustomerQuery.replace("POSTAL_INPUT", input.get("postal"));
				newCustomerQuery = newCustomerQuery.replace("PHONE_INPUT", input.get("phone"));
				newCustomerQuery = newCustomerQuery.replace("EMAIL_INPUT", input.get("email"));
				newCustomerQuery = newCustomerQuery.replace("BUSINESS_TAX_ID_INPUT", businessTaxId);
				newCustomerQuery = newCustomerQuery.replace("DRIVER_LICENSE_INPUT", driversLicense);

				System.out.println(newCustomerQuery);
				statement.executeUpdate(newCustomerQuery);

				if (!driversLicense.equals("N/A")) {
					String individualQuery = "insert into Individual values ('DRIVER_LICENSE_INPUT', 'FIRST_NAME_INPUT', 'LAST_NAME_INPUT', 'BUSINESS_TAX_ID_INPUT');";
					individualQuery = individualQuery.replace("FIRST_NAME_INPUT", input.get("firstName"));
					individualQuery = individualQuery.replace("LAST_NAME_INPUT", input.get("lastName"));
					individualQuery = individualQuery.replace("BUSINESS_TAX_ID_INPUT", businessTaxId);
					individualQuery = individualQuery.replace("DRIVER_LICENSE_INPUT", driversLicense);
					System.out.println(individualQuery);
					statement.executeUpdate(individualQuery);
				}

				if (!businessTaxId.equals("N/A")) {
					String businessQuery = "insert into Business values ('BUSINESS_TAX_ID_INPUT', 'BUSINESS_NAME_INPUT', 'CONTACT_TITLE_INPUT', 'FIRST_NAME_INPUT', 'LAST_NAME_INPUT', 'DRIVER_LICENSE_INPUT');";
					businessQuery = businessQuery.replace("FIRST_NAME_INPUT", input.get("businessFirstName"));
					businessQuery = businessQuery.replace("LAST_NAME_INPUT", input.get("businessLastName"));
					businessQuery = businessQuery.replace("BUSINESS_NAME_INPUT", input.get("businessName"));
					businessQuery = businessQuery.replace("CONTACT_TITLE_INPUT", input.get("businessContactTitle"));
					businessQuery = businessQuery.replace("BUSINESS_TAX_ID_INPUT", businessTaxId);
					businessQuery = businessQuery.replace("DRIVER_LICENSE_INPUT", driversLicense);
					System.out.println(businessQuery);
					statement.executeUpdate(businessQuery);
				}
			}

			statement.executeUpdate(soldQuery);
			con.commit();
			String result = "Product created : " + input;
			return Response.status(201).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").entity(result)
					.build();

		} catch (SQLException e) {
			con.rollback();
			e.printStackTrace();
		} finally {
			con.setAutoCommit(true);
			con.close();
		}
		return null;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getDetails")
	@Consumes("application/json")
	public Response getDetails(Map<String, String> input) {
		System.out.println(input);
		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "SELECT v.vin, v.model_name, v.model_year, IFNULL(v.description, 'N/A') as description, v.chassis_type, v.manufacturer, "
				+ "v.fuel_type, v.mileage, TRUNCATE((IFNULL(cost_of_parts, 0) + s.purchase_price * 1.25), 2) as SalesPrice, "
				+ "group_concat(c.color) as car_color " + "FROM "
				+ "((vehicle v NATURAL JOIN color c NATURAL JOIN sold s NATURAL JOIN customer cu) LEFT JOIN "
				+ "(SELECT " + "vin, SUM(total_cost_per_order) * 1.10 AS cost_of_parts " + "FROM ( " + "SELECT "
				+ "PartsOrder.vin, " + "SUM(part.cost_per_part * part.part_quantity) AS total_cost_per_order " + "FROM "
				+ "PartsOrder " + "JOIN " + "part ON PartsOrder.purchase_order_number = part.purchase_order_number "
				+ "GROUP BY " + "PartsOrder.purchase_order_number, PartsOrder.vin " + ") AS costs_per_order "
				+ "GROUP BY " + "vin) AS parts ON v.vin = parts.vin) " + "WHERE v.vin = 'VIN_INPUT' " + "GROUP BY vin "
				+ "ORDER BY vin ASC;";
		query = query.replace("VIN_INPUT", input.get("vin"));
		System.out.println(query);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement = con.createStatement();
			System.out.println(query);
			ResultSet rs = statement.executeQuery(query);
			List<Vehicle> output = new ArrayList();
			rs.next();
			Vehicle vehicle = new Vehicle();
			vehicle.setVin(rs.getString("vin"));
			vehicle.setChassisType(rs.getString("chassis_type"));
			vehicle.setDescription(rs.getString("description"));
			vehicle.setFuelType(rs.getString("fuel_type"));
			vehicle.setManufacturer(rs.getString("manufacturer"));
			vehicle.setMileage(rs.getInt("mileage"));
			vehicle.setModelName(rs.getString("model_name"));
			vehicle.setModelYear(rs.getInt("model_year"));
			vehicle.setSalesPrice(rs.getFloat("SalesPrice"));
			vehicle.setColor(rs.getString("car_color"));
			output.add(vehicle);
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
	@Path("getDetailsInventoryClerk")
	@Consumes("application/json")
	public Response getDetailsInventoryClerk(Map<String, String> input) {
		System.out.println(input);
		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "SELECT v.vin, v.model_name, v.model_year, IFNULL(v.description, 'N/A') as description, v.chassis_type, v.manufacturer, "
				+ "v.fuel_type, v.mileage, TRUNCATE(IFNULL(cost_of_parts, 0), 2) as cost_of_parts, TRUNCATE(s.purchase_price, 2) as purchase_price, "
				+ "group_concat(c.color) as car_color " + "FROM "
				+ "((vehicle v NATURAL JOIN color c NATURAL JOIN sold s NATURAL JOIN customer cu) LEFT JOIN "
				+ "(SELECT " + "vin, SUM(total_cost_per_order) * 1.10 AS cost_of_parts " + "FROM ( " + "SELECT "
				+ "PartsOrder.vin, " + "SUM(part.cost_per_part * part.part_quantity) AS total_cost_per_order " + "FROM "
				+ "PartsOrder " + "JOIN " + "part ON PartsOrder.purchase_order_number = part.purchase_order_number "
				+ "GROUP BY " + "PartsOrder.purchase_order_number, PartsOrder.vin " + ") AS costs_per_order "
				+ "GROUP BY " + "vin) AS parts ON v.vin = parts.vin) " + "WHERE v.vin = 'VIN_INPUT' " + "GROUP BY vin "
				+ "ORDER BY vin ASC;";
		query = query.replace("VIN_INPUT", input.get("vin"));
		System.out.println(query);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement = con.createStatement();
			System.out.println(query);
			ResultSet rs = statement.executeQuery(query);
			List<Vehicle> output = new ArrayList();
			rs.next();
			Vehicle vehicle = new Vehicle();
			vehicle.setVin(rs.getString("vin"));
			vehicle.setChassisType(rs.getString("chassis_type"));
			vehicle.setDescription(rs.getString("description"));
			vehicle.setFuelType(rs.getString("fuel_type"));
			vehicle.setManufacturer(rs.getString("manufacturer"));
			vehicle.setMileage(rs.getInt("mileage"));
			vehicle.setModelName(rs.getString("model_name"));
			vehicle.setModelYear(rs.getInt("model_year"));
			vehicle.setSalesPrice(rs.getFloat("purchase_price"));
			vehicle.setColor(rs.getString("car_color"));
			vehicle.setCostOfParts(rs.getFloat("cost_of_parts"));
			output.add(vehicle);
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
	@Path("notForSale")
	@Consumes("application/json")
	public Response notForSale(Map<String, String> input) {
		System.out.println(input);
		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "SELECT COUNT(*) FROM (" + "SELECT vin FROM ("
				+ "SELECT vin FROM partsOrder NATURAL JOIN part WHERE part.current_status != 'installed' AND vin NOT IN (SELECT vin FROM bought) GROUP BY vin"
				+ ") AS part" + " UNION" + " SELECT vin FROM bought"
				+ ") AS notForSale WHERE notForSale.vin = 'VIN_INPUT';";

		query = query.replace("VIN_INPUT", input.get("vin"));
		System.out.println(query);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement = con.createStatement();
			System.out.println(query);
			ResultSet rs = statement.executeQuery(query);
			rs.next();
			int count = rs.getInt(1);
			Boolean output = count == 1;

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
	@Path("hasBeenSold")
	@Consumes("application/json")
	public Response hasBeenSold(Map<String, String> input) {
		System.out.println(input);
		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "SELECT COUNT(vin) from bought where bought.vin = 'VIN_INPUT';";

		query = query.replace("VIN_INPUT", input.get("vin"));
		System.out.println(query);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement = con.createStatement();
			System.out.println(query);
			ResultSet rs = statement.executeQuery(query);
			rs.next();
			int count = rs.getInt(1);
			Boolean output = count == 1;

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
	@Path("sellVehicle")
	@Consumes("application/json")
	public Response sellVehicle(Map<String, String> input) throws SQLException {
		System.out.println(input);
		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection con = DriverManager.getConnection(url, username, password);

		try {
			con.setAutoCommit(false);

			Statement statement = con.createStatement();
			String driversLicense = input.get("driverLicense");
			String businessTaxId = input.get("businessTaxId");
			if (driversLicense.length() == 0)
				driversLicense = "N/A";
			if (businessTaxId.length() == 0)
				businessTaxId = "N/A";
			if (input.containsKey("newCustomer")) {
				String newCustomerQuery = "insert into Customer values ('STATE_INPUT', 'CITY_INPUT', 'STREET_INPUT', 'POSTAL_INPUT',"
						+ " 'PHONE_INPUT', 'EMAIL_INPUT', 'BUSINESS_TAX_ID_INPUT', 'DRIVER_LICENSE_INPUT');";
				newCustomerQuery = newCustomerQuery.replace("STATE_INPUT", input.get("state"));
				newCustomerQuery = newCustomerQuery.replace("CITY_INPUT", input.get("city"));
				newCustomerQuery = newCustomerQuery.replace("STREET_INPUT", input.get("street"));
				newCustomerQuery = newCustomerQuery.replace("POSTAL_INPUT", input.get("postal"));
				newCustomerQuery = newCustomerQuery.replace("PHONE_INPUT", input.get("phone"));
				newCustomerQuery = newCustomerQuery.replace("EMAIL_INPUT", input.get("email"));
				newCustomerQuery = newCustomerQuery.replace("BUSINESS_TAX_ID_INPUT", businessTaxId);
				newCustomerQuery = newCustomerQuery.replace("DRIVER_LICENSE_INPUT", driversLicense);

				System.out.println(newCustomerQuery);
				statement.executeUpdate(newCustomerQuery);

				if (!driversLicense.equals("N/A")) {
					String individualQuery = "insert into Individual values ('DRIVER_LICENSE_INPUT', 'FIRST_NAME_INPUT', 'LAST_NAME_INPUT', 'BUSINESS_TAX_ID_INPUT');";
					individualQuery = individualQuery.replace("FIRST_NAME_INPUT", input.get("firstName"));
					individualQuery = individualQuery.replace("LAST_NAME_INPUT", input.get("lastName"));
					individualQuery = individualQuery.replace("BUSINESS_TAX_ID_INPUT", businessTaxId);
					individualQuery = individualQuery.replace("DRIVER_LICENSE_INPUT", driversLicense);
					System.out.println(individualQuery);
					statement.executeUpdate(individualQuery);
				}

				if (!businessTaxId.equals("N/A")) {
					String businessQuery = "insert into Business values ('BUSINESS_TAX_ID_INPUT', 'BUSINESS_NAME_INPUT', 'CONTACT_TITLE_INPUT', 'FIRST_NAME_INPUT', 'LAST_NAME_INPUT', 'DRIVER_LICENSE_INPUT');";
					businessQuery = businessQuery.replace("FIRST_NAME_INPUT", input.get("businessFirstName"));
					businessQuery = businessQuery.replace("LAST_NAME_INPUT", input.get("businessLastName"));
					businessQuery = businessQuery.replace("BUSINESS_NAME_INPUT", input.get("businessName"));
					businessQuery = businessQuery.replace("CONTACT_TITLE_INPUT", input.get("businessContactTitle"));
					businessQuery = businessQuery.replace("BUSINESS_TAX_ID_INPUT", businessTaxId);
					businessQuery = businessQuery.replace("DRIVER_LICENSE_INPUT", driversLicense);
					System.out.println(businessQuery);
					statement.executeUpdate(businessQuery);
				}
			}

			String boughtQuery = "Insert into Bought values ('VIN_INPUT', 'SALES_DATE_INPUT', 'BUSINESS_TAX_ID_INPUT', 'DRIVERS_LICENSE_INPUT', 'SALESPEOPLE_INPUT')";
			boughtQuery = boughtQuery.replace("VIN_INPUT", input.get("vin"));
			boughtQuery = boughtQuery.replace("SALES_DATE_INPUT", input.get("sellDate"));
			boughtQuery = boughtQuery.replace("BUSINESS_TAX_ID_INPUT", businessTaxId);
			boughtQuery = boughtQuery.replace("DRIVERS_LICENSE_INPUT", driversLicense);
			boughtQuery = boughtQuery.replace("SALESPEOPLE_INPUT", input.get("salesPeople"));
			System.out.println(boughtQuery);
			statement.executeUpdate(boughtQuery);

			con.commit();
			String result = "Product created : " + input;
			return Response.status(201).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").entity(result)
					.build();

		} catch (SQLException e) {
			con.rollback();
			e.printStackTrace();
		} finally {
			con.setAutoCommit(true);
			con.close();
		}
		return null;

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("vehicleHasBeenSold")
	@Consumes("application/json")
	public Response vehicleHasBeenSold(Map<String, String> map) {
		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "SELECT EXISTS(SELECT * FROM bought WHERE vin = 'VIN_INPUT') as vehicleHasBeenSold;";
		query = query.replace("VIN_INPUT", map.get("vin"));

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement = con.createStatement();
			System.out.println(query);
			ResultSet rs = statement.executeQuery(query);
			List<Boolean> output = new ArrayList();
			rs.next();
			output.add(rs.getBoolean("vehicleHasBeenSold"));
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
