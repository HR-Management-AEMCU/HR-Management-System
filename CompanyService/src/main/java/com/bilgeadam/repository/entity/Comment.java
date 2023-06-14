package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.enums.ECommentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@AllArgsConstructor
@SuperBuilder
@Entity
@Data
@NoArgsConstructor
public class Comment extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String userId;
    private String name;
    private String surname;
    private Long companyId;
    private String content;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ECommentStatus status = ECommentStatus.PENDING;
}
