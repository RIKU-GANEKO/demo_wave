package product.demo_wave.common.logic;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import lombok.AllArgsConstructor;
import lombok.Data;

public class BasicFacadeDBLogic implements Pagination {

  @Data
  @AllArgsConstructor
  public static class PageData<T> {

    private Page<T> page;
    private int range;
  }

  public static <A, B> Page<B> convert(Page<A> sourcePage, Function<A, B> converter) {
    List<B> convertedContent = sourcePage.getContent()
        .stream()
        .map(converter)
        .toList();

    return new PageImpl<>(
        convertedContent,
        PageRequest.of(sourcePage.getNumber(), sourcePage.getSize(), sourcePage.getSort()),
        sourcePage.getTotalElements());
  }

}
