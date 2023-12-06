package com.emp.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.model.Employee;
import com.emp.model.TaxDeduction;
import com.emp.repository.EmployeeRepository;

/*
 * Class to implement Tax deduction
 */
@Service
public class TaxImpl {
	
	@Autowired
	EmployeeRepository empRepository;
	
	public TaxDeduction calculateTaxDeduction(Employee employee) {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        LocalDate doj = employee.getDoj();

        // Calculate the number of months the employee has worked in the current financial year
        int monthsWorked = calculateMonthsWorked(doj, currentDate);

        // Calculate total salary considering loss of pay
        double totalSalary = calculateTotalSalary(employee.getSalary(), monthsWorked);

        // Calculate tax based on the provided rules
        double taxAmount = calculateTaxAmount(totalSalary);

        // Calculate cess amount for the amount more than 2500000
        double cessAmount = calculateCessAmount(totalSalary);

        // Create and return TaxDeduction object
        return new TaxDeduction(
                employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getLastName(),
                totalSalary,
                taxAmount,
                cessAmount
        );
    }

    private int calculateMonthsWorked(LocalDate doj, LocalDate currentDate) {
        int monthsWorked = 0;
        if (doj.isBefore(currentDate)) {
            monthsWorked = (int) doj.until(currentDate).toTotalMonths();
        }
        return monthsWorked;
    }

    private double calculateTotalSalary(double salary, int monthsWorked) {
        // Consider loss of pay per day for the total salary calculation
        double lossOfPayPerDay = salary / 30;
        double totalSalary = salary * monthsWorked - (lossOfPayPerDay * empRepository.count());
        return totalSalary;
    }

      
 // Implement tax calculation logic based on the provided rules
    private double calculateTaxAmount(double totalSalary) {
        if (totalSalary <= 250000) {
            // No Tax for <=250000
            return 0.0;
        } else if (totalSalary > 250000 && totalSalary <= 500000) {
            // 5% Tax for >250000 and <=500000
            return 0.05 * (totalSalary - 250000);
        } else if (totalSalary > 500000 && totalSalary <= 1000000) {
            // 10% Tax for >500000 and <=1000000
            return 0.1 * (totalSalary - 500000) + calculateTaxAmount(500000); 
        } else {
            // 20% Tax for >1000000
            return 0.2 * (totalSalary - 1000000) + calculateTaxAmount(1000000); 
        }
    }


    private double calculateCessAmount(double totalSalary) {
        // Collect additional 2% cess for the amount more than 2500000
        double excessAmount = Math.max(0, totalSalary - 2500000);
        return 0.02 * excessAmount;
    }

}
