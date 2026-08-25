package com.banking.repository;
import com.banking.entity.Account; import jakarta.persistence.LockModeType; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import java.util.Optional;
public interface AccountRepository extends JpaRepository<Account,Long>{Optional<Account> findByUserId(Long userId); @Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select a from Account a where a.accountNumber=:number") Optional<Account> findByAccountNumberForUpdate(@Param("number") String number);}
