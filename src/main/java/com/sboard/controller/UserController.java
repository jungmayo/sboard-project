package com.sboard.controller;

import com.sboard.config.AppInfo;
import com.sboard.dto.TermsDTO;
import com.sboard.dto.UserDTO;
import com.sboard.service.TermsService;
import com.sboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final AppInfo appInfo;
    private final UserService userService;
    private final TermsService termsService;

    @GetMapping("/user/login")
    public String login(Model model) {
        model.addAttribute(appInfo);
        return "/user/login";
    }
    @GetMapping("/user/register")
    public String register(){
        return "/user/register";
    }


    @PostMapping("/user/register")
    public String register(UserDTO userDTO){
        userService.insertUser(userDTO);
        return "redirect:/user/login";
    }

    @GetMapping("/user/terms")
    public String terms(Model model){
        List<TermsDTO> dto = termsService.selectTerms();
        model.addAttribute("terms", dto);
        return "/user/terms";
    }

}
