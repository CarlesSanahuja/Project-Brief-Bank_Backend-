package com.brief.repository;

import com.brief.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<Account,Long> {
}
