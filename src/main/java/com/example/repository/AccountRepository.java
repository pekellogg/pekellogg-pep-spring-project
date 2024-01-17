package com.example.repository;
import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * NamedQueries are a powerful feature of JPA repositorties that allow a developer to create a query using naming
 * conventions based on the name of fields in a class and the class name itself.

 * There are two important notes to keep in mind here. The return type and name of the method is important when
 * using named queries. For the first method, we are locating a single person object, so the name is "FindPerson..."
 * and the return type matches this expectation by returning a single Person object. We are locating this person
 * using the field "Name", so the full method name: "findPersonByName" follows a naming convention that JpaRepository
 * expects.

 * For the second method, notice that the proper way to say we want to find multiple persons would be to name
 * the method "FindPeopleByAge", however, the class is 'Person' not 'People'. The most important thing to keep in
 * mind when working with named queries are to match the class and field names.
 * **/

// Account class fields:
//     account_id int primary key auto_increment,
//     username varchar(255) not null unique,
//     password varchar(255)
// }

// Message class fields:
//     message_id int primary key auto_increment,
//     posted_by int,
//     message_text varchar(255),
//     time_posted_epoch bigint,
//     foreign key (posted_by) references  account(account_id)

/**
 * JPA Repository interface for the Account entity
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
}