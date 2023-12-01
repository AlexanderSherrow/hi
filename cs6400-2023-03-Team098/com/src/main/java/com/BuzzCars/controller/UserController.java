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

import com.BuzzCars.entity.User;

@Path("user")
public class UserController {

	public UserController() {
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
	@Path("save")
	@Consumes("application/json")
	public Response create(User input) {

		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "insert into user values ('" + input.getUserName() + "', '" + input.getPassword() + "', '"
				+ input.getFirstName() + "', '" + input.getLastName() + "');";
		System.out.println(query);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement = con.createStatement();
			statement.executeUpdate(query);
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
	@Path("login")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(Map<String, String> input) {

		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		String query = "select exists(select * from User where username = 'USER_NAME_INPUT' and password = 'PASSWORD_INPUT') as login";
		query = query.replace("USER_NAME_INPUT", input.get("username"));
		query = query.replace("PASSWORD_INPUT", input.get("password"));

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
			rs.next();

			String[] tables = { "Owner", "Manager", "Salespeople", "InventoryClerk", };
			HashMap<String, String> result = new HashMap();

			if (rs.getBoolean(1))
				for (String table : tables) {
					query = "select exists(select * from TABLE_INPUT where username = 'USER_NAME_INPUT') as type";
					query = query.replace("USER_NAME_INPUT", input.get("username"));
					query = query.replace("TABLE_INPUT", table);
					statement = con.createStatement();
					rs = statement.executeQuery(query);
					rs.next();

					if (rs.getBoolean("type")) {
						result.put("type", table);
						con.close();
						return Response.status(201).header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Allow-Credentials", "true")
								.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
								.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
								.entity(result).build();
					}

				}
			else
				result.put("type", "FAILED");

			con.close();
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

}
