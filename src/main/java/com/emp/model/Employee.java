package com.emp.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Employee {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> phoneNumbers;
    private LocalDate doj;
    private double salary;
    
    

}
