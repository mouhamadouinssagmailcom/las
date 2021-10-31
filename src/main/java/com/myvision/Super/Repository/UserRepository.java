package com.myvision.Super.Repository;

import com.myvision.Super.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository {
    User findByUsername(String username);

    User findByEmail(String email);

    User findById(long id);

}
