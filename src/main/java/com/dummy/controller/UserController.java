package com.dummy.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dummy.service.UserService;

@RestController
public class UserController {

	@GetMapping("/newuser")
	public ResponseEntity<String> CreateUser() {
		try {
			UserService.getInstance().createUser();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new ResponseEntity<String>("success", HttpStatus.CREATED);
	}

	@Scheduled(fixedDelay=50000)
	public void SchedulerForUpdateUser() {
		try { 
			System.out.println("Updating scheduler started at " + new Date());
			UserService.getInstance().updateUser(); 
			System.out.println("Updating scheduler ended at " + new Date());
		} catch (Exception e) { 
			throw new RuntimeException(e); 
		} 
	}

/*
 * public static void main (String [] args) throws Exception {
 * 
 * ExecutorService service = Executors.newFixedThreadPool(2);
 * 
 * Runnable createTask = new Runnable() {
 * 
 * @Override public void run() { try { UserService.getInstance().createUser(); }
 * catch (Exception e) { throw new RuntimeException(e); } } };
 * 
 * Runnable updateTask = new Runnable() {
 * 
 * @Override public void run() { try { UserService.getInstance().updateUser(); }
 * catch (Exception e) { throw new RuntimeException(e); } } };
 * 
 * 
 * service.submit(createTask); service.submit(updateTask); service.shutdown(); }
 */

}
