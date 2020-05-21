package com.everywhere.dev.Repository;

import com.everywhere.dev.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<EverywhereUsers, UUID> {
    EverywhereUsers findByUsername(String userName);

    @Query(value = "Select userid from everywhere_users where username = ?1", nativeQuery = true)
    UUID findUserIdByUsername(String userName);
}
