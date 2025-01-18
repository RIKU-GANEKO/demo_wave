package product.demo_wave.logic;

import org.springframework.data.domain.Page;

public interface Pagination {

	public default <A> int getPageDisplayRange(Page<A> page) {
		int pageDisplayRange = 4;

		if (page.getTotalPages() < 5) {
			pageDisplayRange = page.getTotalPages() - 1;
		}

		return pageDisplayRange;
	}

}
