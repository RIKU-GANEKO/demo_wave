package product.demo_wave.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ギフト送金明細エンティティ（デモごとの受取金額）
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "gift_transfer_details")
@SQLDelete(sql = "UPDATE gift_transfer_details SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftTransferDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "gift_transfer_id", nullable = false)
	private GiftTransfer giftTransfer;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "demo_id", nullable = false)
	private Demo demo;

	@Column(name = "amount", nullable = false)
	private BigDecimal amount;

	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;
}
