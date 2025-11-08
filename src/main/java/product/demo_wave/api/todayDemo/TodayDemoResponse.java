package product.demo_wave.api.todayDemo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import product.demo_wave.common.api.APIResponse;

/**
 * 当日参加予定デモ一覧のレスポンス
 */
@Data
@AllArgsConstructor
public class TodayDemoResponse implements APIResponse {

	private List<TodayDemoDTO> demos;
}
