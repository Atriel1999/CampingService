package com.camping.jpa.kakao;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.camping.jpa.kakao.model.repository.PaymentApproveRepository;
import com.camping.jpa.kakao.model.repository.PaymentRequestRepository;
import com.camping.jpa.kakao.model.vo.PaymentRequest;
import com.camping.jpa.kakao.model.vo.PaymentApprove;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class KakaoPayService {
	@Autowired
	private final PaymentRequestRepository repo1;
	private final PaymentApproveRepository repo2;
	
	private static final String HOST = "https://open-api.kakaopay.com/online";
	
	@Value("${kakao.kakaopay.secretKeyDev}")
	 private String secretKey;
											

	private KakaoPayReadyVO kakaoPayReadyVO;
	private KakaoPayApprovalVO kakaoPayApprovalVO;

	public String kakaoPayReady(Map<String, String> param) {

		RestTemplate restTemplate = new RestTemplate();

		// 서버로 요청할 Header
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "SECRET_KEY " + secretKey);
		headers.add("Content-type", "application/json");
//		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		
		// 서버로 요청할 Body
		Map<String, String> params = new HashMap<String, String>();
		params.put("cid", "TC0ONETIME");
		params.put("partner_order_id", "1002");
		params.put("partner_user_id", "Atriel");
		params.put("item_name", param.get("item_name"));
		params.put("quantity", param.get("quantity"));
		params.put("total_amount", param.get("total_amount"));
		params.put("tax_free_amount", "500");
		params.put("approval_url", "http://localhost/kakao/kakaoPaySuccess");
		params.put("cancel_url", "http://localhost/kakao/kakaoPayCancel");
		params.put("fail_url", "http://localhost/kakao/kakaoPaySuccessFail");
		params.put("msg", "결제가 완료되었습니다.");
		
		log.info("dbg12345: " + param);
		PaymentRequest tempData = new PaymentRequest();
		tempData.setUserno(param.get("userno"));
		tempData.setSiteid(Integer.parseInt(param.get("siteid")));
		tempData.setCid(Integer.parseInt(param.get("cid")));

		HttpEntity<Map<String, String>> body = new HttpEntity<>(params, headers); //new HttpEntity<Map<String, String>>(params, headers);
		System.out.println("dbg1 body: " + body);
		try {
			kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body,
					KakaoPayReadyVO.class);

			log.info("dbg2: " + kakaoPayReadyVO);
			tempData.setTid(kakaoPayReadyVO.getTid());
			tempData.setCreated_at(kakaoPayReadyVO.getCreated_at());
			repo1.save(tempData);
			
			return kakaoPayReadyVO.getNext_redirect_pc_url();

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return "/";
	}

	public KakaoPayApprovalVO kakaoPayInfo(String pg_token, Map<String, String> param) {
		log.info("KakaoPayInfoVO............................................");
		log.info("-----------------------------");

		RestTemplate restTemplate = new RestTemplate();
		PaymentApprove tempData = new PaymentApprove();

		// 서버로 요청할 Header
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "SECRET_KEY " + secretKey);
		headers.add("Content-type", "application/json");
//		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
//		headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

		// 서버로 요청할 Body
		Map<String, String> params = new HashMap<String, String>();
		params.put("cid", "TC0ONETIME");
		params.put("tid", kakaoPayReadyVO.getTid());
		params.put("partner_order_id", "1002");
		params.put("partner_user_id", "Atriel");
		params.put("pg_token", pg_token);
		params.put("total_amount", param.get("total_amount"));

		HttpEntity<Map<String, String>> body = new HttpEntity<>(params, headers);

		try {
			kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body,
					KakaoPayApprovalVO.class);
			log.info("dbg3: " + kakaoPayApprovalVO);
			
			AmountVO tempVO = kakaoPayApprovalVO.getAmount();
			
			tempData.setAid(kakaoPayApprovalVO.getAid());
			tempData.setTid(kakaoPayApprovalVO.getTid());
			tempData.setApprove_at(kakaoPayApprovalVO.getApproved_at());
			tempData.setTotal(tempVO.getTotal());
			tempData.setTax_free(tempVO.getTax_free());
			tempData.setVat(tempVO.getVat());
			
			repo2.save(tempData);
			
			return kakaoPayApprovalVO;

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
}

