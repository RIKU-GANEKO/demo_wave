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
		mypageGetUserContext.fetchAccount();
		mypageGetUserContext.fetchParticipatedInformation();
		mypageGetUserContext.setModelAndView();
		return mypageGetUserContext.getMv();
	}

//	ModelAndView showByGet(InformationShowGetContext informationShowGetContext) throws DataAccessException {
//		// runtime exceptionは何が出るかわからないから、
//		informationShowGetContext.setInformationFacadeDBLogic(informationFacadeDBLogic);
//
//		informationShowGetContext.fetchInformation();
//		informationShowGetContext.fetchComment();
//		informationShowGetContext.fetchIsParticipant();
//		informationShowGetContext.setModelAndView();
//		return informationShowGetContext.getMv();
//		// TODO catchしてlogに吐き出すようにすべき
//		// throwsであるのは、エラーキャッチしたら、すぐ処理できるものを書く、うまくいかないならエラーページに飛ばすというのが多いが
//		// 処理しようがないことが多いが、dataaccessexcみたいなものは、処理できる段階で処理するのが良い
//	}
//
//	ModelAndView createByGet(InformationCreateGetContext informationCreateGetContext) {
//		informationCreateGetContext.setModelAndView();
//		return informationCreateGetContext.getModelAndView();
//	}
//
//	ModelAndView createByPost(InformationCreatePostContext informationCreatePostContext) {
//		if (informationCreatePostContext.hasErrors()) {
//			informationCreatePostContext.setErrorModelAndView();
//			return informationCreatePostContext.getModelAndView();
//		}
//
//		informationCreatePostContext.setModelAndView();
//		return informationCreatePostContext.getModelAndView();
//	}
//
//	ModelAndView createConfirmByGet(
//			InformationCreateConfirmGetContext informationCreateConfirmGetContext) {
//		informationCreateConfirmGetContext.setModelAndView();
//		return informationCreateConfirmGetContext.getModelAndView();
//	}
//
//	ModelAndView createConfirmByPost(
//			InformationCreateConfirmPostContext informationCreateConfirmPostContext) {
//		informationCreateConfirmPostContext.setInformationFacadeDBLogic(informationFacadeDBLogic);
//
//		informationCreateConfirmPostContext.saveInformation();
//		informationCreateConfirmPostContext.setModelAndView();
//		return informationCreateConfirmPostContext.getModelAndView();
//	}
//
//	ModelAndView createCompleteByGet(
//			InformationCreateCompleteGetContext informationCreateCompleteGetContext) {
//		informationCreateCompleteGetContext.setModelAndView();
//		return informationCreateCompleteGetContext.getModelAndView();
//	}
//
//	ModelAndView commentCreateByPost(CommentCreatePostContext commentCreatePostContext) {
//		commentCreatePostContext.setInformationFacadeDBLogic(informationFacadeDBLogic);
//
//		commentCreatePostContext.saveComment();
//		commentCreatePostContext.setModelAndView();
//		return commentCreatePostContext.getModelAndView();
//	}
//
//	ModelAndView createByPost(ParticipantAddPostContext participantAddPostContext) {
//		participantAddPostContext.setInformationFacadeDBLogic(informationFacadeDBLogic);
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
