package com.oopCw.EventTicketingSystem.repository;

import com.oopCw.EventTicketingSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends JpaRepository<User,Long> {


}
