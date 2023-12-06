package com.emp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emp.impl.TaxImpl;
import com.emp.model.Employee;
import com.emp.model.TaxDeduction;
import com.emp.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
	
	@Autowired
	EmployeeRepository empRepository;
	
	@Autowired
	TaxImpl taxImpl;
	
	//Controller for add employee information
	@PostMapping("/add")
	public String addEmployee(@RequestBody Employee employee) {
		
		// Validate employee data 
        if (employee.getEmployeeId() == null || employee.getFirstName() == null ||
                employee.getLastName() == null || employee.getEmail() == null) {
            return "Invalid data. Please provide all mandatory fields.";
        }

        // Save employee details
        empRepository.save(employee);
        return "Employee added successfully!";
		
	}
	
	@GetMapping("/findAll")
	public List<Employee> empInfo() {
        List<Employee> employees = empRepository.findAll();
		return employees;
		
	}
	
	//Controller to check tax deduction information
	@GetMapping("/tax-deduction")
    public List<TaxDeduction> getTaxDeductions() {
		List<TaxDeduction> taxDeductions = new ArrayList<>();
        List<Employee> employees = empRepository.findAll();

        for (Employee employee : employees) {
        	//Segregate the Tax logic to implementation class
            TaxDeduction taxDeduction = taxImpl.calculateTaxDeduction(employee);
            taxDeductions.add(taxDeduction);
        }

        return taxDeductions;

   }
}