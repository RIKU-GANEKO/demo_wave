package product.demo_wave.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at is null")
@Data
public class User {
  @Id
  @Column(name = "id", columnDefinition = "UUID")
  private UUID id;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;

  @Column(name = "profile_image_path")
  private String profileImagePath;

  @Column(name = "status")
  private Boolean status;

  @Column(name = "last_login")
  private LocalDateTime lastLogin;

  // Account関連は不要（削除済み）
  // Stripe + Amazonギフト券を使用するため、銀行口座情報は不要

  @CreationTimestamp
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "deleted_at")
  private Date deletedAt;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval=true, cascade = CascadeType.ALL)
  private List<UserRole> userRole = new ArrayList<>();

  public List<Roles> getRoles() {
    return userRole.stream().map(UserRole::getRole).collect(Collectors.toList());
  }

  public void setRoles(List<Roles> roles) {
    this.userRole = roles.stream().map(role -> {
      UserRole userRole = new UserRole();
      userRole.setUser(this);
      userRole.setRole(role);
      return userRole;
    }).collect(Collectors.toList());
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("User [");
    sb.append("id:").append(this.id).append(", ");
    sb.append("name:").append(this.name).append(", ");
    sb.append("email:").append(this.email).append(", ");
    sb.append("profileImagePath:").append(this.profileImagePath).append(", ");
    sb.append("status:").append(this.status);
    sb.append("]");
    return sb.toString();
  }
}
