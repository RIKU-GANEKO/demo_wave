package product.demo_wave.api.commentList;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentListResponse implements APIResponse {

	@JsonProperty("commentList")
	private List<CommentListRecord> commentList;

	public CommentListResponse(List<CommentListRecord> commentList) {
		this.commentList = commentList;
	}

}
