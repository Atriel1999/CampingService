package com.camping.jpa.kakao.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.camping.jpa.kakao.model.vo.PaymentRequest;

public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, String>  {
	PaymentRequest findByTid(String tid);
}
