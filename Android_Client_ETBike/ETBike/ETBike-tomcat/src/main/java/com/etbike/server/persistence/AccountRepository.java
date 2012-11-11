package com.etbike.server.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etbike.server.domain.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	Account findByUsername(String username);

}
