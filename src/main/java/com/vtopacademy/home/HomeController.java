package com.vtopacademy.home;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

import com.vtopacademy.account.AccountsController;
import com.vtopacademy.schools.SchoolsController;

@Tag(name = "Accounts", description = "APIs for user registration and login")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class HomeController {
	 
	@GetMapping("/api") 
	public EntityModel<Home> getUrls() {
		
		Home home  = new Home("Welcome to vtopacademy tutorial api. "
			+ "Use this urls to access available resources.");
		
		return EntityModel.of(home, 
			linkTo(methodOn(SchoolsController.class)
				.getAllSchools()).withRel("schools"),
			linkTo(methodOn(AccountsController.class)
				.getAccountUrls()).withRel("accounts")
			); 
	} 
} 
