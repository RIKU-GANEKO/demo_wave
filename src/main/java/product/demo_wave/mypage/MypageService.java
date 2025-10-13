package product.demo_wave.mypage;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
class MypageService {
	private final MypageFacadeDBLogic mypageFacadeDBLogic;

	ModelAndView rootByGet(MypageGetContext mypageGetContext) {
		mypageGetContext.setModelAndView();
		return mypageGetContext.getMv();
	}

	ModelAndView userByGet(MypageGetUserContext mypageGetUserContext) {
		mypageGetUserContext.setMypageFacadeDBLogic(mypageFacadeDBLogic);
		mypageGetUserContext.fetchUser();
		mypageGetUserContext.fetchParticipatedDemo();
		mypageGetUserContext.fetchFavoriteDemos();
		mypageGetUserContext.fetchSupportedDemos();
		mypageGetUserContext.fetchPostedDemos();
		mypageGetUserContext.setModelAndView();
		return mypageGetUserContext.getMv();
	}

//	ModelAndView showByGet(demoShowGetContext demoShowGetContext) throws DataAccessException {
//		// runtime exceptionは何が出るかわからないから、
//		demoShowGetContext.setdemoFacadeDBLogic(demoFacadeDBLogic);
//
//		demoShowGetContext.fetchdemo();
//		demoShowGetContext.fetchComment();
//		demoShowGetContext.fetchIsParticipant();
//		demoShowGetContext.setModelAndView();
//		return demoShowGetContext.getMv();
//		// TODO catchしてlogに吐き出すようにすべき
//		// throwsであるのは、エラーキャッチしたら、すぐ処理できるものを書く、うまくいかないならエラーページに飛ばすというのが多いが
//		// 処理しようがないことが多いが、dataaccessexcみたいなものは、処理できる段階で処理するのが良い
//	}
//
//	ModelAndView createByGet(demoCreateGetContext demoCreateGetContext) {
//		demoCreateGetContext.setModelAndView();
//		return demoCreateGetContext.getModelAndView();
//	}
//
//	ModelAndView createByPost(demoCreatePostContext demoCreatePostContext) {
//		if (demoCreatePostContext.hasErrors()) {
//			demoCreatePostContext.setErrorModelAndView();
//			return demoCreatePostContext.getModelAndView();
//		}
//
//		demoCreatePostContext.setModelAndView();
//		return demoCreatePostContext.getModelAndView();
//	}
//
//	ModelAndView createConfirmByGet(
//			demoCreateConfirmGetContext demoCreateConfirmGetContext) {
//		demoCreateConfirmGetContext.setModelAndView();
//		return demoCreateConfirmGetContext.getModelAndView();
//	}
//
//	ModelAndView createConfirmByPost(
//			demoCreateConfirmPostContext demoCreateConfirmPostContext) {
//		demoCreateConfirmPostContext.setdemoFacadeDBLogic(demoFacadeDBLogic);
//
//		demoCreateConfirmPostContext.savedemo();
//		demoCreateConfirmPostContext.setModelAndView();
//		return demoCreateConfirmPostContext.getModelAndView();
//	}
//
//	ModelAndView createCompleteByGet(
//			demoCreateCompleteGetContext demoCreateCompleteGetContext) {
//		demoCreateCompleteGetContext.setModelAndView();
//		return demoCreateCompleteGetContext.getModelAndView();
//	}
//
//	ModelAndView commentCreateByPost(CommentCreatePostContext commentCreatePostContext) {
//		commentCreatePostContext.setdemoFacadeDBLogic(demoFacadeDBLogic);
//
//		commentCreatePostContext.saveComment();
//		commentCreatePostContext.setModelAndView();
//		return commentCreatePostContext.getModelAndView();
//	}
//
//	ModelAndView createByPost(ParticipantAddPostContext participantAddPostContext) {
//		participantAddPostContext.setdemoFacadeDBLogic(demoFacadeDBLogic);
//
////		if (participantAddPostContext.hasErrors()) {
////			participantAddPostContext.setErrorModelAndView();
////			return participantAddPostContext.getModelAndView();
////		}
//
//		participantAddPostContext.toggleParticipant();
//
//		participantAddPostContext.setModelAndView();
//		return participantAddPostContext.getModelAndView();
//	}

}
