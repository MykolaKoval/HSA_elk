package com.course.hsa.repository;

import com.course.hsa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> getByName(String name);

    long countByName(String name);

    long countByBirthDateBetween(Date from, Date to);
}
