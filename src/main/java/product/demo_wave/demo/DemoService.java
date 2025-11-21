package product.demo_wave.demo;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
class DemoService {
	private final DemoFacadeDBLogic demoFacadeDBLogic;

	ModelAndView showByGet(DemoShowGetContext demoShowGetContext) throws DataAccessException {
		// runtime exceptionは何が出るかわからないから、
		demoShowGetContext.setDemoFacadeDBLogic(demoFacadeDBLogic);

		demoShowGetContext.fetchDemo();
		demoShowGetContext.fetchComment();
		demoShowGetContext.fetchIsParticipant();
		demoShowGetContext.fetchParticipantCount();
		demoShowGetContext.fetchDonateAmount();
		demoShowGetContext.fetchTotalPoints();
		demoShowGetContext.fetchParticipants();
		demoShowGetContext.fetchSupporters();
		demoShowGetContext.setModelAndView();
		return demoShowGetContext.getMv();
	}

	ModelAndView createByGet(DemoCreateGetContext demoCreateGetContext) {
		demoCreateGetContext.setDemoFacadeDBLogic(demoFacadeDBLogic);
		demoCreateGetContext.setModelAndView();
		return demoCreateGetContext.getModelAndView();
	}

	ModelAndView createByPost(DemoCreatePostContext demoCreatePostContext) {
		demoCreatePostContext.setDemoFacadeDBLogic(demoFacadeDBLogic);

		// 確認画面から戻る場合は、フォームデータを保持して作成画面を再表示
		if (demoCreatePostContext.isBackFromConfirm()) {
			demoCreatePostContext.setBackModelAndView();
			return demoCreatePostContext.getModelAndView();
		}

		if (demoCreatePostContext.hasErrors()) {
			demoCreatePostContext.setErrorModelAndView();
			return demoCreatePostContext.getModelAndView();
		}

		demoCreatePostContext.setModelAndView();
		return demoCreatePostContext.getModelAndView();
	}

	ModelAndView createConfirmByGet(
			DemoCreateConfirmGetContext demoCreateConfirmGetContext) {
		demoCreateConfirmGetContext.setDemoFacadeDBLogic(demoFacadeDBLogic);
		demoCreateConfirmGetContext.setModelAndView();
		return demoCreateConfirmGetContext.getModelAndView();
	}

	ModelAndView createConfirmByPost(
			DemoCreateConfirmPostContext demoCreateConfirmPostContext) {
		demoCreateConfirmPostContext.setDemoFacadeDBLogic(demoFacadeDBLogic);

		demoCreateConfirmPostContext.saveDemo();
		demoCreateConfirmPostContext.setModelAndView();
		return demoCreateConfirmPostContext.getModelAndView();
	}

	ModelAndView createCompleteByGet(
			DemoCreateCompleteGetContext demoCreateCompleteGetContext) {
		demoCreateCompleteGetContext.setDemoFacadeDBLogic(demoFacadeDBLogic);
		demoCreateCompleteGetContext.setModelAndView();
		return demoCreateCompleteGetContext.getModelAndView();
	}

	ModelAndView commentCreateByPost(CommentCreatePostContext commentCreatePostContext) {
		commentCreatePostContext.setDemoFacadeDBLogic(demoFacadeDBLogic);

		commentCreatePostContext.saveComment();
		commentCreatePostContext.setModelAndView();
		return commentCreatePostContext.getModelAndView();
	}

	ModelAndView createByPost(ParticipantAddPostContext participantAddPostContext) {
		participantAddPostContext.setDemoFacadeDBLogic(demoFacadeDBLogic);

		participantAddPostContext.toggleParticipant();

		participantAddPostContext.setModelAndView();
		return participantAddPostContext.getModelAndView();
	}

	ModelAndView editByGet(DemoEditGetContext demoEditGetContext) {
		demoEditGetContext.setDemoFacadeDBLogic(demoFacadeDBLogic);
		demoEditGetContext.setModelAndView();
		return demoEditGetContext.getModelAndView();
	}

	ModelAndView editByPost(DemoEditPostContext demoEditPostContext) {
		demoEditPostContext.setDemoFacadeDBLogic(demoFacadeDBLogic);

		// 確認画面から戻る場合は、フォームデータを保持して編集画面を再表示
		if (demoEditPostContext.isBackFromConfirm()) {
			demoEditPostContext.setBackModelAndView();
			return demoEditPostContext.getModelAndView();
		}

		if (demoEditPostContext.hasErrors()) {
			demoEditPostContext.setErrorModelAndView();
			return demoEditPostContext.getModelAndView();
		}

		demoEditPostContext.setModelAndView();
		return demoEditPostContext.getModelAndView();
	}

	ModelAndView editConfirmByGet(DemoEditConfirmGetContext demoEditConfirmGetContext) {
		demoEditConfirmGetContext.setDemoFacadeDBLogic(demoFacadeDBLogic);
		demoEditConfirmGetContext.setModelAndView();
		return demoEditConfirmGetContext.getModelAndView();
	}

	ModelAndView editConfirmByPost(DemoEditConfirmPostContext demoEditConfirmPostContext) {
		demoEditConfirmPostContext.setDemoFacadeDBLogic(demoFacadeDBLogic);

		demoEditConfirmPostContext.updateDemo();
		demoEditConfirmPostContext.setModelAndView();
		return demoEditConfirmPostContext.getModelAndView();
	}

}