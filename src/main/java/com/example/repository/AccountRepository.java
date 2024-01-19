package com.example.repository;
import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository interface for the Account entity
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    /**
     * @param username
     * @return instance of Account entity having username
     */
    Account findAccountByUsername(String username);

    /**
     * @param username
     * @param password
     * @return instance of Account entity having username AND password
     */
    Account findAccountByUsernameAndPassword(String username, String password);
}