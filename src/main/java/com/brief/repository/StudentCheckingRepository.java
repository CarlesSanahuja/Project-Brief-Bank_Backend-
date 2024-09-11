package com.brief.repository;

import com.brief.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCheckingRepository extends JpaRepository<Account,Long> {
}
