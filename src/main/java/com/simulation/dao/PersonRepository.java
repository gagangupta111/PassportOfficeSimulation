package com.simulation.dao;

import com.simulation.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByName(String name);

    @Query( "select o from Person o where verificationStatus like %:progress%" )
    List<Person> findPersonsByProgress(@Param("progress") String progress);

    public List<Person> findAllByOrderByQueueAdditionDesc();

    public List<Person> findAllByOrderByTotalTimeDesc();

}
