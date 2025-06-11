package product.demo_wave.api.commentList;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import product.demo_wave.repository.CommentRepository;

@Component
@AllArgsConstructor
public class CommentListDBLogic {

//	private static final Logger logger = Logger.getLogger(DemoListDBLogic.class.getSimpleName());

	private final CommentRepository commentRepository;

	/**
	 * コメント一覧情報のレスポンスを生成します。
	 *
	 * このメソッドは、削除されたユーザーのデータを返しません。
	 * リクエストに存在しないobjが含まれている場合、そのIDに対応するユーザー情報を返しません。
	 *
	 * @return デモ一覧情報のレスポンスを含むリスト
	 */
	List<CommentListRecord> fetchCommentList(Integer demoId) {

		List<CommentListRecord> responses = commentRepository.getByDemoId(demoId);
		return responses;
	}

}
