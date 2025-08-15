package product.demo_wave.demo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

import product.demo_wave.entity.Comment;
import product.demo_wave.entity.Demo;
import product.demo_wave.entity.Participant;
import product.demo_wave.entity.User;
import product.demo_wave.logic.GetUserLogic;
import product.demo_wave.repository.CommentRepository;
import product.demo_wave.repository.DemoRepository;
import product.demo_wave.repository.ParticipantRepository;
import product.demo_wave.repository.PaymentRepository;
import product.demo_wave.common.annotation.CustomRetry;
import product.demo_wave.common.logic.BasicFacadeDBLogic;
import product.demo_wave.repository.UserRepository;
import product.demo_wave.repository.CategoryRepository;
import product.demo_wave.repository.PrefectureRepository;
import product.demo_wave.entity.Category;
import product.demo_wave.entity.Prefecture;

@Component
@AllArgsConstructor
class DemoFacadeDBLogic extends BasicFacadeDBLogic {
    private final DemoRepository demoRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ParticipantRepository participantRepository;
    private final PaymentRepository paymentRepository;
    private final CategoryRepository categoryRepository;
    private final PrefectureRepository prefectureRepository;
    private final GetUserLogic getUserLogic; // GetUserLogicをフィールドとして追加

//    @CustomRetry
//    PageData<Demo> fetchAllDemo(Pageable pageable) {
//        LocalDateTime now = LocalDateTime.now();
//        Page<Demo> demos = demoRepository.findByAnnouncementTimeBeforeOrderByAnnouncementTimeDesc(now, pageable);
//        int range = getPageDisplayRange(demos);
//        return new PageData<>(demos, range);
//    }

    @CustomRetry
    PageData<DemoWithParticipantDTO> fetchAllDemo(Pageable pageable) {
        LocalDateTime now = LocalDateTime.now();
        Page<DemoWithParticipantDTO> demos =
                demoRepository.findDemoWithParticipantCounts(now, pageable);
        int range = getPageDisplayRange(demos);
        return new PageData<>(demos, range);
    }

//    Integer fetchParticipantCount() {
//        List<Integer> participantCount = participantRepository.participantCount();
//        return participantCount;
//    }

    @CustomRetry
    Demo fetchDemo(Integer demoId) {
        Optional<Demo> demo = demoRepository.findById(demoId);
        return demo.orElse(new Demo());
    }

    @CustomRetry
    List<Comment> fetchComment(Integer demoId) {
        List<Comment> comments = commentRepository.findByDemoId(demoId);
        return comments;
    }

    User fetchUser(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(new User());
    }

    @CustomRetry
    void saveDemo(DemoForm demoForm) {

        // Demo を保存し、保存したエンティティを取得
        Demo savedDemo = demoRepository.saveAndFlush(toEntity(demoForm, null));

        // 登録した Demo の ID を取得
        Integer demoId = savedDemo.getId();

        // Participant に登録
        participantRepository.saveAndFlush(toParticipantEntity(demoId));

//        demoRepository.saveAndFlush(toEntity(DemoForm, null));
    }

    private Demo toEntity(DemoForm demoForm, Integer demoId) {
        Demo demo = new Demo();

        if (demoId != null) {
            demo = demoRepository.findById(demoId)
                    .orElseThrow(() -> new NoSuchElementException("Demo not found."));
        }

//        // SecurityContextからログインユーザーの詳細情報を取得
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UsersDetails usersDetails = (UsersDetails) authentication.getPrincipal(); // UsersDetailsを取得
//        User loggedInUser = usersDetails.getUser(); // Userオブジェクトを取得

        demo.setTitle(demoForm.title());
        demo.setContent(demoForm.content());
        // Combine date and time to create LocalDateTime
        demo.setDemoStartDate(demoForm.demoDate().atTime(demoForm.demoStartTime()));
        demo.setDemoEndDate(demoForm.demoDate().atTime(demoForm.demoEndTime()));
        demo.setDemoPlace(demoForm.demoPlace());
        demo.setDemoAddressLatitude(demoForm.demoAddressLatitude());
        demo.setDemoAddressLongitude(demoForm.demoAddressLongitude());
        demo.setUser(this.getUserLogic.getUserFromCache());
        
        // Set category from form
        Category category = categoryRepository.findById(demoForm.categoryId())
            .orElseThrow(() -> new RuntimeException("Category not found"));
        demo.setCategory(category);
        
        // Set prefecture from form
        Prefecture prefecture = prefectureRepository.findById(demoForm.prefectureId())
            .orElseThrow(() -> new RuntimeException("Prefecture not found"));
        demo.setPrefecture(prefecture);
//        demo.setOrganizerName("我如古陸");

        return demo;
    }

