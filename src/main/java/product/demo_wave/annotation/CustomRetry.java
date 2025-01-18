package product.demo_wave.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.NoSuchElementException;

import org.springframework.dao.DataAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retryable(retryFor = { NoSuchElementException.class, DataAccessException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
public @interface CustomRetry {

}
