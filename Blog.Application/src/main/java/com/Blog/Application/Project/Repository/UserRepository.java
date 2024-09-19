package com.Blog.Application.Project.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Blog.Application.Project.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

//	User findByEmail(String email);
	Optional<User> findByEmail(String email);
}
