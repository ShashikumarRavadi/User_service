package com.user.Controller;

import java.util.Date;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.Model.User;
import com.user.Service.UserServiceImpl;

import ch.qos.logback.classic.Logger;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserServiceImpl service;

	private Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);

	@PostMapping()
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User saveUser = service.saveUser(user);
		logger.info("User Object ,{}", saveUser);
		return ResponseEntity.status(HttpStatus.CREATED).body(saveUser);
	}
	
	int count=1;
	
	
	@GetMapping("/{userId}")
//	@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallbackMethod")
//	@Retry(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallbackMethod")
	@RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallbackMethod")
	public ResponseEntity<User> getByID(@PathVariable String userId) {
		logger.info("Retry count {}", count);
		count++;
		User byID = service.getByID(userId);
		return ResponseEntity.ok(byID);
	}

	@GetMapping
	public ResponseEntity<List<User>> getAll() {
		List<User> getaAllUsers = service.getaAllUsers();
		return ResponseEntity.ok(getaAllUsers);
	}

	public ResponseEntity<User> ratingHotelFallbackMethod(String userId, Exception ex) {
		logger.info("Fallback is executed because service is down : ", ex.getMessage());
		User user = User.builder().email("dummyEmail.com").name("dummyName").about("This user is created by dummy because service is down").createdDate(new Date()).userId("abcd1234").build();
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

}
