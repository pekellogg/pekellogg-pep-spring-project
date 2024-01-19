package com.example.repository;
import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository interface for the Account entity
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {
    /**
     * @param id
     * @return instance of Account entity having id
     */
    Account findAccountById(Integer id);

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