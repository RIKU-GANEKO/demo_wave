package product.demo_wave.demo;

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
@RequestMapping("/demo")
@AllArgsConstructor
class DemoController {
	private final DemoService demoService;
	private final AppProperties properties;
	private final GetUserLogic getUserLogic;

	@GetMapping("/show")
	ModelAndView showByGet(@RequestParam("demoId") Integer demoId, ModelAndView mv) {
		DemoShowGetContext demoShowGetContext = new DemoShowGetContext(demoId, mv);
		return demoService.showByGet(demoShowGetContext);
	}

	@GetMapping("/create")
	ModelAndView createByGet(DemoForm demoForm, ModelAndView modelAndView) {
		DemoCreateGetContext demoCreateGetContext = new DemoCreateGetContext(
				modelAndView, demoForm);
		return demoService.createByGet(demoCreateGetContext);
	}

	@PostMapping("/create")
	ModelAndView createByPost(@Valid DemoForm demoForm, BindingResult bindingResult,
			@RequestParam(required = false) String back,
			ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
		DemoCreatePostContext demoPostContext = new DemoCreatePostContext(
				modelAndView, demoForm, bindingResult, redirectAttributes, back);
		return demoService.createByPost(demoPostContext);
	}

	@GetMapping("/create/confirm")
	ModelAndView createConfirmByGet(DemoForm demoForm, ModelAndView modelAndView) {
		DemoCreateConfirmGetContext demoCreateConfirmGetContext = new DemoCreateConfirmGetContext(
				demoForm, modelAndView);
		return demoService.createConfirmByGet(demoCreateConfirmGetContext);
	}

	@PostMapping("/create/confirm")
	ModelAndView createConfirmByPost(DemoForm demoForm, ModelAndView modelAndView,
			RedirectAttributes redirectAttributes) {
		DemoCreateConfirmPostContext demoCreateConfirmPostContext = new DemoCreateConfirmPostContext(
				modelAndView, demoForm, redirectAttributes);
		return demoService.createConfirmByPost(demoCreateConfirmPostContext);
	}

	@GetMapping("/create/complete")
	ModelAndView createCompleteByGet(DemoForm demoForm, ModelAndView modelAndView) {
		DemoCreateCompleteGetContext demoCreateCompleteGetContext = new DemoCreateCompleteGetContext(
				demoForm, modelAndView);
		return demoService.createCompleteByGet(demoCreateCompleteGetContext);
	}

	@PostMapping("/comment/create")
	ModelAndView commentCreateByPost(@RequestParam("demoId") Integer demoId, @Valid CommentForm commentForm, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
		CommentCreatePostContext commentPostContext = new CommentCreatePostContext(
				demoId, modelAndView, commentForm, redirectAttributes);
		return demoService.commentCreateByPost(commentPostContext);
	}

	@PostMapping("{demoId}/participant/add")
	ModelAndView createByPost(@PathVariable Integer demoId, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
		ParticipantAddPostContext participantAddPostContext = new ParticipantAddPostContext(
				demoId, modelAndView, redirectAttributes);
		return demoService.createByPost(participantAddPostContext);
	}

	@GetMapping("/edit")
	ModelAndView editByGet(@RequestParam("demoId") Integer demoId, ModelAndView modelAndView) {
		DemoEditGetContext demoEditGetContext = new DemoEditGetContext(modelAndView, demoId);
		return demoService.editByGet(demoEditGetContext);
	}

	@PostMapping("/edit")
	ModelAndView editByPost(@Valid DemoForm demoForm, BindingResult bindingResult,
			@RequestParam("demoId") Integer demoId,
			@RequestParam(required = false) String back,
			ModelAndView modelAndView) {
		DemoEditPostContext demoEditPostContext = new DemoEditPostContext(
				modelAndView, demoForm, demoId, bindingResult, back);
		return demoService.editByPost(demoEditPostContext);
	}

	@GetMapping("/edit/confirm")
	ModelAndView editConfirmByGet(DemoForm demoForm, @RequestParam("demoId") Integer demoId,
			ModelAndView modelAndView) {
		DemoEditConfirmGetContext demoEditConfirmGetContext = new DemoEditConfirmGetContext(
				modelAndView, demoForm, demoId);
		return demoService.editConfirmByGet(demoEditConfirmGetContext);
	}

	@PostMapping("/edit/confirm")
	ModelAndView editConfirmByPost(DemoForm demoForm, @RequestParam("demoId") Integer demoId,
			ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
		// Use demoId from request parameter (record can't bind hidden fields properly)
		DemoEditConfirmPostContext demoEditConfirmPostContext = new DemoEditConfirmPostContext(
				modelAndView, demoForm, demoId);
		return demoService.editConfirmByPost(demoEditConfirmPostContext);
	}

}
