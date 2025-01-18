package product.demo_wave.information;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

import product.demo_wave.entity.Comment;
import product.demo_wave.entity.Information;
import product.demo_wave.entity.Participant;
import product.demo_wave.entity.User;
import product.demo_wave.logic.GetUserLogic;
import product.demo_wave.repository.CommentRepository;
import product.demo_wave.repository.ParticipantRepository;
import product.demo_wave.repository.PaymentRepository;
import product.demo_wave.security.UsersDetails;
import product.demo_wave.security.UsersDetailsService;
import product.demo_wave.repository.InformationRepository;
import product.demo_wave.common.annotation.CustomRetry;
import product.demo_wave.common.logic.BasicFacadeDBLogic;

@Component
@AllArgsConstructor
class InformationFacadeDBLogic extends BasicFacadeDBLogic {
    private final InformationRepository informationRepository;
    private final CommentRepository commentRepository;
    private final ParticipantRepository participantRepository;
    private final PaymentRepository paymentRepository;
    private final GetUserLogic getUserLogic; // GetUserLogicをフィールドとして追加

//    @CustomRetry
//    PageData<Information> fetchAllInformation(Pageable pageable) {
//        LocalDateTime now = LocalDateTime.now();
//        Page<Information> informations = informationRepository.findByAnnouncementTimeBeforeOrderByAnnouncementTimeDesc(now, pageable);
//        int range = getPageDisplayRange(informations);
//        return new PageData<>(informations, range);
//    }

    @CustomRetry
    PageData<InformationWithParticipantDTO> fetchAllInformation(Pageable pageable) {
        LocalDateTime now = LocalDateTime.now();
        Page<InformationWithParticipantDTO> informations =
                informationRepository.findInformationWithParticipantCountsBeforeAnnouncementTime(now, pageable);
        int range = getPageDisplayRange(informations);
        return new PageData<>(informations, range);
    }

//    Integer fetchParticipantCount() {
//        List<Integer> participantCount = participantRepository.participantCount();
//        return participantCount;
//    }

    @CustomRetry
    Information fetchInformation(Integer informationId) {
        Optional<Information> information = informationRepository.findById(informationId);
        return information.orElse(new Information());
    }

    @CustomRetry
    List<Comment> fetchComment(Integer informationId) {
        List<Comment> comments = commentRepository.findByInformationId(informationId);
        return comments;
    }

    @CustomRetry
    void saveInformation(InformationForm informationForm) {

        // Information を保存し、保存したエンティティを取得
        Information savedInformation = informationRepository.saveAndFlush(toEntity(informationForm, null));

        // 登録した Information の ID を取得
        Integer informationId = savedInformation.getId();

        // Participant に登録
        participantRepository.saveAndFlush(toParticipantEntity(informationId));

//        informationRepository.saveAndFlush(toEntity(informationForm, null));
    }

    private Information toEntity(InformationForm informationForm, Integer informationId) {
        Information information = new Information();

        if (informationId != null) {
            information = informationRepository.findById(informationId)
                    .orElseThrow(() -> new NoSuchElementException("Information not found."));
        }

//        // SecurityContextからログインユーザーの詳細情報を取得
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UsersDetails usersDetails = (UsersDetails) authentication.getPrincipal(); // UsersDetailsを取得
//        User loggedInUser = usersDetails.getUser(); // Userオブジェクトを取得

        information.setTitle(informationForm.title());
        information.setContent(informationForm.content());
        information.setAnnouncementTime(informationForm.announcementTime());
        information.setDemoDate(informationForm.demoDate());
        information.setDemoPlace(informationForm.demoPlace());
        information.setDemoAddress(informationForm.demoAddress());
        information.setDemoAddressLatitude(informationForm.demoAddressLatitude());
        information.setDemoAddressLongitude(informationForm.demoAddressLongitude());
        information.setOrganizerUserId(this.getUserLogic.getUserFromCache().getId());
//        information.setOrganizerName("我如古陸");

        return information;
    }

    private Participant toParticipantEntity(Integer informationId) {
        Participant participant = new Participant();

        participant.setInformationId(informationId);
        participant.setUserId(this.getUserLogic.getUserFromCache().getId());

        return participant;
    }

    @CustomRetry
    void saveComment(CommentForm commentForm, Integer informationId) {
        commentRepository.saveAndFlush(toEntity(commentForm, informationId));
    }

    private Comment toEntity(CommentForm commentForm, Integer informationId) {
        Comment comment = new Comment();

        comment.setInformationId(informationId);
        comment.setContent(commentForm.content());
        comment.setUserId(this.getUserLogic.getUserFromCache().getId());

        return comment;
    }

    @CustomRetry
    void toggleParticipant(Integer informationId) {

        Integer userId = this.getUserLogic.getUserFromCache().getId();

        // 該当データを検索
        Optional<Participant> existingParticipant = participantRepository.findByInformationIdAndUserId(informationId, userId);

        if (existingParticipant.isEmpty() || existingParticipant.get().getDeletedAt() != null) {
            // データが存在しない、または論理削除されている場合、新しいデータを作成
            participantRepository.saveAndFlush(toEntity(informationId, userId));
        } else {
            // データが存在し、論理削除されていない場合、不参加として論理削除
            Participant participant = existingParticipant.get();
            participant.setDeletedAt(java.time.LocalDateTime.now());
            participantRepository.saveAndFlush(participant);
        }

//        Boolean isParticipant = participantRepository.existsByInformationIdAndUserIdAndDeletedAtIsNull(informationId, this.getUserLogic.getUserFromCache().getId());
//        if (!isParticipant) {
//            participantRepository.saveAndFlush(toEntity(informationId, userId));
//        } else {
//
//        }
    }

    private Participant toEntity(Integer informationId, Integer userId) {
        Participant participant = new Participant();

        participant.setInformationId(informationId);
        participant.setUserId(userId);

        return participant;
    }

    @CustomRetry
    public Boolean isParticipant(Integer informationId) {
        Boolean isParticipant = participantRepository.existsByInformationIdAndUserIdAndDeletedAtIsNull(informationId, this.getUserLogic.getUserFromCache().getId());
        return isParticipant;
    }

    @CustomRetry
    public Integer participantCount(Integer informationId) {
        Integer participantCount = participantRepository.countByInformationId(informationId);
        return participantCount;
    }

    @CustomRetry
    public BigDecimal donateAmount(Integer informationId) {
        BigDecimal donateAmount = paymentRepository.getTotalDonatedAmountByInformationId(informationId);
        return donateAmount;
    }

}
