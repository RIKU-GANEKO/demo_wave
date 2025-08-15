package product.demo_wave.demo;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
class DemoService {
	private final DemoFacadeDBLogic demoFacadeDBLogic;

//	private final String YOUR_DOMAIN = "http://localhost:8082/demo_wave";

	ModelAndView rootByGet(DemoGetContext demoGetContext) {
		demoGetContext.setDemoFacadeDBLogic(demoFacadeDBLogic);
		demoGetContext.init();

		demoGetContext.getOrganizerUserName();
//		DemoGetContext.getParticipantCount();
		demoGetContext.fetchDemo();
		demoGetContext.setModelAndView();
		return demoGetContext.getMv();
	}

	ModelAndView showByGet(DemoShowGetContext demoShowGetContext) throws DataAccessException {
		// runtime exceptionは何が出るかわからないから、
		demoShowGetContext.setDemoFacadeDBLogic(demoFacadeDBLogic);

		demoShowGetContext.fetchDemo();
		demoShowGetContext.fetchComment();
		demoShowGetContext.fetchIsParticipant();
		demoShowGetContext.fetchParticipantCount();
		demoShowGetContext.fetchDonateAmount();
		demoShowGetContext.setModelAndView();
		return demoShowGetContext.getMv();
		// TODO catchしてlogに吐き出すようにすべき
		// throwsであるのは、エラーキャッチしたら、すぐ処理できるものを書く、うまくいかないならエラーページに飛ばすというのが多いが
		// 処理しようがないことが多いが、dataaccessexcみたいなものは、処理できる段階で処理するのが良い
	}

	ModelAndView createByGet(DemoCreateGetContext demoCreateGetContext) {
		demoCreateGetContext.setDemoFacadeDBLogic(demoFacadeDBLogic);
		demoCreateGetContext.setModelAndView();
		return demoCreateGetContext.getModelAndView();
	}

	ModelAndView createByPost(DemoCreatePostContext demoCreatePostContext) {
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

//		if (participantAddPostContext.hasErrors()) {
//			participantAddPostContext.setErrorModelAndView();
//			return participantAddPostContext.getModelAndView();
//		}

		participantAddPostContext.toggleParticipant();

		participantAddPostContext.setModelAndView();
		return participantAddPostContext.getModelAndView();
	}

}