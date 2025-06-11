package product.demo_wave.api.commentList;

import com.fasterxml.jackson.annotation.JsonProperty;

import product.demo_wave.common.api.APIResponse;
import product.demo_wave.entity.Comment;

public class CommentCreateResponse implements APIResponse {

	@JsonProperty("comment")
	private Comment comment;

	public CommentCreateResponse(Comment comment) {
		this.comment = comment;
	}

}
