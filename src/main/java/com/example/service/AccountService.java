package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
     * @return saved account
     */
    public Account save(Account account) {
        if (accountRepository.findAccountByUsername(account.getUsername()) == null) {
            accountRepository.save(account);
            return account;
        }
        return null;
    }

    /**
     * @param id: account entity's id
     * @param replacement: account entity with updated field values
     * @return the updated account entity
     */
    public Account update(Integer id, Account replacement) {
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

    public void delete(Integer id) {
        accountRepository.deleteById(id);
    }

    /**
     * @param transient account
     * @return found account entity
     */
    public Account login(Account account) {
        return accountRepository.findAccountByUsernameAndPassword(
            account.getUsername(),
            account.getPassword()
        );
    }
}