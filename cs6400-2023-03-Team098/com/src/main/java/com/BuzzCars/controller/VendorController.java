package com.BuzzCars.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.BuzzCars.entity.Vendor;

@Path("vendor")
public class VendorController {

	public VendorController() {
		// TODO Auto-generated constructor stub
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getAllNames")
	public Response getAllNames() {

		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "select name from Vendor";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			List<Vendor> vendors = new ArrayList();
			while (resultSet.next()) {
				Vendor vendor = new Vendor();
				vendor.setName(resultSet.getString(1));
				vendors.add(vendor);
			}
			con.close();
			return Response.status(200).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").entity(vendors)
					.build();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getTotalCostPerVendor")
	public Response getTotalCost(Map<String, String> input) {

		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		// String vendorName = vendor.getName();
		String query = "Select TRUNCATE(SUM(total_cost_per_order), 2) AS total_cost_for_vendor "
				+ "FROM (SELECT PartsOrder.vendor_name, SUM(part.cost_per_part * part.part_quantity) AS total_cost_per_order "
				+ "FROM PartsOrder " + "JOIN part ON PartsOrder.purchase_order_number = part.purchase_order_number "
				+ "GROUP BY PartsOrder.purchase_order_number, PartsOrder.vendor_name) AS total_cost_per_order "
				+ "where vendor_name = '" + input.get("name") + "' GROUP BY vendor_name;";
		System.out.println(query);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			float totalCost = 0;
			resultSet.next();
			totalCost = resultSet.getFloat(1);

			con.close();
			return Response.status(200).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").entity(totalCost)
					.build();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

}
