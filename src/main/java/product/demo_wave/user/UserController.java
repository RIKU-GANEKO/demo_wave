package product.demo_wave.user;

import java.util.NoSuchElementException;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import product.demo_wave.AppProperties;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
class UserController {
    private final UserService userService;
    private final AppProperties appProperties;

//    /**
//     * ユーザー一覧画面の表示。
//     */
//    @GetMapping
//    ModelAndView rootByGet(@RequestParam("accountId") Integer accountId, ModelAndView modelAndView, Pageable pageable) {
//        UserGetContext userGetContext = new UserGetContext(accountId, appProperties, modelAndView, pageable);
//        userService.rootByGet(userGetContext);
//        return userGetContext.getModelAndView();
//    }

//    @PostMapping
//    ModelAndView rootByPost(@ModelAttribute @Validated UserForm userForm,
//                            BindingResult result, ModelAndView mv) {
//        UserPostContext userPostContext = new UserPostContext(userForm, result, mv);
//        return userService.rootByPost(userPostContext);
//    }

//    /**
//     * ユーザー詳細画面の表示
//     */
//    @GetMapping("/show/{userId}")
//    ModelAndView showByGet(@PathVariable Integer userId, ModelAndView modelAndView) throws DataAccessException, NoSuchElementException {
//        UserShowGetContext userShowGetContext = new UserShowGetContext(userId, modelAndView);
//        userService.showByGet(userShowGetContext);
//        return userShowGetContext.getModelAndView();
//    }

    /**
     * ユーザー登録画面の表示。
     * <li>確認ボタンを押下するとcreateByPostが実行され入力内容のチェックを行い、問題がなければ登録確認画面に遷移する
     */
    @GetMapping("/signup")
    ModelAndView createByGet(UserForm userForm, ModelAndView modelAndView) {
        UserCreateGetContext userCreateGetContext = new UserCreateGetContext(userForm, modelAndView);
        System.out.println("きている");
        userService.createByGet(userCreateGetContext);
        return userCreateGetContext.getModelAndView();
    }

    /**
     * 入力内容のチェックを行い、問題がなければ登録確認画面に遷移する
     */
    @PostMapping("/signup")
    ModelAndView createByPost(@Validated UserForm userForm, BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        UserCreatePostContext userCreatePostContext = new UserCreatePostContext(userForm, bindingResult, modelAndView, redirectAttributes);
        return userService.createByPost(userCreatePostContext);
    }

    /**
     * 登録確認画面の表示。
     * <li>登録ボタンを押下するとcreateConfirmByPostが実行され、登録処理が行われる
     */
    @GetMapping("/create/confirm")
    ModelAndView createConfirmByGet(UserForm userForm, ModelAndView modelAndView) {
        UserCreateConfirmGetContext userCreateConfirmGetContext = new UserCreateConfirmGetContext(userForm, modelAndView);
        userService.createConfirmByGet(userCreateConfirmGetContext);
        return userCreateConfirmGetContext.getModelAndView();
    }

    /**
     * 登録処理の実行。登録後は完了画面に遷移する
     */
    @PostMapping("/create/confirm")
    ModelAndView createConfirmByPost(@Validated UserForm userForm, ModelAndView modelAndView, RedirectAttributes redirectAttributes) throws DataAccessException, NoSuchElementException {
        UserCreateConfirmPostContext userCreateConfirmPostContext = new UserCreateConfirmPostContext(userForm, modelAndView, redirectAttributes);
        return userService.createConfirmByPost(userCreateConfirmPostContext);
    }

