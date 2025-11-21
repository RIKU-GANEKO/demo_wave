package product.demo_wave.api.donation.webhook;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import product.demo_wave.common.api.APIResponse;
import product.demo_wave.common.api.ErrorCode;
import product.demo_wave.common.aws.SESService;

/**
 * ユーザ情報取得用 Service
 */
@Service
public class WebhookService {

	private final WebhookDBLogic webhookDBLogic;
	private final SESService sesService;
	private final product.demo_wave.service.PointService pointService;

	@Value("${stripe.webhook.secret}")
	private String webhookSecret;

	public WebhookService(WebhookDBLogic webhookDBLogic, SESService sesService, product.demo_wave.service.PointService pointService) {
		this.webhookDBLogic = webhookDBLogic;
		this.sesService = sesService;
		this.pointService = pointService;
	}

	ResponseEntity<APIResponse> handleEvent(WebhookContext webhookContext) {
		webhookContext.setWebhookDBLogic(webhookDBLogic);
		webhookContext.setSesService(sesService);
		webhookContext.setPointService(pointService);
		try {
			return webhookContext.handleEvent();
		}
		catch (Exception e) {
			return webhookContext.errorResponse(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), ErrorCode.INTERNAL_SERVER_ERROR.getDescription(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
