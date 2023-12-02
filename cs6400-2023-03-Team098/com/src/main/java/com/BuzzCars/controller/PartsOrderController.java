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

import com.BuzzCars.entity.PartsOrder;
import com.BuzzCars.entity.User;

@Path("partsOrder")
public class PartsOrderController {

	public PartsOrderController() {
		// TODO Auto-generated constructor stub
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getAll")
	public Response getAll() {

//		String username = "dbmasteruser";
//		String password = "R4.?:XM2H|$-aB1Bmj7xKoG!jFD8>RJG";
//		String url = "jdbc:mysql://ls-a796dc16d733095b4067e02434fdca87bc364277.cpmqvpdxfdzt.us-east-1.rds.amazonaws.com/cs6400";

		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "select * from User";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			List<User> users = new ArrayList();
			while (resultSet.next()) {
				User user = new User();
				user.setUserName(resultSet.getString(1));
				user.setPassword(resultSet.getString(2));
				user.setFirstName(resultSet.getString(3));
				user.setLastName(resultSet.getString(4));
				users.add(user);
			}
			con.close();
			return Response.status(200).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").entity(users)
					.build();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@POST
	@Path("createNewPartsOrder")
	@Consumes("application/json")
	public Response create(Map<String, Map<String, String>> input) {
		System.out.println(input);
		Map<String, String> length = input.get("numberOfParts");
		int numberOfParts = Integer.parseInt(length.get("length"));
		String vin = input.get("vin").get("vin");
		String partOrderNumber = input.get("partOrderNumber").get("partOrderNumber");

		Map<String, Map<String, String>> parts = new HashMap();
		for (int i = 0; i < numberOfParts; i++) {
			String get = Integer.toString(i);
			parts.put("Part" + i, input.get(get));
			System.out.println(parts);
		}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {

			String username = "root";
			String password = "password";
			String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
			Connection con = DriverManager.getConnection(url, username, password);

			String partOrderQuery = "Insert into PartsOrder values ('VIN_INPUT', 'PURCHASE_ORDER_NUMBER_INPUT');";
			partOrderQuery = partOrderQuery.replace("VIN_INPUT", vin);
			partOrderQuery = partOrderQuery.replace("PURCHASE_ORDER_NUMBER_INPUT", partOrderNumber);

			Statement statement = con.createStatement();
			System.out.println(partOrderQuery);
			statement.executeUpdate(partOrderQuery);

			for (String key : parts.keySet()) {
				Map<String, String> partMap = parts.get(key);
				System.out.println(partMap);

				String partQuery = "Insert into Part values ('PART_NUMBER_INPUT', 'PURCHASE_ORDER_NUMBER_INPUT', PART_QUANTITY_INPUT, COST_PER_PART_INPUT, 'DESCRIPTION_INPUT', 'VENDOR_NAME_INPUT', 'ordered');";
				partQuery = partQuery.replace("VENDOR_NAME_INPUT", partMap.get("vendor"));
				partQuery = partQuery.replace("PART_NUMBER_INPUT", partMap.get("partNumber"));
				partQuery = partQuery.replace("PURCHASE_ORDER_NUMBER_INPUT", partOrderNumber);
				partQuery = partQuery.replace("PART_QUANTITY_INPUT", partMap.get("quantity"));
				partQuery = partQuery.replace("COST_PER_PART_INPUT", partMap.get("cost"));
				partQuery = partQuery.replace("DESCRIPTION_INPUT", partMap.get("description"));

				statement = con.createStatement();
				System.out.println(partQuery);
				statement.executeUpdate(partQuery);
			}

			con.close();
			String result = "Product created : " + input;
			return Response.status(201).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").entity(result)
					.build();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getPartsByVin")
	@Consumes("application/json")
	public Response getPartsByVin(Map<String, String> input) {
		System.out.println(input);
		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "select part_number, description, vendor_name, purchase_order_number, TRUNCATE(cost_per_part * part_quantity, 2) as cost, current_status"
				+ " from partsOrder" + " natural join part where vin = 'VIN_INPUT';";

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
			List<PartsOrder> output = new ArrayList();
			while (rs.next()) {
				PartsOrder partOrder = new PartsOrder();
				partOrder.setPartNumber(rs.getString("part_number"));
				partOrder.setDescription(rs.getString("description"));
				partOrder.setVendorName(rs.getString("vendor_name"));
				partOrder.setPurchaseOrderNumber(rs.getString("purchase_order_number"));
				partOrder.setTotalPartsCost(rs.getFloat("cost"));
				partOrder.setCurrentStatus(rs.getString("current_status"));
				output.add(partOrder);
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
	@Path("updatePartStatus")
	@Consumes("application/json")
	public Response updatePartStatus(Map<String, String> input) {
		System.out.println(input);
		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "UPDATE Part SET current_status = 'CURRENT_STATUS_INPUT' WHERE part_number = 'PART_NUMBER_INPUT' and purchase_order_number = 'PURCHASE_ORDER_NUMBER_INPUT';";

		query = query.replace("CURRENT_STATUS_INPUT", input.get("status"));
		query = query.replace("PART_NUMBER_INPUT", input.get("partNumber"));
		query = query.replace("PURCHASE_ORDER_NUMBER_INPUT", input.get("purchaseOrderNumber"));

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
			statement.executeUpdate(query);
			con.close();
			return Response.status(200).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").entity("success")
					.build();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getPartsOrderNumber")
	@Consumes("application/json")
	public Response getPartsOrderNumber(Map<String, String> input) {
		System.out.println(input);
		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "select count(*) from partsOrder where vin = 'VIN_INPUT';";

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
			Map<String, Integer> output = new HashMap();
			output.put("count", rs.getInt(1));
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
