package com.sboard.entity;

import com.sboard.dto.ArticleDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.IgnoreForBinding;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    private String cate;
    private String title;
    private String content;
    private int comment;
    private int file;
    private int hit;
    private String writer;
    private String regip;

    @CreationTimestamp
    private LocalDateTime rdate;

    //추가필드
    @Transient // entity의 속성에서 제외시키는 어노테이션 , nick이라는 컬럼 생성을 안함
    private String nick;

    // join(dsl) 대신 관계설정
    @OneToMany(mappedBy = "ano") // mappedBY는 매핑되는 엔티티(테이블)의 FK(외래키) 컬럼 // 현재는 Article로 조회할 예정이므로 FileEntitiy에는 아직 관계 설정할 필요가 없음
    private List<FileEntity> filelist;

    @OneToMany(mappedBy = "parent")
    private List<Comment> commentList;
    /*
    DTO 변환 메서드 대신 ModelMapper 사용

    public ArticleDTO toDTO(){
        return ArticleDTO.builder()
                .no(no)
                .cate(cate)
                .title(title)
                .build();
    }
    */

}