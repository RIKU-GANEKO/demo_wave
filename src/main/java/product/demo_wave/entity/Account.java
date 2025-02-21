package product.demo_wave.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "account")
@SQLDelete(sql = "UPDATE account SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at is null")
@Data
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

//  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
//  private List<User> userList = new ArrayList<>();

//  @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, orphanRemoval = true)
//  private List<Media> mediaList;

  @Column(name = "name")
  private String name;

  @Column(name = "contract_status")
  private Integer contractStatus;

  @Column(name = "company_name")
  private String companyName;

  @Column(name = "zip")
  private String zip;

  @Column(name = "address")
  private String address;

  @Column(name = "tel")
  private String tel;

  @Column(name = "registered_business_number")
  private String registeredBusinessNumber;

  @Column(name = "bank_name")
  private String bankName;

  @Column(name = "bank_code")
  private String bankCode;

  @Column(name = "branch_name")
  private String branchName;

  @Column(name = "branch_code")
  private String branchCode;

  @Column(name = "account_type")
  private Integer accountType;

  @Column(name = "account_number")
  private String accountNumber;

  @Column(name = "account_holder")
  private String accountHolder;

  @Column(name = "ad_unit_id")
  private Integer adUnitId;

  @CreationTimestamp
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "deleted_at")
  private Date deletedAt;

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("Account [");
    sb.append("Name:").append(this.name).append(", ");
    sb.append("contractStatus:").append(this.contractStatus).append(", ");
    sb.append("companyName:").append(this.companyName).append(", ");
    sb.append("zip:").append(this.zip).append(", ");
    sb.append("address:").append(this.address).append(", ");
    sb.append("tel:").append(this.tel).append(", ");
    sb.append("registeredBusinessNumber:").append(this.registeredBusinessNumber).append(", ");
    sb.append("bankName:").append(this.bankName).append(", ");
    sb.append("bankCode:").append(this.bankCode).append(", ");
    sb.append("branchName:").append(this.bankName).append(", ");
    sb.append("branchCode:").append(this.branchCode).append(", ");
    sb.append("accountType:").append(this.accountType).append(", ");
    sb.append("accountNumber:").append(this.accountNumber).append(", ");
    sb.append("accountHolder:").append(this.accountHolder);
    sb.append("].");
    return sb.toString();
  }
}
