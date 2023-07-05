package com.vtopacademy.account;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vtopacademy.account.entities.AccountUrl;
import com.vtopacademy.account.entities.JwtResponse;
import com.vtopacademy.account.entities.MessageResponse;
import com.vtopacademy.account.entities.RegisterRequest; 
import com.vtopacademy.account.entities.LoginRequest; 
import com.vtopacademy.account.jwt.JwtUtils;

import com.vtopacademy.account.models.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/accounts")
public class AccountsController {
  
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils; 

	@GetMapping("")
	public ResponseEntity<?> getAccountUrls() { 
		String baseUrl = ServletUriComponentsBuilder
			.fromCurrentContextPath()
			.build().toUriString();
		String registerUrl = baseUrl + "/api/accounts/register";
		String loginUrl = baseUrl + "/api/accounts/login";
		return ResponseEntity.ok(new AccountUrl(registerUrl, loginUrl));
	}	 
		
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
    
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
		List<String> roles = userDetails.getAuthorities().stream()
			.map(item -> item.getAuthority())
			.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
			userDetails.getId(), 
			userDetails.getUsername(), 
			userDetails.getEmail(), 
			roles));
	}
 
	@PostMapping("/register")  
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
		if (userRepository.existsByUsername(registerRequest.getUsername())) {
			return ResponseEntity
				.badRequest()
				.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(registerRequest.getEmail())) {
			return ResponseEntity
				.badRequest()
				.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(registerRequest.getUsername(), 
			registerRequest.getEmail(),
			encoder.encode(registerRequest.getPassword()));

		Set<Role> roles = new HashSet<>();
		
		Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
			.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		      
		roles.add(userRole);
		user.setRoles(roles);
		
		userRepository.save(user);
 
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	@GetMapping("/create-default-roles") 
	public ResponseEntity<?> createDefautUserRoles() {
		if (!roleRepository.existsByName(RoleName.ROLE_USER)) {
			roleRepository.save(new Role(RoleName.ROLE_USER));	
		}
		if (!roleRepository.existsByName(RoleName.ROLE_ADMIN)) {
			roleRepository.save(new Role(RoleName.ROLE_ADMIN));	
		}
		return ResponseEntity.ok(new MessageResponse("Default Roles created successfully!"));
	}
	
}
