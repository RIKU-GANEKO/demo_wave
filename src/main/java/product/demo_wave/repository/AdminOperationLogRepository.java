package product.demo_wave.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import product.demo_wave.entity.AdminOperationLog;

@Repository
public interface AdminOperationLogRepository extends JpaRepository<AdminOperationLog, Integer> {
	// 基本的なCRUD操作のみ
	// 必要に応じてカスタムクエリを追加
}
