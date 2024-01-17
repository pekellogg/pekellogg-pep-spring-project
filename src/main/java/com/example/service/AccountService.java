package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * @param transient account
     * @return persisted account
     */
    public Account persistAccount(Account account) {
        return accountRepository.save(account);
    }

    /**
     * @return all account entities
     */
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    /**
     * @param account entity's id
     * @return account entity
     */
    public Account getAccountById(long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);

        if (optionalAccount.isPresent()) {
            return optionalAccount.get();
        } else {
            return null;
        }
    }

    public void deleteAccount(long id) {
        accountRepository.deleteById(id);
    }

    /**
     * @param id: account entity's id
     * @param replacement: account entity with updated field values
     * @return the updated account entity
     */
    public Account updateAccount(long id, Account replacement) {
        // findById returns a type Optional<Account>. This helps the developer avoid null pointer
        // exceptions. We can use the method .get() to convert an Optional<Account> to Account.
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setUsername(replacement.getUsername());
            account.setPassword(replacement.getPassword());
            accountRepository.save(account);
            return account;
        } else {
            return null;
        }
    }
}