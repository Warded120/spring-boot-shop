package com.ivan.usercrud.repo;

import com.ivan.usercrud.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepository extends JpaRepository<UserData, Integer> {
}
