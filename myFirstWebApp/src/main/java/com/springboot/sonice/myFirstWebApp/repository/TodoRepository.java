package com.springboot.sonice.myFirstWebApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.sonice.myFirstWebApp.todo.Todo;


@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer>{

	public List<Todo> findByUserName(String userName);
}
