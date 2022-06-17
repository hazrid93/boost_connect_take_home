package com.dummy.service;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import com.dummy.model.User;
import com.dummy.repository.UserRepostory;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserService {
	  private static volatile UserService userService = null;
	    private static Object lock = new Object();

	    public static UserService getInstance() {

	        if(userService == null ) {

	            synchronized (lock) {
	                if(userService == null ) {
	                	userService = new UserService();
	                }
	            }
	        }
	        return userService;

	    }

	    public void createUser() throws Exception {

	        URLConnection connection = new URL("https://dummyapi.io/data/v1/user?page=1&limit=10").openConnection();
	        connection.setRequestProperty("app-id", "62aae6c295a625da065b3a18");

	        InputStream inputStream = connection.getInputStream();
	        Scanner scann = new Scanner(inputStream).useDelimiter("\\A");

	        String result = scann.hasNext() ? scann.next() : "";

	        ObjectMapper objectMapper = new ObjectMapper();

	        User user = objectMapper.readValue(result, User.class);
	        UserRepostory.getInstance().insertUser(user);

	    }

	    public void updateUser() throws Exception {

	        URLConnection connection = new URL("https://dummyapi.io/data/v1/user?page=1&limit=10").openConnection();
	        connection.setRequestProperty("app-id", "62aae6c295a625da065b3a18");

	        InputStream inputStream = connection.getInputStream();
	        Scanner scann = new Scanner(inputStream).useDelimiter("\\A");

	        String result = scann.hasNext() ? scann.next() : "";

	        ObjectMapper objectMapper = new ObjectMapper();
	        User user = objectMapper.readValue(result, User.class);

	        UserRepostory.getInstance().updateUser(user);

	    }
}
