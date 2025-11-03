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
		java.util.UUID userId = this.getUserLogic.getUserFromCache().getId(); // キャッシュからログインユーザーID取得
		mypageGetContext.setUserId(userId); // コンテキストに userId をセット
		return mypageService.rootByGet(mypageGetContext);
	}

	@GetMapping("{userId}")
	ModelAndView userByGet(@PathVariable String userId, ModelAndView mv) {
		MypageGetUserContext mypageGetUserContext = new MypageGetUserContext(mv);
		mypageGetUserContext.setUserId(java.util.UUID.fromString(userId));
		return mypageService.userByGet(mypageGetUserContext);
	}

	@GetMapping("/demos")
	ModelAndView demosByGet(ModelAndView mv) {
		MypageGetUserContext mypageGetUserContext = new MypageGetUserContext(mv);
		java.util.UUID userId = this.getUserLogic.getUserFromCache().getId();
		mypageGetUserContext.setUserId(userId);
		mypageGetUserContext.setPageType("demos");
		return mypageService.userByGet(mypageGetUserContext);
	}

	@GetMapping("/participated")
	ModelAndView participatedByGet(ModelAndView mv) {
		MypageGetUserContext mypageGetUserContext = new MypageGetUserContext(mv);
		java.util.UUID userId = this.getUserLogic.getUserFromCache().getId();
		mypageGetUserContext.setUserId(userId);
		mypageGetUserContext.setPageType("participated");
		return mypageService.userByGet(mypageGetUserContext);
	}

	@GetMapping("/favorites")
	ModelAndView favoritesByGet(ModelAndView mv) {
		MypageGetUserContext mypageGetUserContext = new MypageGetUserContext(mv);
		java.util.UUID userId = this.getUserLogic.getUserFromCache().getId();
		mypageGetUserContext.setUserId(userId);
		mypageGetUserContext.setPageType("favorites");
		return mypageService.userByGet(mypageGetUserContext);
	}

	@GetMapping("/supported")
	ModelAndView supportedByGet(ModelAndView mv) {
		MypageGetUserContext mypageGetUserContext = new MypageGetUserContext(mv);
		java.util.UUID userId = this.getUserLogic.getUserFromCache().getId();
		mypageGetUserContext.setUserId(userId);
		mypageGetUserContext.setPageType("supported");
		return mypageService.userByGet(mypageGetUserContext);
	}

	@GetMapping("/settings")
	ModelAndView settingsByGet(ModelAndView mv) {
		MypageGetUserContext mypageGetUserContext = new MypageGetUserContext(mv);
		java.util.UUID userId = this.getUserLogic.getUserFromCache().getId();
		mypageGetUserContext.setUserId(userId);
		mypageGetUserContext.setPageType("settings");
		return mypageService.userByGet(mypageGetUserContext);
	}

//	@GetMapping("/show")
//	ModelAndView showByGet(@RequestParam("demoId") Integer demoId, ModelAndView mv) {
//		demoShowGetContext demoShowGetContext = new demoShowGetContext(demoId, mv);
//		return demoService.showByGet(demoShowGetContext);
//	}
//
//	@GetMapping("/create")
//	ModelAndView createByGet(DemoForm DemoForm, ModelAndView modelAndView) {
//		demoCreateGetContext demoCreateGetContext = new demoCreateGetContext(
//				modelAndView, DemoForm);
//		return demoService.createByGet(demoCreateGetContext);
//	}
//
//	@PostMapping("/create")
//	ModelAndView createByPost(@Valid DemoForm DemoForm, BindingResult bindingResult,
//			ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
//		demoCreatePostContext demoPostContext = new demoCreatePostContext(
//				modelAndView, DemoForm, bindingResult, redirectAttributes);
//		return demoService.createByPost(demoPostContext);
//	}
//
//	@GetMapping("/create/confirm")
//	ModelAndView createConfirmByGet(DemoForm DemoForm, ModelAndView modelAndView) {
//		demoCreateConfirmGetContext demoCreateConfirmGetContext = new demoCreateConfirmGetContext(
//				DemoForm, modelAndView);
//		return demoService.createConfirmByGet(demoCreateConfirmGetContext);
//	}
//
//	@PostMapping("/create/confirm")
//	ModelAndView createConfirmByPost(DemoForm DemoForm, ModelAndView modelAndView,
//			RedirectAttributes redirectAttributes) {
//		demoCreateConfirmPostContext demoCreateConfirmPostContext = new demoCreateConfirmPostContext(
//				modelAndView, DemoForm, redirectAttributes);
//		return demoService.createConfirmByPost(demoCreateConfirmPostContext);
//	}
//
//	@GetMapping("/create/complete")
//	ModelAndView createCompleteByGet(DemoForm DemoForm, ModelAndView modelAndView) {
//		demoCreateCompleteGetContext demoCreateCompleteGetContext = new demoCreateCompleteGetContext(
//				DemoForm, modelAndView);
//		return demoService.createCompleteByGet(demoCreateCompleteGetContext);
//	}
//
//	@PostMapping("/comment/create")
//	ModelAndView commentCreateByPost(@RequestParam("demoId") Integer demoId, @Valid CommentForm commentForm, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
//		CommentCreatePostContext commentPostContext = new CommentCreatePostContext(
//				demoId, modelAndView, commentForm, redirectAttributes);
//		return demoService.commentCreateByPost(commentPostContext);
//	}
//
//	@PostMapping("{demoId}/participant/add")
//	ModelAndView createByPost(@PathVariable Integer demoId, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
//		ParticipantAddPostContext participantAddPostContext = new ParticipantAddPostContext(
//				demoId, modelAndView, redirectAttributes);
//		return demoService.createByPost(participantAddPostContext);
//	}
}
