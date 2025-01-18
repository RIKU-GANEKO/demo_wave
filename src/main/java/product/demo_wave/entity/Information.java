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
import jakarta.persistence.Table;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "information")
@SQLDelete(sql = "UPDATE information SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at is null")
@Data
public class Information {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "title")
  private String title;

  @Column(name = "content")
  private String content;

  @Column(name = "demo_date")
  private LocalDateTime demoDate;

  @Column(name = "demo_place")
  private String demoPlace;

  @Column(name = "demo_address")
  private String demoAddress;

  @Column(name = "demo_address_latitude")
  private BigDecimal demoAddressLatitude;

  @Column(name = "demo_address_longitude")
  private BigDecimal demoAddressLongitude;

  @Column(name = "organizer_user_id")
  private Integer organizerUserId;

  @Column(name = "announcement_time")
  private LocalDateTime announcementTime;

  @CreatedDate
  @Column
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column
  private LocalDateTime updatedAt;

  @Column
  private LocalDateTime deletedAt;

}
