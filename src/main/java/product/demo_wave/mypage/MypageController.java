package product.demo_wave.mypage;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import jakarta.validation.Valid;
import product.demo_wave.AppProperties;
import product.demo_wave.logic.GetUserLogic;

@Controller
@RequestMapping("/mypage")
@AllArgsConstructor
class MypageController {
	private final MypageService mypageService;
	private final AppProperties properties;
	private final GetUserLogic getUserLogic;

	@GetMapping
	ModelAndView rootByGet(ModelAndView mv) {
		MypageGetContext mypageGetContext = new MypageGetContext(mv);
		Integer userId = this.getUserLogic.getUserFromCache().getId(); // キャッシュからログインユーザーID取得
		mypageGetContext.setUserId(userId); // コンテキストに userId をセット
		return mypageService.rootByGet(mypageGetContext);
	}

	@GetMapping("{informationId}")
	ModelAndView userByGet(ModelAndView mv) {
		MypageGetUserContext mypageGetUserContext = new MypageGetUserContext(mv);
		return mypageService.userByGet(mypageGetUserContext);
	}

//	@GetMapping("/show")
//	ModelAndView showByGet(@RequestParam("informationId") Integer informationId, ModelAndView mv) {
//		InformationShowGetContext informationShowGetContext = new InformationShowGetContext(informationId, mv);
//		return informationService.showByGet(informationShowGetContext);
//	}
//
//	@GetMapping("/create")
//	ModelAndView createByGet(InformationForm informationForm, ModelAndView modelAndView) {
//		InformationCreateGetContext informationCreateGetContext = new InformationCreateGetContext(
//				modelAndView, informationForm);
//		return informationService.createByGet(informationCreateGetContext);
//	}
//
//	@PostMapping("/create")
//	ModelAndView createByPost(@Valid InformationForm informationForm, BindingResult bindingResult,
//			ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
//		InformationCreatePostContext informationPostContext = new InformationCreatePostContext(
//				modelAndView, informationForm, bindingResult, redirectAttributes);
//		return informationService.createByPost(informationPostContext);
//	}
//
//	@GetMapping("/create/confirm")
//	ModelAndView createConfirmByGet(InformationForm informationForm, ModelAndView modelAndView) {
//		InformationCreateConfirmGetContext informationCreateConfirmGetContext = new InformationCreateConfirmGetContext(
//				informationForm, modelAndView);
//		return informationService.createConfirmByGet(informationCreateConfirmGetContext);
//	}
//
//	@PostMapping("/create/confirm")
//	ModelAndView createConfirmByPost(InformationForm informationForm, ModelAndView modelAndView,
//			RedirectAttributes redirectAttributes) {
//		InformationCreateConfirmPostContext informationCreateConfirmPostContext = new InformationCreateConfirmPostContext(
//				modelAndView, informationForm, redirectAttributes);
//		return informationService.createConfirmByPost(informationCreateConfirmPostContext);
//	}
//
//	@GetMapping("/create/complete")
//	ModelAndView createCompleteByGet(InformationForm informationForm, ModelAndView modelAndView) {
//		InformationCreateCompleteGetContext informationCreateCompleteGetContext = new InformationCreateCompleteGetContext(
//				informationForm, modelAndView);
//		return informationService.createCompleteByGet(informationCreateCompleteGetContext);
//	}
//
//	@PostMapping("/comment/create")
//	ModelAndView commentCreateByPost(@RequestParam("informationId") Integer informationId, @Valid CommentForm commentForm, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
//		CommentCreatePostContext commentPostContext = new CommentCreatePostContext(
//				informationId, modelAndView, commentForm, redirectAttributes);
//		return informationService.commentCreateByPost(commentPostContext);
//	}
//
//	@PostMapping("{informationId}/participant/add")
//	ModelAndView createByPost(@PathVariable Integer informationId, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
//		ParticipantAddPostContext participantAddPostContext = new ParticipantAddPostContext(
//				informationId, modelAndView, redirectAttributes);
//		return informationService.createByPost(participantAddPostContext);
//	}
}
