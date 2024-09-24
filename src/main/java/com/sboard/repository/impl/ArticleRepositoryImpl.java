package com.sboard.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sboard.dto.PageRequestDTO;
import com.sboard.entity.QArticle;
import com.sboard.entity.QUser;
import com.sboard.repository.custom.ArticleRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Repository
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private QArticle qarticle = QArticle.article;
    private QUser quser = QUser.user;

    @Override
    public Page<Tuple> selectArticleAllForList(PageRequestDTO pagerequestDTO, Pageable pageable) {

        List<Tuple> content = queryFactory
                                        .select(qarticle, quser.nick) // 선별, 큐 아티클의 모든 속성 + 큐 유저의 닉
                                        .from(qarticle)
                                        .join(quser)
                                        .on(qarticle.writer.eq(quser.uid))
                                        .offset(pageable.getOffset())
                                        .limit(pageable.getPageSize())
                                        .orderBy(qarticle.no.desc())
                                        .fetch();


        long total = queryFactory
                .select(qarticle.count())
                .from(qarticle)
                .fetchOne(); // 전체 데이터 개수

        //페이징 처리를 위해 page 객체 리턴
        return new PageImpl<Tuple>(content, pageable, total); //pageable : 요청한 페이지의 정보 ( 개수, 크기, 번호 정렬 방식을 위해 필요)

    }

    @Override
    public Page<Tuple> selectArticleForSearch(PageRequestDTO pagerequestDTO, Pageable pageable) {

        String type = pagerequestDTO.getType();
        String keyword = pagerequestDTO.getKeyword();

        // 검색 선택 조건에 따라 where 조건 표현식 생성
        BooleanExpression expression = null;

        if(type.equals("title")){
            expression = qarticle.title.contains(keyword); // title like '%keyword'을 의미
            log.info("expression : " + expression);

        }else if(type.equals("content")){
            expression = qarticle.content.contains(keyword);

        }else if(type.equals("title_content")){
            BooleanExpression titleexpression = qarticle.title.contains(keyword);
            BooleanExpression contentexpression = qarticle.content.contains(keyword);

            expression = titleexpression.or(contentexpression);
            log.info("expression : " + expression);

        }else if(type.equals("writer")){
            expression = quser.nick.contains(keyword);
            log.info("expression : " + expression);
        }

        List<Tuple> content = queryFactory
                                .select(qarticle, quser.nick) // 선별, 큐 아티클의 모든 속성 + 큐 유저의 닉
                                .from(qarticle)
                                .join(quser)
                                .on(qarticle.writer.eq(quser.uid))
                                .where(expression)
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .orderBy(qarticle.no.desc())
                                .fetch();


        long total = queryFactory
                .select(qarticle.count())
                .from(qarticle)
                .where(expression)
                .join(quser)
                .on(qarticle.writer.eq(quser.uid))
                .fetchOne(); // 전체 데이터 개수

        //페이징 처리를 위해 page 객체 리턴
        return new PageImpl<Tuple>(content, pageable, total);

    }

}
