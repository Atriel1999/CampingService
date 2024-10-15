package com.camping.jpa.kakao.model.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "PAYMENT_APPROVE")
@Transactional
public class PaymentApprove {
	@Id
	@Column(name="AID")				
	public String aid;
	@Column(name="TID")		
	public String tid;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	@Column(name="APPROVE_AT")			
	public Date approve_at;
	@Column(name="TOTAL")			
	public int total;
	@Column(name="TAX_FREE")			
	public int tax_free;
	@Column(name="VAT")			
	public int vat;
}