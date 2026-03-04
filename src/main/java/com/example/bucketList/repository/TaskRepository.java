package com.example.bucketList.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bucketList.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {


	long countByCompletedTrue();

}
