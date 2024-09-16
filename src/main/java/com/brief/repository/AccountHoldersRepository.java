package com.brief.repository;

import com.brief.model.AccountHolders;
import com.brief.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountHoldersRepository extends JpaRepository<AccountHolders,Long> {
}
