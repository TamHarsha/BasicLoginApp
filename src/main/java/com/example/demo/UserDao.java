package com.example.demo;

import com.example.demo.data.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserData,Integer> {

    UserData findByUserName(String name);
}
