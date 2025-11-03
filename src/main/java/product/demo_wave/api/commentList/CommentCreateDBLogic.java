package product.demo_wave.api.commentList;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import product.demo_wave.common.annotation.CustomRetry;
import product.demo_wave.entity.Comment;
import product.demo_wave.entity.Demo;
import product.demo_wave.entity.User;
import product.demo_wave.repository.CommentRepository;
import product.demo_wave.repository.DemoRepository;
import product.demo_wave.repository.UserRepository;

@Component
@AllArgsConstructor
public class CommentCreateDBLogic {

//	private static final Logger logger = Logger.getLogger(DemoListDBLogic.class.getSimpleName());

	private final CommentRepository commentRepository;
	private final DemoRepository demoRepository;
	private final UserRepository userRepository;


	/**
	 *
	 */
	@CustomRetry
	Comment saveComment(String supabaseUid, CommentRequest request) {
		Comment newComment = commentRepository.saveAndFlush(toEntity(supabaseUid, request));
		return newComment;
	}

	private Comment toEntity(String supabaseUid, CommentRequest request) {
		Comment comment = new Comment();

		comment.setDemo(fetchDemo(request.getDemoId()));
		comment.setContent(request.getContent());
		comment.setUser(fetchUser(supabaseUid));

		return comment;
	}

	@CustomRetry
	Demo fetchDemo(Integer demoId) {
		Optional<Demo> demo = demoRepository.findById(demoId);
		return demo.orElse(new Demo());
	}

	User fetchUser(String supabaseUid) {
		Optional<User> user = userRepository.findById(java.util.UUID.fromString(supabaseUid));
		return user.orElse(new User());
	}

}
