package com.camping.jpa.kakao.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.camping.jpa.kakao.model.vo.PaymentApprove;

public interface PaymentApproveRepository extends JpaRepository<PaymentApprove, String>  {
	PaymentApprove findByAid(String aid);
}
