package product.demo_wave.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import product.demo_wave.api.commentList.CommentListRecord;
import product.demo_wave.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

  List<Comment> findByDemoId(Integer demoId);

  @Query("""
    SELECT new product.demo_wave.api.commentList.CommentListRecord(
        c.id, 
        c.demo.id, 
        c.content, 
        c.user.name,
        c.user.profileImagePath, 
        c.createdAt
    ) 
    FROM Comment c 
    WHERE c.demo.id = :demoId 
      AND c.deletedAt IS NULL 
    ORDER BY c.createdAt DESC
  """)
  List<CommentListRecord> getByDemoId(@Param("demoId") Integer demoId);

}
