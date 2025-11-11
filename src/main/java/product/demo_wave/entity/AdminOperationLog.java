package product.demo_wave.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
 * 管理画面操作ログエンティティ
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "admin_operation_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminOperationLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "admin_user_id", nullable = false)
	private User adminUser;  // 操作した管理者

	@Column(name = "operation_type", nullable = false, length = 50)
	private String operationType;  // 操作種類（例: 'GIFT_CSV_EXPORT'）

	@Column(name = "target_month")
	private LocalDate targetMonth;  // 対象月

	@Column(name = "record_count")
	private Integer recordCount;  // 処理件数

	@Column(name = "total_amount")
	private BigDecimal totalAmount;  // 合計金額

	@Column(name = "success", nullable = false)
	@Builder.Default
	private Boolean success = true;  // 成功/失敗

	@Column(name = "error_message", columnDefinition = "TEXT")
	private String errorMessage;  // エラーメッセージ

	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdAt;
}
