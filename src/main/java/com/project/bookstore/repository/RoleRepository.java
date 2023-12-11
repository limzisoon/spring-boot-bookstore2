package com.project.bookstore.repository;

import java.util.Optional;

import com.project.bookstore.models.ERole;
import com.project.bookstore.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
