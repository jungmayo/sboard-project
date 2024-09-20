package com.sboard.controller;

import com.sboard.dto.UserDTO;
import com.sboard.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@RequiredArgsConstructor
@Controller
public class ArticleController {

    @GetMapping("/article/list")
    public String articleList(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        // 이제 myUserDetails를 바로 사용할 수 있습니다.
        String name = myUserDetails.getUser().getNick(); // 또는 getNick() 사용
        model.addAttribute("name", name);

        return "/article/list"; // 반환할 뷰 이름
    }

    @GetMapping("/article/modify")
    public String modify(){
        return "article/modify";
    }

    @GetMapping("/article/view")
    public String view(){
        return "article/view";
    }

    @GetMapping("/article/write")
    public String write(){
        return "article/write";
    }
}
