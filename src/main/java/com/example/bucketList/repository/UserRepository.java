package com.example.bucketList.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bucketList.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

	User findByUsername(String username);

}
