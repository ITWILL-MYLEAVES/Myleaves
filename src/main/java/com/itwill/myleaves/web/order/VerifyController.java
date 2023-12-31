package com.itwill.myleaves.web.order;

import java.io.IOException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 수빈
//Iamport 결제 검증 컨트롤러
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/verifyIamport")
public class VerifyController {
	
	private final IamportClient iamportClient;
	
	
	// 생성자를 통해 REST API와 REST API secret 입력
	public VerifyController() {
		this.iamportClient = new IamportClient("4031565674362421"
				, "AKNNZLAFwqlPWl02pqZ7TqOfCaoX1PsBiMBsEdxNPFwJFiw2lycrbrTONJZU5X1W40Cu7OqHGj3bzH8j");
		
	}
	
	@PostMapping("/{imp_uid}")
	public IamportResponse<Payment> paymentByImpUid(@PathVariable String imp_uid) 
			throws IamportResponseException, IOException {
		
		log.info("paymentByImpUid 진입");
		return iamportClient.paymentByImpUid(imp_uid);
	}
	
	
	
	
}
