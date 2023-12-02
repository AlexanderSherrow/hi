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

import com.BuzzCars.entity.Customer;

@Path("customer")
public class CustomerController {

	public CustomerController() {
		// TODO Auto-generated constructor stub
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getAll")
	public Response getAllCustomers() {

		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";

		String query = "select * from customer";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			List<Customer> customers = new ArrayList();
			while (resultSet.next()) {
				Customer customer = new Customer();
				customer.setState(resultSet.getString(1));
				customer.setCity(resultSet.getString(2));
				customer.setStreet(resultSet.getString(3));
				customer.setPostalCode(resultSet.getString(4));
				customer.setPhoneNumber(resultSet.getString(5));
				customer.setEmail(resultSet.getString(6));
				customer.setBusinessTaxId(resultSet.getString(7));
				customer.setDriversLicenseNumber(resultSet.getString(8));
				customers.add(customer);
			}
			con.close();
			return Response.status(200).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").entity(customers)
					.build();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("findCustomer")
	@Consumes("application/json")
	public Response findCustomer(Map<String, String> map) {
		System.out.println(map);
		String username = "root";
		String password = "password";
		String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";

		String query = "select * from customer where customer.business_tax_id = 'BUSINESS_TAX_ID_INPUT' and customer.drivers_license_number = 'DRIVERS_LICENSE_NUMBER_INPUT';";

		String businessTaxIdInput = map.get("businessTaxID");
		String driverLicenseInput = map.get("driversLicense");

		query = query.replace("BUSINESS_TAX_ID_INPUT", businessTaxIdInput);
		query = query.replace("DRIVERS_LICENSE_NUMBER_INPUT", driverLicenseInput);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			System.out.println(query);
			Connection con = DriverManager.getConnection(url, username, password);
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			List<Customer> customers = new ArrayList();
			while (resultSet.next()) {
				Customer customer = new Customer();
				customer.setState(resultSet.getString(1));
				customer.setCity(resultSet.getString(2));
				customer.setStreet(resultSet.getString(3));
				customer.setPostalCode(resultSet.getString(4));
				customer.setPhoneNumber(resultSet.getString(5));
				customer.setEmail(resultSet.getString(6));
				customer.setBusinessTaxId(resultSet.getString(7));
				customer.setDriversLicenseNumber(resultSet.getString(8));
				customers.add(customer);
			}
			con.close();
			return Response.status(200).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").entity(customers)
					.build();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

}
