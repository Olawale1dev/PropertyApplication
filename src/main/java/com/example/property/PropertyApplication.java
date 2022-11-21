package com.example.property;

import com.example.property.service.PropertyServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;


@SpringBootApplication
public class PropertyApplication /*implements CommandLineRunner*/ {
	/*@Resource
	PropertyServiceImpl propertyService;*/
	public static void main(String[] args) {
		SpringApplication.run(PropertyApplication.class, args);
	}

	/*@Override
	public void run(String... arg) throws Exception {
		//propertyService.deleteAll();
		//propertyService.init();
	}*/

}
