package product.demo_wave.mypage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import product.demo_wave.common.annotation.CustomRetry;
import product.demo_wave.common.logic.BasicFacadeDBLogic;
import product.demo_wave.entity.Demo;
//import product.demo_wave.demo.CommentForm;
//import product.demo_wave.demo.DemoForm;
import product.demo_wave.entity.GiftTransfer;
import product.demo_wave.entity.GiftTransferDetail;
import product.demo_wave.entity.User;
import product.demo_wave.logic.GetUserLogic;
import product.demo_wave.repository.DemoRepository;
import product.demo_wave.repository.GiftTransferDetailRepository;
import product.demo_wave.repository.GiftTransferRepository;
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
    private final GiftTransferRepository giftTransferRepository;
    private final GiftTransferDetailRepository giftTransferDetailRepository;
    private final GetUserLogic getUserLogic; // GetUserLogicをフィールドとして追加

    @CustomRetry
    User fetchUser() {
        java.util.UUID userId = this.getUserLogic.getUserFromCache().getId();
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(new User());
    }

    @CustomRetry
    void updateUserName(java.util.UUID userId, String name) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new java.util.NoSuchElementException("User not found"));
        user.setName(name);
        userRepository.save(user);
    }

    // ログイン中のユーザーが参加した(する予定の)デモ活動を取得
    @CustomRetry
    List<Demo> fetchParticipatedDemo() {
        java.util.UUID userId = this.getUserLogic.getUserFromCache().getId();
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

    // ログイン中のユーザーが支援したデモ活動と支援金額を取得
    @CustomRetry
    List<SupportedDemoDTO> fetchSupportedDemosWithAmount() {
        User user = fetchUser();
        List<Demo> demos = paymentRepository.findDistinctDemosByUserAndDeletedAtIsNull(user);

        List<SupportedDemoDTO> result = new ArrayList<>();
        for (Demo demo : demos) {
            BigDecimal totalAmount = paymentRepository.getTotalDonatedAmountByUserAndDemo(user, demo);
            result.add(new SupportedDemoDTO(demo, totalAmount));
        }

        return result;
    }

    // ログイン中のユーザーが投稿したデモ活動を取得
    @CustomRetry
    List<Demo> fetchPostedDemos() {
        java.util.UUID userId = this.getUserLogic.getUserFromCache().getId();
        return demoRepository.findByUserIdAndDeletedAtIsNullOrderByCreatedAtDesc(userId);
    }

    // ログイン中のユーザーが支援金を受け取ったデモ活動を取得
    @CustomRetry
    List<Demo> fetchReceivedGiftDemos() {
        java.util.UUID userId = this.getUserLogic.getUserFromCache().getId();
        return demoRepository.findReceivedGiftDemosByUserId(userId);
    }

    // ログイン中のユーザーの月ごとの受取支援金データを取得
    @CustomRetry
    List<MonthlyGiftSummaryDTO> fetchMonthlyGiftSummaries() {
        java.util.UUID userId = this.getUserLogic.getUserFromCache().getId();

        // 月ごとの受取履歴を取得（新しい順）
        List<GiftTransfer> transfers = giftTransferRepository.findByUserIdOrderByTransferMonthDesc(userId);

        List<MonthlyGiftSummaryDTO> summaries = new ArrayList<>();

        for (GiftTransfer transfer : transfers) {
            LocalDate transferMonth = transfer.getTransferMonth();

            // この月のデモごとの詳細を取得
            List<GiftTransferDetail> details = giftTransferDetailRepository
                .findByUserIdAndMonth(userId, transferMonth);

            // DTOに変換
            List<DemoGiftDetailDTO> demoDetails = details.stream()
                .map(detail -> new DemoGiftDetailDTO(
                    detail.getDemo().getId(),
                    detail.getDemo().getTitle(),
                    detail.getAmount(),
                    detail.getDemo().getDemoStartDate().toString()
                ))
                .toList();

            // 月のサマリーDTOを作成
            summaries.add(new MonthlyGiftSummaryDTO(
                transferMonth,
                transfer.getTotalAmount(),
                demoDetails
            ));
        }

        return summaries;
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
