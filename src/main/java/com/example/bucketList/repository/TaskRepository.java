package com.example.bucketList.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bucketList.entity.Task;
import com.example.bucketList.entity.User;

public interface TaskRepository extends JpaRepository<Task, Long> {

	long countByCompletedTrueAndUser(User user);

	long countByUser(User user);

	List<Task> findByUser(User user, Sort sort);

}
