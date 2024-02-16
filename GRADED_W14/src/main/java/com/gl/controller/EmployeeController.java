package com.gl.controller;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gl.dbModel.Employee;

@Controller
public class EmployeeController {

	@RequestMapping("/")
	public String homeLink() {
		return "welcome";
	}

	@RequestMapping("/show-employee-con")
	public String showEmployeeLink(Model data) {

		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();

		try {
			Query q1 = session.createQuery("from Employee");
			List<Employee> employees = q1.getResultList();
			data.addAttribute("employees", employees);

		} catch (Exception e) {
			System.out.println("Hibernate Error: " + e.getMessage());
		}

		return "show-employee";
	}
	
	@RequestMapping("/add-employee-con")
	public String addEmployeeLink() {
		return "add-employee";
	}
	
	@PostMapping("/added-employee-con")
	public String insertLink(@RequestParam String name, @RequestParam String address, @RequestParam String phone, @RequestParam double salary, Model data) {
		
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		
		try {
			Transaction tx = null;
			tx = session.beginTransaction();
			
			Employee e1 = new Employee(name, address, phone, salary);
			session.save(e1);
			
			tx.commit();
			
			Query q1 = session.createQuery("from Employee");
			List<Employee> employees = q1.getResultList();
			data.addAttribute("employees", employees);
		}
		catch (Exception e) {
			System.out.println("Hibernate Error: " + e.getMessage());
		}

		return "show-employee";	
		
	}
	
	@GetMapping("/update-employee-con")
	public String updateEmployeeFormLink(@RequestParam int id, Model data) {

		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();

		try {
			Employee updateEmployee = session.get(Employee.class, id);
			data.addAttribute("employee", updateEmployee);

		}
		catch (Exception e) {
			System.out.println("Hibernate Error: " + e.getMessage());
		}

		return "update-employee";

	}

	@PostMapping("/updation-con")
	public String updateEmployeeLink(@RequestParam int id, @RequestParam String name, @RequestParam String address, @RequestParam String phone, @RequestParam double salary, Model data) {

		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();

		try {
			Transaction tx = null;
			tx = session.beginTransaction();


			Employee updateEmployee = session.get(Employee.class, id);
			updateEmployee.setEmployeeName(name);
			updateEmployee.setEmployeeAddress(address);
			updateEmployee.setEmployeePhone(phone);
			updateEmployee.setEmployeeSalary(salary);
			session.update(updateEmployee);
			
			tx.commit();
			
			Query q1 = session.createQuery("from Employee");
			List<Employee> employees = q1.getResultList();
			data.addAttribute("employees", employees);
		}
		catch (Exception e) {
			System.out.println("Hibernate Error: " + e.getMessage());
		}

		return "show-employee";		

	}
	
	@GetMapping("/delete-employee-con")
	public String deleteEmployeeLink(@RequestParam int id, Model data) {
		
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();

		try {
			Transaction tx = null;
			tx = session.beginTransaction();


			Employee DeleteEmployee = session.get(Employee.class, id);
			session.delete(DeleteEmployee);
			
			tx.commit();
			
			Query q1 = session.createQuery("from Employee");
			List<Employee> employees = q1.getResultList();
			data.addAttribute("employees", employees);
		}
		catch (Exception e) {
			System.out.println("Hibernate Error: " + e.getMessage());
		}

		return "show-employee";	
		
	}

}
