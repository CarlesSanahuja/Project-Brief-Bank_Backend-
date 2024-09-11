package com.brief.repository;

import com.brief.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckingRepository extends JpaRepository<Account,Long> {
}
