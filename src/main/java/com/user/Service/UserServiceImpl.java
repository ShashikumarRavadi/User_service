package com.user.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.Exception.ResourceNotFoundException;
import com.user.Model.Hotel;
import com.user.Model.Rating;
import com.user.Model.User;
import com.user.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private RestTemplate template;

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User saveUser(User user) {
		String string = UUID.randomUUID().toString();
		user.setUserId(string);
		user.setCreatedDate(new Date());
		return repository.save(user);
	}

	@Override
	public List<User> getaAllUsers() {

		List<User> findAll = repository.findAll();
		
		for (User user : findAll) {
			
		
		Rating[] forObject = template.getForObject("http://RATING-SERVICE/rating/users/"+user.getUserId(), Rating[].class);
		logger.info("{}", forObject);
		List<Rating> ratings = Arrays.stream(forObject).toList();
		
//		http://localhost:8082/hotel/
		List<Rating> ratinglist= ratings.stream().map(rating->{
			ResponseEntity<Hotel> forEntity = template.getForEntity("http://HOTEL-SERVICE/hotel/"+rating.getHotelId(), Hotel.class);
			Hotel hotel = forEntity.getBody();
		rating.setHotel(hotel);
		return rating;
		}).collect(Collectors.toList());
		user.setRatings(ratings);

		}
		return findAll;
	}

	@Override
	public User getByID(String userId) {
		User user = repository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with given ID on server " + userId));
		Rating[] forObject = template.getForObject("http://RATING-SERVICE/rating/users/" + userId, Rating[].class);
		logger.info("{}", forObject);
		List<Rating> ratings = Arrays.stream(forObject).toList();
		
//		http://localhost:8082/hotel/
		List<Rating> ratinglist= ratings.stream().map(rating->{
			ResponseEntity<Hotel> forEntity = template.getForEntity("http://HOTEL-SERVICE/hotel/"+rating.getHotelId(), Hotel.class);
			Hotel hotel = forEntity.getBody();
		rating.setHotel(hotel);
		return rating;
		}).collect(Collectors.toList());
		user.setRatings(ratings);

		return user;
	}

	@Override
	public String deleteUSer(String userId) {
		repository.deleteById(userId);
		return null;
	}

}
