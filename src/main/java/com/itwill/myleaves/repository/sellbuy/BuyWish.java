package com.itwill.myleaves.repository.sellbuy;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 정지언
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
@Entity
@Data
@IdClass(BuyWishId.class)
@Table(name = "WISH_SELL")
public class BuyWish {
	
	@Id
	@Column(name = "user_id")
	private String userId;
	
	@Id
	@Column(name = "sell_id")
	private Long sellId;

}
