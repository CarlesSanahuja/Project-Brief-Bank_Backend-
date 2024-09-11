package com.brief.repository;

import com.brief.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminsRepository extends JpaRepository<User,Long> {
}
