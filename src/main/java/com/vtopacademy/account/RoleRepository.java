package com.vtopacademy.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vtopacademy.account.models.Role;
import com.vtopacademy.account.models.RoleName;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Optional<Role> findByName(RoleName name);
	Boolean existsByName(RoleName name);
	
}