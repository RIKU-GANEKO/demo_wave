package product.demo_wave.mypage;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import product.demo_wave.common.annotation.CustomRetry;
import product.demo_wave.common.logic.BasicFacadeDBLogic;
import product.demo_wave.entity.Account;
import product.demo_wave.entity.Comment;
import product.demo_wave.entity.Information;
import product.demo_wave.entity.Participant;
//import product.demo_wave.information.CommentForm;
//import product.demo_wave.information.InformationForm;
import product.demo_wave.entity.User;
import product.demo_wave.information.InformationWithParticipantDTO;
import product.demo_wave.logic.GetUserLogic;
import product.demo_wave.repository.AccountRepository;
import product.demo_wave.repository.CommentRepository;
import product.demo_wave.repository.InformationRepository;
import product.demo_wave.repository.ParticipantRepository;
import product.demo_wave.repository.PaymentRepository;
import product.demo_wave.repository.UserRepository;

@Component
@AllArgsConstructor
class MypageFacadeDBLogic extends BasicFacadeDBLogic {
    private final InformationRepository informationRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PaymentRepository paymentRepository;
    private final ParticipantRepository participantRepository;
    private final GetUserLogic getUserLogic; // GetUserLogicをフィールドとして追加

    @CustomRetry
    User fetchUser() {
        Integer userId = this.getUserLogic.getUserFromCache().getId();
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(new User());
    }

    @CustomRetry
    Account fetchAccount() {
        Integer userId = this.getUserLogic.getUserFromCache().getId();
        Optional<User> user = userRepository.findById(userId);
        Integer account_id = user.get().getAccount().getId();
        Optional<Account> account = accountRepository.findById(account_id);
        return account.orElse(new Account());
    }

    // ログイン中のユーザーが参加した(する予定の)デモ活動を取得
    @CustomRetry
    List<Information> fetchParticipatedInformation() {
        Integer userId = this.getUserLogic.getUserFromCache().getId();
        return informationRepository.findParticipatedInformationByUserId(userId);
    }

    // ログイン中のユーザーが今月支援した金額を取得
    @CustomRetry
    BigDecimal fetchSendDonateAmount() {
        Integer userId = this.getUserLogic.getUserFromCache().getId();
        return paymentRepository.getTotalDonatedAmountByUserIdForCurrentMonth(userId);
    }

//    @CustomRetry
//    Information fetchInformation(Integer informationId) {
//        Optional<Information> information = informationRepository.findById(informationId);
//        return information.orElse(new Information());
//    }

//    @CustomRetry
//    void saveInformation(InformationForm informationForm) {
//
//        // Information を保存し、保存したエンティティを取得
//        Information savedInformation = informationRepository.saveAndFlush(toEntity(informationForm, null));
//
//        // 登録した Information の ID を取得
//        Integer informationId = savedInformation.getId();
//
//        // Participant に登録
//        participantRepository.saveAndFlush(toParticipantEntity(informationId));
//
////        informationRepository.saveAndFlush(toEntity(informationForm, null));
//    }
//
//    private Information toEntity(InformationForm informationForm, Integer informationId) {
//        Information information = new Information();
//
//        if (informationId != null) {
//            information = informationRepository.findById(informationId)
//                    .orElseThrow(() -> new NoSuchElementException("Information not found."));
//        }
//
////        // SecurityContextからログインユーザーの詳細情報を取得
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        UsersDetails usersDetails = (UsersDetails) authentication.getPrincipal(); // UsersDetailsを取得
////        User loggedInUser = usersDetails.getUser(); // Userオブジェクトを取得
//
//        information.setTitle(informationForm.title());
//        information.setContent(informationForm.content());
//        information.setAnnouncementTime(informationForm.announcementTime());
//        information.setDemoDate(informationForm.demoDate());
//        information.setDemoPlace(informationForm.demoPlace());
//        information.setDemoAddress(informationForm.demoAddress());
//        information.setDemoAddressLatitude(informationForm.demoAddressLatitude());
//        information.setDemoAddressLongitude(informationForm.demoAddressLongitude());
//        information.setOrganizerUserId(this.getUserLogic.getUserFromCache().getId());
////        information.setOrganizerName("我如古陸");
//
//        return information;
//    }
//
//    private Participant toParticipantEntity(Integer informationId) {
//        Participant participant = new Participant();
//
//        participant.setInformationId(informationId);
//        participant.setUserId(this.getUserLogic.getUserFromCache().getId());
//
//        return participant;
//    }
//
//    @CustomRetry
//    void saveComment(CommentForm commentForm, Integer informationId) {
//        commentRepository.saveAndFlush(toEntity(commentForm, informationId));
//    }
//
//    private Comment toEntity(CommentForm commentForm, Integer informationId) {
//        Comment comment = new Comment();
//
//        comment.setInformationId(informationId);
//        comment.setContent(commentForm.content());
//        comment.setUserId(this.getUserLogic.getUserFromCache().getId());
//
//        return comment;
//    }
//
//    @CustomRetry
//    void toggleParticipant(Integer informationId) {
//
//        Integer userId = this.getUserLogic.getUserFromCache().getId();
//
//        // 該当データを検索
//        Optional<Participant> existingParticipant = participantRepository.findByInformationIdAndUserId(informationId, userId);
//
//        if (existingParticipant.isEmpty() || existingParticipant.get().getDeletedAt() != null) {
//            // データが存在しない、または論理削除されている場合、新しいデータを作成
//            participantRepository.saveAndFlush(toEntity(informationId, userId));
//        } else {
//            // データが存在し、論理削除されていない場合、不参加として論理削除
//            Participant participant = existingParticipant.get();
//            participant.setDeletedAt(LocalDateTime.now());
//            participantRepository.saveAndFlush(participant);
//        }
//
////        Boolean isParticipant = participantRepository.existsByInformationIdAndUserIdAndDeletedAtIsNull(informationId, this.getUserLogic.getUserFromCache().getId());
////        if (!isParticipant) {
////            participantRepository.saveAndFlush(toEntity(informationId, userId));
////        } else {
////
////        }
//    }
//
//    private Participant toEntity(Integer informationId, Integer userId) {
//        Participant participant = new Participant();
//
//        participant.setInformationId(informationId);
//        participant.setUserId(userId);
//
//        return participant;
//    }
//
//    @CustomRetry
//    public Boolean isParticipant(Integer informationId) {
//        Boolean isParticipant = participantRepository.existsByInformationIdAndUserIdAndDeletedAtIsNull(informationId, this.getUserLogic.getUserFromCache().getId());
//        return isParticipant;
//    }

}
