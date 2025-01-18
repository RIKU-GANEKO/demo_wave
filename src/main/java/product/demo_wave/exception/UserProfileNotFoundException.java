package product.demo_wave.exception;

public class UserProfileNotFoundException extends RuntimeException{

	public UserProfileNotFoundException(String message) {
		super(message);
	}
}
