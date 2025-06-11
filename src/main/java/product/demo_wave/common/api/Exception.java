package product.demo_wave.common.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class Exception extends RuntimeException {

	private final String error;
	private final HttpStatus httpStatus;

	public Exception(String error, String message, HttpStatus httpStatus) {
		super(message);
		this.error = error;
		this.httpStatus = httpStatus;
	}

}
