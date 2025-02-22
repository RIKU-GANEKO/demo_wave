package product.demo_wave.api.commentList;

import java.time.LocalDateTime;

public record CommentListRecord(
		Integer id, // comment.id
		Integer demoId, // comment.demo.id
		String content, // comment.content
		String writerName, // comment.user.name
		LocalDateTime createdAt // comment.createdAt
) {

}
