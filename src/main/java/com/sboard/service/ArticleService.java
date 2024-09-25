package com.sboard.service;

import com.querydsl.core.Tuple;
import com.sboard.dto.ArticleDTO;
import com.sboard.dto.PageRequestDTO;
import com.sboard.dto.PageResponseDTO;
import com.sboard.entity.Article;
import com.sboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    public int insertArticle(ArticleDTO articleDTO) {

        // ModelMapper를 이용해서 DTO를 Entity로 변환
        Article article = modelMapper.map(articleDTO, Article.class);
        log.info(article);

        // 저장
        Article savedArticle = articleRepository.save(article);

        // 저장된 글번호 리턴
        return savedArticle.getNo();
    }

    public ArticleDTO selectArticle(int no){
        Optional<Article> optArticle = articleRepository.findById(no);

        if(optArticle.isPresent()){
            Article article = optArticle.get();
            log.info(article);

            ArticleDTO articleDTO = modelMapper.map(article, ArticleDTO.class);
            return articleDTO;
        }

        return null;
    }




    public PageResponseDTO selectArticleAll(PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable("no"); //pg-1 , size , no의 내림차순 정렬

        Page<Tuple> pageArticle = null;
        // 엔티티 조회
        //List<Article> articles = articleRepository.findAll();
        if(pageRequestDTO.getKeyword() == null) {
            // 일반 글 목록 조회
            pageArticle = articleRepository.selectArticleAllForList(pageRequestDTO, pageable);
        }else {
            pageArticle = articleRepository.selectArticleForSearch(pageRequestDTO, pageable);
        }
        // 튜플 리스트 -> DTO 리스트 변환 // 1개의 리스트에 10개의 튜플이 있고, 튜플안에 여러개의 키밸류가있음(no=1,title=머시기)-> 이 값은 article이고 뒤의 따로 정의한 Nick은 String으로 , 따라서 0은 article, 1은 nick이 된다.
        List<ArticleDTO> articleList = pageArticle.getContent().stream().map(tuple -> {
            Article article = tuple.get(0, Article.class); //entity에 있는 값을 받아오는 Article.class / Article 타입으로 알아서 Article에 해당 하는 값을 가져온다는 의미 / class -> .앞에 있는 해당 타입으로 값을 받아온다는 의미
            String nick = tuple.get(1, String.class);
            article.setNick(nick);

            return modelMapper.map(article, ArticleDTO.class);


        }).toList();

        int total = (int) pageArticle.getTotalElements(); // 튜플이 몇개있는지 (10개) 한페이지에 보여지는 글 갯수

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(articleList)
                .total(total)
                .build();
    }



    public void updateArticle(ArticleDTO articleDTO) {

    }

    public void deleteArticle(int no){

    }
}
