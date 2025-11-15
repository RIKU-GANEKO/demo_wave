package product.demo_wave.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString(exclude = {"userRole"})
@EqualsAndHashCode(exclude = {"userRole"})
public class Roles {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "role")
  private String role;

  @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
  private List<UserRole> userRole  = new ArrayList<>();

  public List<User> getUsers() {
    return userRole.stream().map(UserRole::getUser).collect(Collectors.toList());
  }
}
