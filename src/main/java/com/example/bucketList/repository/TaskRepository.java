package com.example.bucketList.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bucketList.entity.Task;

public interface TaskRepository extends JpaRepository<Task, String> {
	public Optional<Task> findById(String id);

}
