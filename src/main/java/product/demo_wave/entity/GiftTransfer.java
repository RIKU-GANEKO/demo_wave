package product.demo_wave.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

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
 * ギフトカード送金履歴エンティティ
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "gift_transfers")
@SQLDelete(sql = "UPDATE gift_transfers SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftTransfer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "transfer_month", nullable = false)
	private LocalDate transferMonth;  // 対象月（月初日を記録）

	@Column(name = "total_amount", nullable = false)
	private BigDecimal totalAmount;  // 送金額

	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createdBy;  // CSV出力を実行した管理者

	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;
}
