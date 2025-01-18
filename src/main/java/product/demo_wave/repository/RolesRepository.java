package product.demo_wave.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import product.demo_wave.entity.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {

  Roles findByRole(String role);

}
