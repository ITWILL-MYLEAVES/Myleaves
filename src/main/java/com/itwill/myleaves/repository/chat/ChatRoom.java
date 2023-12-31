package com.itwill.myleaves.repository.chat;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@ToString
@Data
@IdClass(ChatRoomId.class)
@Table(name = "CHAT_ROOM")
@SequenceGenerator(name = "CHAT_ROOM_SEQ_GEN", sequenceName = "CHAT_ROOM_SEQ", allocationSize = 1)
public class ChatRoom {
// 정지언
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHAT_ROOM_SEQ_GEN")
	@Column(name = "ROOM_ID")
	private Long roomId;
	
	@Id
    @Column(name = "SELL_ID")
    private Long sellId;
    
    @Column(name = "MY_ID")
    private String myId;
	
    @Id
    @Column(name = "OTHER_ID")
    private String otherId;
    
    
    public static ChatRoom create(long sellId, String myId, String otherId) {
	    return ChatRoom.builder()
	    		.sellId(sellId)
	    		.myId(myId)
	    		.otherId(otherId)
	    		.build();
    }

}