    /**
     * 完了画面の表示。
     * <li>一覧へ戻るボタンを押下すると一覧画面に遷移する
     */
    @GetMapping("/create/complete")
    ModelAndView createCompleteByGet(UserForm userForm, ModelAndView modelAndView) {
        UserCompleteGetContext userCreateCompleteGetContext = new UserCompleteGetContext(userForm, modelAndView);
        userService.completeByGet(userCreateCompleteGetContext);
        return userCreateCompleteGetContext.getModelAndView();
    }
//
//    /**
//     * 編集画面の表示。
//     * <li>確認ボタンを押下するとeditByPostが実行され入力内容のチェックを行い、問題がなければ編集確認画面に遷移する
//     */
//    @GetMapping("/edit/{userId}")
//    ModelAndView editByGet(@PathVariable Integer userId, UserForm userForm, ModelAndView modelAndView) {
//        UserEditGetContext userEditGetContext = new UserEditGetContext(userId, userForm, modelAndView);
//        userService.editByGet(userEditGetContext);
//        return userEditGetContext.getModelAndView();
//    }
//
//    /**
//     * 入力内容のチェックを行い、問題がなければ編集確認画面に遷移する
//     */
//    @PostMapping("/edit/{userId}")
//    ModelAndView editByPost(@PathVariable Integer userId, @ModelAttribute @Validated UserForm userForm, BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
//        UserEditPostContext userEditPostContext = new UserEditPostContext(userId, userForm, bindingResult, modelAndView, redirectAttributes);
//        return userService.editByPost(userEditPostContext);
//    }
//
//    /**
//     * 編集確認画面の表示。
//     * <li>登録ボタンを押下するとeditConfirmByPostが実行され、編集処理が行われる
//     */
//    @GetMapping("/edit/{userId}/confirm")
//    ModelAndView editConfirmByGet(@PathVariable Integer userId, UserForm userForm, ModelAndView modelAndView) {
//        UserEditConfirmGetContext userEditConfirmGetContext = new UserEditConfirmGetContext(userId, userForm, modelAndView);
//        userService.editConfirmByGet(userEditConfirmGetContext);
//        return userEditConfirmGetContext.getModelAndView();
//    }
//
//    /**
//     * 編集処理の実行。編集後は完了画面に遷移する
//     */
//    @PostMapping("/edit/{userId}/confirm")
//    ModelAndView editConfirmByPost(@PathVariable Integer userId, @Validated UserForm userForm, ModelAndView modelAndView, RedirectAttributes redirectAttributes) throws DataAccessException, NoSuchElementException {
//        UserEditConfirmPostContext userEditConfirmPostContext = new UserEditConfirmPostContext(userId, userForm, modelAndView, redirectAttributes);
//        return userService.editConfirmByPost(userEditConfirmPostContext);
//    }
//
//    /**
//     * 完了画面の表示。
//     * <li>一覧へ戻るボタンを押下すると一覧画面に遷移する
//     */
//    @GetMapping("/edit/complete")
//    ModelAndView editCompleteByGet(UserForm userForm, ModelAndView modelAndView) {
//        UserCompleteGetContext userCreateCompleteGetContext = new UserCompleteGetContext(userForm, modelAndView);
//        userService.completeByGet(userCreateCompleteGetContext);
//        return userCreateCompleteGetContext.getModelAndView();
//    }
//
//    /**
//     * 削除画面の表示。
//     * <li>削除ボタン押下でdeleteByPostが実行されて、その際に削除を行う
//     */
//    @GetMapping("/delete/{userId}")
//    ModelAndView deleteByGet(@PathVariable Integer userId, ModelAndView modelAndView) throws DataAccessException, NoSuchElementException {
//        UserDeleteGetContext userDeleteGetContext = new UserDeleteGetContext(userId, modelAndView);
//        userService.deleteByGet(userDeleteGetContext);
//        return userDeleteGetContext.getModelAndView();
//    }
//
//    /**
//     * 削除処理の実行。削除後は一覧画面に遷移する
//     */
//    @PostMapping("/delete/{userId}")
//    ModelAndView deleteByPost(@PathVariable Integer userId, ModelAndView modelAndView) throws DataAccessException, NoSuchElementException {
//        UserDeletePostContext userDeletePostContext = new UserDeletePostContext(userId, modelAndView);
//        return userService.deleteByPost(userDeletePostContext);
//    }
}
