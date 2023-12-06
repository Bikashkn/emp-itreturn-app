package com.emp.model;

import lombok.Data;

@Data
public class TaxDeduction {
	
	private String employeeId;
    private String firstName;
    private String lastName;
    private double yearlySalary;
    private double taxAmount;
    private double cessAmount;
    
    public TaxDeduction(String employeeId, String firstName, String lastName,
            double yearlySalary, double taxAmount, double cessAmount) {
			this.employeeId = employeeId;
			this.firstName = firstName;
			this.lastName = lastName;
			this.yearlySalary = yearlySalary;
			this.taxAmount = taxAmount;
			this.cessAmount = cessAmount;

 }
}