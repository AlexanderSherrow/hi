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

@Path("manufacturer")
public class ManufacturerController {

	public ManufacturerController() {
		// TODO Auto-generated constructor stub
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getAll")
	public Response numberOfVehiclesForSale() {
		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "select distinct name from Manufacturer;";

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
			List<String> output = new ArrayList<String>();
			while (rs.next())
				output.add(rs.getString(1));
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
	@Path("save")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(Map<String, String> input) {
		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "insert into Manufacturer values ('MANUFACTURER_INPUT');";
		query = query.replace("MANUFACTURER_INPUT", input.get("name"));

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
			List<String> output = new ArrayList<String>();
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
