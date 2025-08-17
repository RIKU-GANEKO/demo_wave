package product.demo_wave.user;

import java.util.NoSuchElementException;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
class UserService {
    private final UserFacadeDBLogic userFacadeDBLogic;

//    void rootByGet(UserGetContext userGetContext) {
//        userGetContext.setUserFacadeDBLogic(userFacadeDBLogic);
//        userGetContext.init();
//        userGetContext.fetchUsers();
//        userGetContext.setModelAndView();
//    }
//
//    ModelAndView rootByPost(UserPostContext userPostContext) {
//        userPostContext.setUserFacadeDBLogic(userFacadeDBLogic);
//
//        if (userPostContext.hasErrors()) {
//            userPostContext.setErrorModelAndView();
//            return userPostContext.getModelAndView();
//        }
//
//        if (userPostContext.isValid()) {
//            userPostContext.saveUser();
//        }
//        userPostContext.setModelAndView();
//        return userPostContext.getModelAndView();
//    }
//
//    void showByGet(UserShowGetContext userShowGetContext) throws DataAccessException, NoSuchElementException {
//        userShowGetContext.setUserFacadeDBLogic(userFacadeDBLogic);
//        userShowGetContext.fetchUser();
//        userShowGetContext.setModelAndView();
//    }

    void createByGet(UserCreateGetContext userCreateGetContext) {
        userCreateGetContext.setUserFacadeDBLogic(userFacadeDBLogic);
//        userCreateGetContext.init();
        userCreateGetContext.setModelAndView();
    }

    ModelAndView createByPost(UserCreatePostContext userCreatePostContext) {
        userCreatePostContext.setUserFacadeDBLogic(userFacadeDBLogic);

        if (userCreatePostContext.hasErrors()) {
            userCreatePostContext.setErrorModelAndView();
            System.out.println("エラーがあります");
            return userCreatePostContext.getModelAndView();
        }

        userCreatePostContext.setModelAndView();
        return userCreatePostContext.getModelAndView();
    }

    void createConfirmByGet(UserCreateConfirmGetContext userCreateConfirmGetContext) {
        userCreateConfirmGetContext.setModelAndView();
    }

    ModelAndView createConfirmByPost(UserCreateConfirmPostContext userCreateConfirmPostContext) throws DataAccessException, NoSuchElementException {
        userCreateConfirmPostContext.setUserFacadeDBLogic(userFacadeDBLogic);
        userCreateConfirmPostContext.saveUser();
        userCreateConfirmPostContext.setModelAndView();
        return userCreateConfirmPostContext.getModelAndView();
    }

    // 完了画面は削除 - 登録後は直接デモ一覧に遷移
//
//    void editByGet(UserEditGetContext userEditGetContext) throws DataAccessException, NoSuchElementException {
//        userEditGetContext.setUserFacadeDBLogic(userFacadeDBLogic);
//        userEditGetContext.fetchUser();
//        userEditGetContext.setModelAndView();
//    }
//
//    ModelAndView editByPost(UserEditPostContext userEditPostContext) {
//        userEditPostContext.setUserFacadeDBLogic(userFacadeDBLogic);
//
//        userEditPostContext.fetchUser();
//        if (userEditPostContext.hasErrors()) {
//            userEditPostContext.setErrorModelAndView();
//            return userEditPostContext.getModelAndView();
//        }
//
//        userEditPostContext.setModelAndView();
//        return userEditPostContext.getModelAndView();
//    }
//
//    void editConfirmByGet(UserEditConfirmGetContext userEditConfirmGetContext) {
//        userEditConfirmGetContext.setModelAndView();
//    }
//
//    ModelAndView editConfirmByPost(UserEditConfirmPostContext userEditConfirmPostContext) throws DataAccessException, NoSuchElementException {
//        userEditConfirmPostContext.setUserFacadeDBLogic(userFacadeDBLogic);
//        userEditConfirmPostContext.updateUser();
//        userEditConfirmPostContext.setModelAndView();
//        return userEditConfirmPostContext.getModelAndView();
//    }
//
//    void deleteByGet(UserDeleteGetContext userDeleteGetContext) throws DataAccessException, NoSuchElementException {
//        userDeleteGetContext.setUserFacadeDBLogic(userFacadeDBLogic);
//        userDeleteGetContext.fetchUser();
//        userDeleteGetContext.setModelAndView();
//    }
//
//    ModelAndView deleteByPost(UserDeletePostContext userDeletePostContext) throws DataAccessException, NoSuchElementException {
//        userDeletePostContext.setUserFacadeDBLogic(userFacadeDBLogic);
//
//        userDeletePostContext.deleteUser();
//        userDeletePostContext.setModelAndView();
//        return userDeletePostContext.getModelAndView();
//    }
}
