package loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class dataLoader {

//	static String username = "dbmasteruser";
//	static String password = "R4.?:XM2H|$-aB1Bmj7xKoG!jFD8>RJG";
//	static String url = "jdbc:mysql://ls-a796dc16d733095b4067e02434fdca87bc364277.cpmqvpdxfdzt.us-east-1.rds.amazonaws.com/cs6400";

	static String username = "root";
	static String password = "password";
	static String url = "jdbc:mysql://localhost:3306/cs6400?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";

	public static void main(String[] args) throws IOException {
		File usersFile = new File("Demo Data\\users.tsv");
		File customersFile = new File("Demo Data\\customers.tsv");
		File partsFile = new File("Demo Data\\parts.tsv");
		File vehiclesFile = new File("Demo Data\\vehicles.tsv");
		File vendorsFile = new File("Demo Data\\vendors.tsv");

		BufferedReader TSVReader = new BufferedReader(new FileReader(usersFile));
		String line = TSVReader.readLine();
		ArrayList<String[]> userData = new ArrayList<>();
		try {
			while (line != null) {
				String[] lineItems = line.split("\t");
				userData.add(lineItems);
				line = TSVReader.readLine();
			}
			TSVReader.close();
			insertUser(userData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		TSVReader = new BufferedReader(new FileReader(customersFile));
		line = TSVReader.readLine();
		ArrayList<String[]> customerData = new ArrayList<>();
		try {
			while (line != null) {
				String[] lineItems = line.split("\t");
				customerData.add(lineItems);
				line = TSVReader.readLine();
			}
			TSVReader.close();
			insertCustomer(customerData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		TSVReader = new BufferedReader(new FileReader(vehiclesFile));
		line = TSVReader.readLine();
		ArrayList<String[]> vehiclesData = new ArrayList<>();
		try {
			while (line != null) {
				String[] lineItems = line.split("\t");
				vehiclesData.add(lineItems);
				line = TSVReader.readLine();
			}
			TSVReader.close();
			insertVehicle(vehiclesData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		TSVReader = new BufferedReader(new FileReader(vendorsFile));
		line = TSVReader.readLine();
		ArrayList<String[]> vendorsData = new ArrayList<>();

		try {
			while (line != null) {
				String[] lineItems = line.split("\t");
				vendorsData.add(lineItems);
				line = TSVReader.readLine();
			}
			TSVReader.close();
			insertVendor(vendorsData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		TSVReader = new BufferedReader(new FileReader(partsFile));
		line = TSVReader.readLine();
		ArrayList<String[]> partsData = new ArrayList<>();

		try {
			while (line != null) {
				String[] lineItems = line.split("\t");
				partsData.add(lineItems);
				line = TSVReader.readLine();
			}
			TSVReader.close();
			insertPartsOrder(partsData);
			insertParts(partsData);
			// insertParts(partsData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("DATA UPLOADED");

	}

	public static void insertUser(List<String[]> data) {

		try {
			Connection con = DriverManager.getConnection(url, username, password);
			int count = 0;
			for (String[] item : data) {
				if (count == 0) {
					count++;
					continue;
				}
				if (count == 1) {
					Statement statement = con.createStatement();
					String query = "Insert into User values " + "( '" + item[0] + "', '" + item[1] + "', '" + item[2]
							+ "', '" + item[3] + "' );";
					statement.executeUpdate(query);

					query = "Insert into Manager values " + "( '" + item[0] + "' );";
					statement = con.createStatement();
					statement.executeUpdate(query);

					query = "Insert into InventoryClerk values " + "( '" + item[0] + "' );";
					statement = con.createStatement();
					statement.executeUpdate(query);

					query = "Insert into Salespeople values " + "( '" + item[0] + "' );";
					statement = con.createStatement();
					statement.executeUpdate(query);

					query = "Insert into Owner values " + "( '" + item[0] + "' );";
					statement = con.createStatement();
					statement.executeUpdate(query);
					count++;
					continue;

				}
				Statement statement = con.createStatement();
				String query = "Insert into User values " + "( '" + item[0] + "', '" + item[1] + "', '" + item[2]
						+ "', '" + item[3] + "' );";

				statement.executeUpdate(query);

				String roleLine = item[4];
				List<String> roleList = Arrays.asList(roleLine.split(","));
				for (String role : roleList) {
					if (role.equals("roles"))
						continue;
					if (role.equals("inventory clerk"))
						role = "InventoryClerk";
					else if (role.equals("salesperson"))
						role = "Salespeople";
					else
						role = role.substring(0, 1).toUpperCase() + role.substring(1);
					query = "Insert into " + role + " values " + "( '" + item[0] + "' );";
					statement = con.createStatement();
					System.out.println(query);
					statement.executeUpdate(query);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void insertVehicle(List<String[]> data) {
		int count = 0;
		try {
			Connection con = DriverManager.getConnection(url, username, password);
			for (String[] item : data) {
				if (count == 0) {
					count++;
					continue;
				}
				Statement statement = con.createStatement();
				String vin = item[0];
				String modelName = item[1];
				int year = Integer.parseInt(item[2]);
				String desc = item[3];
				String manufacturer = item[4];
				String condition = item[5];
				String vehicleType = item[6];
				String odometer = item[7];
				String fuelType = item[8];
				String colors = item[9];
				String purchaseDate = item[10];
				float price = Float.parseFloat(item[11]);
				String purchasedFromCustomerId = item[12];
				String clerk = item[13];
				String saleDate = "";
				String soldToCustomerId = "";
				String salesperson = "";
				String query = "Insert into Vehicle values " + "( '" + modelName + "', '" + year + "', '" + desc
						+ "', '" + vin + "', '" + odometer + "', '" + manufacturer + "', '" + fuelType + "', '"
						+ vehicleType + "' );";

				if (desc.equals(""))
					query = "Insert into Vehicle (model_name, model_year, vin, mileage, manufacturer, fuel_type, chassis_type) "
							+ "VALUES " + "( '" + modelName + "', '" + year + "', '" + vin + "', '" + odometer + "', '"
							+ manufacturer + "', '" + fuelType + "', '" + vehicleType + "' );";

				System.out.println(query);
				statement.executeUpdate(query);

				String[] colorsArr = colors.split(",");
				for (String colorElement : colorsArr) {
					statement = con.createStatement();
					String colorQuery = "Insert into Color values " + "( '" + vin + "', '" + colorElement + "');";
					System.out.println(colorQuery);
					statement.executeUpdate(colorQuery);
				}

				String driversLicenseQuery = "Select count(*) from Customer c where c.drivers_license_number = '"
						+ purchasedFromCustomerId + "';";
				statement = con.createStatement();
				ResultSet rs = statement.executeQuery(driversLicenseQuery);
				String driversLicense = "N/A";
				String businessTaxId = "N/A";
				rs.next();
				if (rs.getString(1).equals("1"))
					driversLicense = purchasedFromCustomerId;
				else
					businessTaxId = purchasedFromCustomerId;

				if (item.length >= 15) {
					saleDate = item[14];
					soldToCustomerId = item[15];
					salesperson = item[16];
					String boughtByCustomerQuery = "Insert into Bought values " + "( '" + vin + "', '" + saleDate
							+ "', '" + businessTaxId + "', '" + driversLicense + "', '" + salesperson + "' );";
					statement = con.createStatement();
					System.out.println(boughtByCustomerQuery);
					statement.executeUpdate(boughtByCustomerQuery);

				}

				statement = con.createStatement();
				String soldToUsQuery = "Insert into Sold values " + "( '" + vin + "', '" + condition + "', '" + price
						+ "', '" + purchaseDate + "', '" + businessTaxId + "', '" + driversLicense + "', '" + clerk
						+ "' );";
				System.out.println(soldToUsQuery);
				statement.executeUpdate(soldToUsQuery);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void insertVendor(List<String[]> data) {

		try {
			Connection con = DriverManager.getConnection(url, username, password);
			int count = 0;
			for (String[] item : data) {
				if (count == 0) {
					count++;
					continue;
				}
				Statement statement = con.createStatement();
				String vendorName = item[0];
				String phone = item[1];
				String street = item[2];
				String city = item[3];
				String state = item[4];
				String postal = item[5];

				String query = "Insert into Vendor values " + "( '" + vendorName + "', '" + state + "', '" + street
						+ "', '" + city + "', '" + postal + "', '" + phone + "' );";
				System.out.println(query);
				statement.executeUpdate(query);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void insertPartsOrder(List<String[]> data) {

		try {
			Connection con = DriverManager.getConnection(url, username, password);
			int iteration = 0;
			for (String[] item : data) {
				if (iteration == 0) {
					iteration++;
					continue;
				}
				Statement statement = con.createStatement();
				String vin = item[0];
				String orderNum = item[1];

				String countQuery = "Select COUNT(*) from PartsOrder p where p.purchase_order_number = '" + vin + "-"
						+ orderNum + "';";
				ResultSet countResult = statement.executeQuery(countQuery);
				countResult.next();
				String count = countResult.getString(1);
				if (!count.equals("0"))
					continue;

				String query = "Insert into PartsOrder values " + "( '" + vin + "', '" + vin + "-" + orderNum + "');";
				System.out.println(query);
				statement.executeUpdate(query);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void insertParts(List<String[]> data) {

		try {
			Connection con = DriverManager.getConnection(url, username, password);
			int count = 0;
			for (String[] item : data) {
				if (count == 0) {
					count++;
					continue;
				}
				Statement statement = con.createStatement();
				String vin = item[0];
				String orderNum = item[1];
				String vendorName = item[2];
				String partNumber = item[3];
				String description = item[4];
				float price = Float.parseFloat(item[5]);
				String status = item[6];
				int qty = Integer.parseInt(item[7]);
				String query = "Insert into Part values " + "( '" + partNumber + "', '" + vin + "-" + orderNum + "', '"
						+ qty + "', '" + price + "', '" + description + "', '" + vendorName + "', '" + status + "' );";
				System.out.println(query);
				statement.executeUpdate(query);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void insertCustomer(List<String[]> data) {

		try {
			Connection con = DriverManager.getConnection(url, username, password);
			int count = 0;
			for (String[] item : data) {
				if (count == 0) {
					count++;
					continue;
				}
				Statement statement = con.createStatement();
				String type = item[0];
				String email = item[1];
				String phone = item[2];
				String street = item[3];
				String city = item[4];
				String state = item[5];
				String postal = item[6];
				String businessId = item[7];
				String businessName = item[8];
				String businessContactFirstName = item[9];
				String businessContactLastName = item[10];
				String businessContactTitle = item[11];
				String driversLicense = "N/A";
				String personFirstName = "N/A";
				String personLastName = "N/A";
				if (email.equals(""))
					email = "Not Provided";
				if (item.length > 12) {
					driversLicense = item[12];
					personFirstName = item[13];
					personLastName = item[14];
				}

				if (businessId.equals(""))
					businessId = "N/A";
				if (businessName.equals(""))
					businessName = "N/A";
				if (businessContactFirstName.equals(""))
					businessContactFirstName = "N/A";
				if (businessContactLastName.equals(""))
					businessContactLastName = "N/A";
				if (businessContactTitle.equals(""))
					businessContactTitle = "N/A";

				String query = "Insert into Customer values " + "( '" + state + "', '" + city + "', '" + street + "', '"
						+ postal + "', '" + phone + "', '" + email + "', '" + businessId + "', '" + driversLicense
						+ "' );";
				System.out.println(query);
				statement.executeUpdate(query);
				if (type.equals("Person")) {
					String indivQuery = "Insert into Individual values " + "( '" + driversLicense + "', '"
							+ personFirstName + "', '" + personLastName + "', '" + businessId + "' );";
					System.out.println(indivQuery);
					statement.executeUpdate(indivQuery);
				} else {
					String businessQuery = "Insert into Business values " + "( '" + businessId + "', '" + businessName
							+ "', '" + businessContactTitle + "', '" + businessContactFirstName + "', '"
							+ businessContactLastName + "', '" + driversLicense + "' );";
					System.out.println(businessQuery);
					statement.executeUpdate(businessQuery);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}