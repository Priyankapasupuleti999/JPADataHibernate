package com.cg.mypaymentapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.mypaymentapp.beans.Transactions;

public interface TransactionRepo extends JpaRepository<Transactions, String> {

	@Query("SELECT t FROM Transactions t WHERE t.mobileNo =:mobileNo")
	public List<Transactions> findByMobileNo(@Param("mobileNo") String mobileno);

}
 