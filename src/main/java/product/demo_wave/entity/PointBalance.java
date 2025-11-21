package product.demo_wave.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * ユーザーのポイント残高を管理するエンティティ
 */
@Entity
@Table(name = "point_balances")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointBalance {

	@Id
	@Column(name = "user_id", columnDefinition = "UUID")
	private UUID userId;

	@OneToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;

	@Column(name = "balance", nullable = false)
	@Builder.Default
	private Integer balance = 0;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	/**
	 * ポイントを加算する
	 */
	public void addPoints(Integer points) {
		if (points < 0) {
			throw new IllegalArgumentException("加算ポイントは0以上である必要があります");
		}
		this.balance += points;
	}

	/**
	 * ポイントを減算する
	 */
	public void subtractPoints(Integer points) {
		if (points < 0) {
			throw new IllegalArgumentException("減算ポイントは0以上である必要があります");
		}
		if (this.balance < points) {
			throw new IllegalStateException("ポイント残高が不足しています");
		}
		this.balance -= points;
	}
}
