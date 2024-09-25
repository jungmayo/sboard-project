package com.sboard.service;

import com.sboard.dto.CommentDTO;
import com.sboard.entity.Comment;
import com.sboard.entity.User;
import com.sboard.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

     public CommentDTO insertComment(CommentDTO commentDTO){


         Comment comment = modelMapper.map(commentDTO, Comment.class);

         User user = User.builder()
                        .uid(commentDTO.getWriter())
                        .build();
         comment.setUser(user);

         Comment savedComment = commentRepository.save(comment); // save는 insert후 select함 (리턴값이 있을 수 있음)

         return modelMapper.map(savedComment, CommentDTO.class);

     }
    public List<CommentDTO> selectCommentAll(){
         return null;
    }
    public CommentDTO selectComment(int no){
         return null;
    }
    public void updateComment(CommentDTO commentDTO){

    }

    public void deleteComment(int no){

    }
}
