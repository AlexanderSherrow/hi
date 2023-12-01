package com.BuzzCars.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;

import com.BuzzCars.controller.ChassisTypeController;
import com.BuzzCars.controller.ColorController;
import com.BuzzCars.controller.ConditionController;
import com.BuzzCars.controller.CustomerController;
import com.BuzzCars.controller.FuelTypeController;
import com.BuzzCars.controller.ManufacturerController;
import com.BuzzCars.controller.PartsOrderController;
import com.BuzzCars.controller.ReportsController;
import com.BuzzCars.controller.UserController;
import com.BuzzCars.controller.VehicleController;
import com.BuzzCars.controller.VendorController;

@ApplicationPath("")
public class RestEasyServices extends Application {

	private Set<Object> singletons = new HashSet();

	public RestEasyServices() {
		this.singletons.add(new UserController());
		this.singletons.add(new VehicleController());
		this.singletons.add(new CustomerController());
		this.singletons.add(new VendorController());
		this.singletons.add(new ColorController());
		this.singletons.add(new ManufacturerController());
		this.singletons.add(new FuelTypeController());
		this.singletons.add(new ChassisTypeController());
		this.singletons.add(new ConditionController());
		this.singletons.add(new PartsOrderController());
		this.singletons.add(new ReportsController());
		this.singletons.add(this.getCorsFilter());
	}

	private CorsFilter getCorsFilter() {
		CorsFilter result = new CorsFilter();
		result.getAllowedOrigins().add("http://localhost:3000");
		result.setAllowedMethods("OPTIONS, GET, POST, DELETE, PUT, PATCH");
		result.setCorsMaxAge(86400);
		return result;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
