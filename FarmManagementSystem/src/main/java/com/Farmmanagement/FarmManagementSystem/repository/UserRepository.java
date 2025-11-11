package com.Farmmanagement.FarmManagementSystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Farmmanagement.FarmManagementSystem.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAll(Pageable pageable);


// required endpoint: get users by province code or province name
// We'll implement derived queries that join through location.
@Query("SELECT u FROM User u WHERE u.location.code = :code")
List<User> findByLocationCode(@Param("code") String code);


@Query("SELECT u FROM User u WHERE u.location.name = :name")
List<User> findByLocationProvinceName(@Param("name") String name);


boolean existsByEmail(String email);
    
}
