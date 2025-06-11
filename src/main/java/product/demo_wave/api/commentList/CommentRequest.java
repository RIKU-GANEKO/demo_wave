package product.demo_wave.api.commentList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class CommentRequest {
	private final Integer demoId;
	private final String content;

	@JsonCreator
	public CommentRequest(@JsonProperty("demoId") Integer demoId, @JsonProperty("content") String content) {
		this.demoId = demoId;
		this.content = content;
	}
}