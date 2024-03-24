package com.ws.startupProject.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByActivationToken(String activationToken);

    Page<User> findByIdNot(long id, Pageable pageable);
    Page<User> findById(long id, Pageable pageable);
}
