package com.igmasiri.fitnet.authorizationserver.repository;

import com.igmasiri.fitnet.authorizationserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

}


