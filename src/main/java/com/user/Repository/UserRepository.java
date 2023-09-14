package com.user.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.user.Model.User;

public interface UserRepository extends MongoRepository<User, String>{

}
