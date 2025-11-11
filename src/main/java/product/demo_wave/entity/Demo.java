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
@Table(name = "demo")
@SQLDelete(sql = "UPDATE demo SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at is null")
@Data
public class Demo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "title")
  private String title;

  @Column(name = "content")
  private String content;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @Column(name = "demo_start_date")
  private LocalDateTime demoStartDate;

  @Column(name = "demo_end_date")
  private LocalDateTime demoEndDate;

  @Column(name = "demo_place")
  private String demoPlace;

  @ManyToOne
  @JoinColumn(name = "prefecture_id")
  private Prefecture prefecture;

  @Column(name = "demo_address_latitude")
  private BigDecimal demoAddressLatitude;

  @Column(name = "demo_address_longitude")
  private BigDecimal demoAddressLongitude;

  @ManyToOne
  @JoinColumn(name = "organizer_user_id")
  private User user;

  @Column(name = "activity_report_url")
  private String activityReportUrl;

  @CreatedDate
  @Column
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column
  private LocalDateTime updatedAt;

  @Column
  private LocalDateTime deletedAt;

}
