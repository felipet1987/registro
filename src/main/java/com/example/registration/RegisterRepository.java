package com.example.registration;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RegisterRepository extends CrudRepository<UserDAO, UUID> {
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN 'TRUE' ELSE 'FALSE' END FROM UserDAO u WHERE u.email = ?1")
    boolean existsByEmail(String email);
}