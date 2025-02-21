package product.demo_wave.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "payment")
@SQLDelete(sql = "UPDATE payment SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at is null")
@Data
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "demo_id")
  private Demo demo;

  @ManyToOne
  @JoinColumn(name = "donate_user_id")
  private User user;

  @Column(name = "donate_amount")
  private BigDecimal donateAmount;

  @CreatedDate
  @Column
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column
  private LocalDateTime updatedAt;

  @Column
  private LocalDateTime deletedAt;

}
