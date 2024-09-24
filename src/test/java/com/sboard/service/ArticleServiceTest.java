package com.sboard.service;


import com.sboard.dto.PageRequestDTO;
import com.sboard.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;


@SpringBootTest
class ArticleServiceTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void selectArticleAll() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
        Pageable pageable = pageRequestDTO.getPageable("no");

//        Page<Tuple> pageAriticle = articlerepository.selec
//        System.out.println(pageArticle);

    }
}