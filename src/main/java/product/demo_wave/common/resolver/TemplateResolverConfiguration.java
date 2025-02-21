package product.demo_wave.common.resolver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
public class TemplateResolverConfiguration {

  @Bean
  public SpringResourceTemplateResolver contactTemplateResolver() {
    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
    templateResolver.setPrefix("classpath:/templates/contact/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setCharacterEncoding("UTF-8");
    templateResolver.setOrder(0);
    templateResolver.setCheckExistence(true);

    return templateResolver;
  }

  @Bean
  public SpringResourceTemplateResolver demoTemplateResolver() {
    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
    templateResolver.setPrefix("classpath:/templates/demo/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setCharacterEncoding("UTF-8");
    templateResolver.setOrder(1);
    templateResolver.setCheckExistence(true);

    return templateResolver;
  }

  @Bean
  public SpringResourceTemplateResolver loginTemplateResolver() {
    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
    templateResolver.setPrefix("classpath:/templates/login/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setCharacterEncoding("UTF-8");
    templateResolver.setOrder(2);
    templateResolver.setCheckExistence(true);

    return templateResolver;
  }

  @Bean
  public SpringResourceTemplateResolver passwordTemplateResolver() {
    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
    templateResolver.setPrefix("classpath:/templates/password/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setCharacterEncoding("UTF-8");
    templateResolver.setOrder(3);
    templateResolver.setCheckExistence(true);

    return templateResolver;
  }

  @Bean
  public SpringResourceTemplateResolver profileTemplateResolver() {
    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
    templateResolver.setPrefix("classpath:/templates/profile/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setCharacterEncoding("UTF-8");
    templateResolver.setOrder(4);
    templateResolver.setCheckExistence(true);

    return templateResolver;
  }

  @Bean
  public SpringResourceTemplateResolver reportTemplateResolver() {
    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
    templateResolver.setPrefix("classpath:/templates/analysis/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setCharacterEncoding("UTF-8");
    templateResolver.setOrder(5);
    templateResolver.setCheckExistence(true);

    return templateResolver;
  }

  @Bean
  public SpringResourceTemplateResolver userTemplateResolver() {
    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
    templateResolver.setPrefix("classpath:/templates/user/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setCharacterEncoding("UTF-8");
    templateResolver.setOrder(6);
    templateResolver.setCheckExistence(true);

    return templateResolver;
  }

  @Bean
  public SpringResourceTemplateResolver paymentTemplateResolver() {
    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
    templateResolver.setPrefix("classpath:/templates/payment/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setCharacterEncoding("UTF-8");
    templateResolver.setOrder(7);
    templateResolver.setCheckExistence(true);

    return templateResolver;
  }

  @Bean
  public SpringResourceTemplateResolver mypageTemplateResolver() {
    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
    templateResolver.setPrefix("classpath:/templates/mypage/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setCharacterEncoding("UTF-8");
    templateResolver.setOrder(8);
    templateResolver.setCheckExistence(true);

    return templateResolver;
  }

}
