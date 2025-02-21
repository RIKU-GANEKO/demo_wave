package product.demo_wave.user;

import java.util.Collections;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import jakarta.transaction.Transactional;

import product.demo_wave.entity.Account;
import product.demo_wave.entity.Roles;
import product.demo_wave.entity.User;
import product.demo_wave.repository.AccountRepository;
import product.demo_wave.repository.RolesRepository;
import product.demo_wave.repository.UserRepository;
import product.demo_wave.common.annotation.CustomRetry;
import product.demo_wave.common.logic.BasicFacadeDBLogic;
//import jp.fs.toolkit.logger.Logger;

@Component
@AllArgsConstructor
class UserFacadeDBLogic extends BasicFacadeDBLogic {

//  private static final Logger logger = Logger.getLogger(UserFacadeDBLogic.class.getSimpleName());

  private final UserRepository userRepository;
  private final AccountRepository accountRepository;
  private final RolesRepository roleRepository;

  private final PasswordEncoder passwordEncoder;

//  @CustomRetry
//  PageData<UserDTO> fetchByAccountId(Integer accountId, Pageable pageable) {
//    Account account = accountRepository.findById(accountId)
//        .orElseThrow(() -> new NoSuchElementException("Account not found."));
//    Page<User> users = userRepository.findByAccount(pageable, account);
//    Page<UserDTO> userDTOS = convert(users,
//        user -> new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getLastLogin(),
//            user.getAccount()));
//    int range = getPageDisplayRange(userDTOS);
//    return new PageData<>(userDTOS, range);
//  }
//
//  @CustomRetry
//  User fetchUser(Integer userId) {
//    User user = userRepository.findById(userId)
//        .orElseThrow(() -> new NoSuchElementException("User not found."));
//    return user;
//  }
//
//  @CustomRetry
//  Account fetchAccount(Integer accountId) {
//    Account account =  accountRepository.findById(accountId)
//        .orElseThrow(() -> new NoSuchElementException("Account not found."));
//    return account;
//  }
//
  @CustomRetry
  boolean existsSameEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @CustomRetry
  void saveUser(UserForm userForm) {
    User user = toEntity(userForm);

    // ユーザの初期設定を行う
    // ユーザのステータスを有効に設定
    user.setStatus(true);
    // ユーザのロールをUSERに設定
    user.setRoles(Collections.singletonList(fetchRoleUSER()));

//    logger.info("Create user.");
//    logger.info("New user demo : " + user.toString());
    try {
//      logger.info("Start creating user...");
      userRepository.saveAndFlush(user);
//      logger.info("User created successfully");
    } catch (Exception e) {
//      logger.error("Error occurred while creating user " + user.getName() + " : " + e.getMessage());
      throw e;
    }
  }

  private User toEntity(UserForm userForm) {
    User user = new User();
//    user.setId(userForm.id());
    user.setName(userForm.name());
    user.setEmail(userForm.email());

//    Account account = accountRepository.findById(userForm.accountId())
//        .orElseThrow(() -> new NoSuchElementException("Account not found."));
    // 現状アカウントはテスト用アカウントをダミーで入れておく
    user.setAccount(accountRepository.findById(2).get());

    String encodedPassword = passwordEncoder.encode(userForm.password());
    user.setPassword(encodedPassword);

    return user;
  }

  @CustomRetry
  private Roles fetchRoleUSER() {
    return roleRepository.findByRole("ROLE_USER");
  }
//
//  @CustomRetry
//  void updateUser(UserForm userForm, Integer userId) {
//    User user = userRepository.findById(userId)
//        .orElseThrow(() -> new NoSuchElementException("User not found."));
//
//    logger.info("Update user.");
//    logger.info("Target user demo : " + user.toString());
//    user.setName(userForm.name());
//    user.setEmail(userForm.email());
//    if (!userForm.password().isBlank()) {
//      String encodedPassword = passwordEncoder.encode(userForm.password());
//      user.setPassword(encodedPassword);
//    }
//    logger.info("Updated user demo : " + user.toString());
//    try {
//      logger.info("Start updating site...");
//      userRepository.saveAndFlush(user);
//      logger.info("User updated successfully");
//    } catch (Exception e) {
//      logger.error("Error occurred while updating user " + userId + " : " + e.getMessage());
//      throw e;
//    }
//  }
//
//  @Transactional
//  @CustomRetry
//  void deleteUser(Integer userId) {
//    User user = userRepository.findById(userId)
//        .orElseThrow(() -> new NoSuchElementException("User not found."));
//    Account account = user.getAccount();
//    logger.info("Delete user.");
//    logger.info("Target user demo : " + user.toString());
//    try {
//      // accountから対象userの紐づけを削除
//      account.getUserList().remove(user);
//      accountRepository.saveAndFlush(account);
//
//      // userの削除
//      // userRoleの削除はorphanRemoval=trueで自動で行われる
//      logger.info("Start deleting user...");
//      userRepository.delete(user);
//      logger.info("User deleted successfully");
//    } catch (Exception e) {
//      logger.error("Error occurred while deleting user " + userId + " : " + e.getMessage());
//      throw e;
//    }
//  }
}
