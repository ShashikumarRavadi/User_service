package com.user.Service;

import java.util.List;

import com.user.Model.User;

public interface UserService {

	
	public User saveUser(User user);
	
	public List<User> getaAllUsers();
	
	public User getByID(String userId);
	
	public String deleteUSer(String userId);
	
}
