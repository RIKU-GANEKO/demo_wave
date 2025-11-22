package product.demo_wave.contact;

import lombok.Data;

/**
 * お問い合わせフォームのDTO
 */
@Data
public class ContactForm {
    private String name;
    private String email;
    private String subject;
    private String message;
}
