package product.demo_wave.api.commentList;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CommentListException extends RuntimeException {

	private final String error;
	private final HttpStatus httpStatus;

	public CommentListException(String error, String message, HttpStatus httpStatus) {
		super(message);
		this.error = error;
		this.httpStatus = httpStatus;
	}

}
