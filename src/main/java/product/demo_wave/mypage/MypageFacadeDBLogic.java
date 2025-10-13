package product.demo_wave.mypage;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import product.demo_wave.common.annotation.CustomRetry;
import product.demo_wave.common.logic.BasicFacadeDBLogic;
import product.demo_wave.entity.Demo;
//import product.demo_wave.demo.CommentForm;
//import product.demo_wave.demo.DemoForm;
import product.demo_wave.entity.User;
import product.demo_wave.logic.GetUserLogic;
import product.demo_wave.repository.DemoRepository;
import product.demo_wave.repository.ParticipantRepository;
import product.demo_wave.repository.UserRepository;

@Component
@AllArgsConstructor
class MypageFacadeDBLogic extends BasicFacadeDBLogic {
    private final DemoRepository demoRepository;
    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;
    private final product.demo_wave.repository.FavoriteDemoRepository favoriteDemoRepository;
    private final product.demo_wave.repository.PaymentRepository paymentRepository;
    private final GetUserLogic getUserLogic; // GetUserLogicをフィールドとして追加

    @CustomRetry
    User fetchUser() {
        Integer userId = this.getUserLogic.getUserFromCache().getId();
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(new User());
    }

    // ログイン中のユーザーが参加した(する予定の)デモ活動を取得
    @CustomRetry
    List<Demo> fetchParticipatedDemo() {
        Integer userId = this.getUserLogic.getUserFromCache().getId();
        return demoRepository.findParticipatedDemoByUserId(userId);
    }

    // ログイン中のユーザーがお気に入りに登録したデモ活動を取得
    @CustomRetry
    List<Demo> fetchFavoriteDemos() {
        User user = fetchUser();
        return favoriteDemoRepository.findAllByUserAndDeletedAtIsNull(user)
                .stream()
                .map(favoriteDemo -> favoriteDemo.getDemo())
                .collect(java.util.stream.Collectors.toList());
    }

    // ログイン中のユーザーが支援したデモ活動を取得
    @CustomRetry
    List<Demo> fetchSupportedDemos() {
        User user = fetchUser();
        return paymentRepository.findDistinctDemosByUserAndDeletedAtIsNull(user);
    }

    // ログイン中のユーザーが投稿したデモ活動を取得
    @CustomRetry
    List<Demo> fetchPostedDemos() {
        Integer userId = this.getUserLogic.getUserFromCache().getId();
        return demoRepository.findByUserIdAndDeletedAtIsNullOrderByCreatedAtDesc(userId);
    }

//    @CustomRetry
//    Demo fetchdemo(Integer demoId) {
//        Optional<Demo> demo = demoRepository.findById(demoId);
//        return demo.orElse(new Demo());
//    }

//    @CustomRetry
//    void savedemo(DemoForm DemoForm) {
//
//        // Demo を保存し、保存したエンティティを取得
//        Demo saveddemo = demoRepository.saveAndFlush(toEntity(DemoForm, null));
//
//        // 登録した Demo の ID を取得
//        Integer demoId = saveddemo.getId();
//
//        // Participant に登録
//        participantRepository.saveAndFlush(toParticipantEntity(demoId));
//
////        demoRepository.saveAndFlush(toEntity(DemoForm, null));
//    }
//
//    private Demo toEntity(DemoForm DemoForm, Integer demoId) {
//        Demo demo = new Demo();
//
//        if (demoId != null) {
//            demo = demoRepository.findById(demoId)
//                    .orElseThrow(() -> new NoSuchElementException("Demo not found."));
//        }
//
////        // SecurityContextからログインユーザーの詳細情報を取得
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        UsersDetails usersDetails = (UsersDetails) authentication.getPrincipal(); // UsersDetailsを取得
////        User loggedInUser = usersDetails.getUser(); // Userオブジェクトを取得
//
//        demo.setTitle(DemoForm.title());
//        demo.setContent(DemoForm.content());
//        demo.setAnnouncementTime(DemoForm.announcementTime());
//        demo.setDemoDate(DemoForm.demoDate());
//        demo.setDemoPlace(DemoForm.demoPlace());
//        demo.setDemoAddress(DemoForm.demoAddress());
//        demo.setDemoAddressLatitude(DemoForm.demoAddressLatitude());
//        demo.setDemoAddressLongitude(DemoForm.demoAddressLongitude());
//        demo.setOrganizerUserId(this.getUserLogic.getUserFromCache().getId());
////        demo.setOrganizerName("我如古陸");
//
//        return demo;
//    }
//
//    private Participant toParticipantEntity(Integer demoId) {
//        Participant participant = new Participant();
//
//        participant.setdemoId(demoId);
//        participant.setUserId(this.getUserLogic.getUserFromCache().getId());
//
//        return participant;
//    }
//
//    @CustomRetry
//    void saveComment(CommentForm commentForm, Integer demoId) {
//        commentRepository.saveAndFlush(toEntity(commentForm, demoId));
//    }
//
//    private Comment toEntity(CommentForm commentForm, Integer demoId) {
//        Comment comment = new Comment();
//
//        comment.setdemoId(demoId);
//        comment.setContent(commentForm.content());
//        comment.setUserId(this.getUserLogic.getUserFromCache().getId());
//
//        return comment;
//    }
//
//    @CustomRetry
//    void toggleParticipant(Integer demoId) {
//
//        Integer userId = this.getUserLogic.getUserFromCache().getId();
//
//        // 該当データを検索
//        Optional<Participant> existingParticipant = participantRepository.findBydemoIdAndUserId(demoId, userId);
//
//        if (existingParticipant.isEmpty() || existingParticipant.get().getDeletedAt() != null) {
//            // データが存在しない、または論理削除されている場合、新しいデータを作成
//            participantRepository.saveAndFlush(toEntity(demoId, userId));
//        } else {
//            // データが存在し、論理削除されていない場合、不参加として論理削除
//            Participant participant = existingParticipant.get();
//            participant.setDeletedAt(LocalDateTime.now());
//            participantRepository.saveAndFlush(participant);
//        }
//
////        Boolean isParticipant = participantRepository.existsBydemoIdAndUserIdAndDeletedAtIsNull(demoId, this.getUserLogic.getUserFromCache().getId());
////        if (!isParticipant) {
////            participantRepository.saveAndFlush(toEntity(demoId, userId));
////        } else {
////
////        }
//    }
//
//    private Participant toEntity(Integer demoId, Integer userId) {
//        Participant participant = new Participant();
//
//        participant.setdemoId(demoId);
//        participant.setUserId(userId);
//
//        return participant;
//    }
//
//    @CustomRetry
//    public Boolean isParticipant(Integer demoId) {
//        Boolean isParticipant = participantRepository.existsBydemoIdAndUserIdAndDeletedAtIsNull(demoId, this.getUserLogic.getUserFromCache().getId());
//        return isParticipant;
//    }

}
