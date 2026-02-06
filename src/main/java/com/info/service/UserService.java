package com.info.service;

import com.info.entity.User;
import com.info.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Cacheable(
            value = "users",        // cache name
            key = "#id"             // cache key
    )
    public User getUserById(String id) {
        System.out.println("Fetching user from MongoDB...");
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @CacheEvict(
            value = "users",
            key = "#id"
    )
    public User updateUser(String id, User user) {
        User existing = getUserById(id);
        existing.setName(user.getName());
        existing.setEmail(user.getEmail());
        existing.setAge(user.getAge());
        return userRepository.save(existing);
    }

    @CacheEvict(
            value = "users",
            key = "#id"
    )
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public List<User> findByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.find(query, User.class);
    }
}