    private Participant toParticipantEntity(Integer demoId) {
        Participant participant = new Participant();

        participant.setDemo(fetchDemo(demoId));
        participant.setUser(fetchUser(this.getUserLogic.getUserFromCache().getId()));

        return participant;
    }

    @CustomRetry
    void saveComment(CommentForm commentForm, Integer demoId) {
        commentRepository.saveAndFlush(toEntity(commentForm, demoId));
    }

    private Comment toEntity(CommentForm commentForm, Integer demoId) {
        Comment comment = new Comment();

        comment.setDemo(fetchDemo(demoId));
        comment.setContent(commentForm.content());
        comment.setUser(fetchUser(this.getUserLogic.getUserFromCache().getId()));

        return comment;
    }

    @CustomRetry
    void toggleParticipant(Integer demoId) {

        Integer userId = this.getUserLogic.getUserFromCache().getId();

        // 該当データを検索
        Optional<Participant> existingParticipant = participantRepository.findByDemoAndUser(fetchDemo(demoId), fetchUser(userId));

        if (existingParticipant.isEmpty() || existingParticipant.get().getDeletedAt() != null) {
            // データが存在しない、または論理削除されている場合、新しいデータを作成
            participantRepository.saveAndFlush(toEntity(demoId, userId));
        } else {
            // データが存在し、論理削除されていない場合、不参加として論理削除
            Participant participant = existingParticipant.get();
            participant.setDeletedAt(java.time.LocalDateTime.now());
            participantRepository.saveAndFlush(participant);
        }

//        Boolean isParticipant = participantRepository.existsBydemoIdAndUserIdAndDeletedAtIsNull(demoId, this.getUserLogic.getUserFromCache().getId());
//        if (!isParticipant) {
//            participantRepository.saveAndFlush(toEntity(demoId, userId));
//        } else {
//
//        }
    }

    private Participant toEntity(Integer demoId, Integer userId) {
        Participant participant = new Participant();

        participant.setDemo(fetchDemo(demoId));
        participant.setUser(fetchUser(userId));

        return participant;
    }

    @CustomRetry
    public Boolean isParticipant(Integer demoId) {
        Boolean isParticipant = participantRepository.existsByDemoAndUserAndDeletedAtIsNull(fetchDemo(demoId), fetchUser(this.getUserLogic.getUserFromCache().getId()));
        return isParticipant;
    }

    @CustomRetry
    public Integer participantCount(Integer demoId) {
        Integer participantCount = participantRepository.countByDemo(fetchDemo(demoId));
        return participantCount;
    }

    @CustomRetry
    public BigDecimal donateAmount(Integer demoId) {
        BigDecimal donateAmount = paymentRepository.getTotalDonatedAmountByDemo(fetchDemo(demoId));
        return donateAmount;
    }

    @CustomRetry
    public List<Category> fetchAllCategories() {
        return categoryRepository.findAll();
    }

    @CustomRetry
    public List<Prefecture> fetchAllPrefectures() {
        return prefectureRepository.findAll();
    }

    @CustomRetry
    public Category fetchCategory(Integer categoryId) {
        return categoryRepository.findById(categoryId)
            .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @CustomRetry
    public Prefecture fetchPrefecture(Integer prefectureId) {
        return prefectureRepository.findById(prefectureId)
            .orElseThrow(() -> new RuntimeException("Prefecture not found"));
    }

}
