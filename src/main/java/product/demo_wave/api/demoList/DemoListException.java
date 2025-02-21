package product.demo_wave.api.demoList;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class DemoListException extends RuntimeException {

	private final String error;
	private final HttpStatus httpStatus;

	public DemoListException(String error, String message, HttpStatus httpStatus) {
		super(message);
		this.error = error;
		this.httpStatus = httpStatus;
	}

}
