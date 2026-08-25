package com.banking.repository;
import com.banking.entity.BankTransaction; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface TransactionRepository extends JpaRepository<BankTransaction,Long>{List<BankTransaction> findTop50ByAccountIdOrderByCreatedAtDesc(Long accountId);}
