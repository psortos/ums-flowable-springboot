package com.flowable.flowableboot.repository;

import com.flowable.flowableboot.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {
  Person findByUsername(String username);
}
