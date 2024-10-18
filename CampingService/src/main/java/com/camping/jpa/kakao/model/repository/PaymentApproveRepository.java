package com.camping.jpa.kakao.model.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;


import com.camping.jpa.kakao.model.vo.PaymentApprove;

public interface PaymentApproveRepository extends JpaRepository<PaymentApprove, String>  {
	PaymentApprove findByAid(String aid);
	
	List<PaymentApprove> findAllByOrderByAidDesc();
	
//	@Query("select e from PAYMENT_APPROVE e"
//			+"WHERE e.aid = :aid")
//	Page<PaymentApprove> findPaymentList(@Param("aid") String aid, Pageable pageable);
}
