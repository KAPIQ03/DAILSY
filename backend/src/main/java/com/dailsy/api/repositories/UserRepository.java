package com.dailsy.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dailsy.api.models.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
