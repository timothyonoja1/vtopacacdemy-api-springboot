package com.vtopacademy.kclasses;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vtopacademy.schools.School; 

@Repository
public interface KclassRepository extends JpaRepository<Kclass, Long> {
	
	  List<Kclass> findBySchool(School school);      
	  
}
