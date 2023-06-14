package com.bilgeadam.services;

import com.bilgeadam.dto.request.CreateCommentDto;
import com.bilgeadam.dto.response.UserProfileResponseDto;
import com.bilgeadam.exception.CompanyManagerException;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.manager.IUserProfileManager;
import com.bilgeadam.repository.ICommentRepository;
import com.bilgeadam.repository.entity.Comment;
import com.bilgeadam.repository.enums.ECommentStatus;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.utility.JwtTokenProvider;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService extends ServiceManager<Comment,Long> {
    private final ICommentRepository commentRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final IUserProfileManager userProfileManager;

    public CommentService(ICommentRepository commentRepository, JwtTokenProvider jwtTokenProvider, IUserProfileManager userProfileManager) {
        super(commentRepository);
        this.commentRepository = commentRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userProfileManager = userProfileManager;
    }

    public Boolean createComment(String token, CreateCommentDto dto) {
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        if (authId.isEmpty()) {
            throw new CompanyManagerException(ErrorType.USER_NOT_FOUND);
        }
        UserProfileResponseDto userProfile = userProfileManager.findByUserProfileDto(authId.get()).getBody();
        if (dto.getCompanyId().equals(userProfile.getCompanyId())) {
            Comment comment = Comment.builder()
                    .userId(userProfile.getUserId())
                    .name(userProfile.getName())
                    .surname(userProfile.getSurname())
                    .content(dto.getContent())
                    .companyId(dto.getCompanyId())
                    .build();
            save(comment);
            return true;
        } else {
            throw new CompanyManagerException(ErrorType.AUTHORIZATION_ERROR);
        }
    }

    public Boolean adminCommentApproval(String token, Long commentId, Boolean action) {
                List<String> adminRole = jwtTokenProvider.getRoleFromToken(token);
        System.out.println(adminRole.stream().toList());
                if(adminRole.contains(ERole.ADMIN.toString())){
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            if (action) {
                comment.setStatus(ECommentStatus.APPROVED);
            } else {
                comment.setStatus(ECommentStatus.DISAPPROVED);
            }
            commentRepository.save(comment);
            return true;
        }
                    throw new CompanyManagerException(ErrorType.COMMENT_NOT_FOUND);
                }

        throw new CompanyManagerException(ErrorType.AUTHORIZATION_ERROR);
    }

}
