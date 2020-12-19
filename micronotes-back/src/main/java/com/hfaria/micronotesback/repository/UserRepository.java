package com.hfaria.micronotesback.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hfaria.micronotesback.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	

	public Optional<User> findByEmail(String email);
}
