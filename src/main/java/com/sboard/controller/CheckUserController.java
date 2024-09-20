package com.sboard.controller;

import com.google.gson.JsonObject;
import com.sboard.repository.UserRepository;
import com.sboard.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CheckUserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/user/checkUser")
    public ResponseEntity checkUser(@RequestParam String type, @RequestParam String value) {
        if("uid".equals(type)) {
            boolean result = userRepository.existsByuid(value);
            return ResponseEntity.ok(result ? 1 : 0);
        }
        else if("nick".equals(type)) {
            boolean result = userRepository.existsByNick(value);
            return ResponseEntity.ok(result ? 1 : 0);
        }
        else if("hp".equals(type)) {
            boolean result = userRepository.existsByHp(value);
            return ResponseEntity.ok(result ? 1 : 0);
        }

        return ResponseEntity.badRequest().body("유효하지 않은 요청입니다");

    }
    @PostMapping("/user/email")
    public ResponseEntity<Map<String, Object>> sendEmailCode(@RequestParam String email, HttpSession session) {
        int result = userRepository.existsByEmail(email) ? 1 : 0;
        Map<String, Object> json = new HashMap<>();

        if (result == 0) {
            String code = userService.sendEmailCode(email);
            session.setAttribute("code", code);
            json.put("result", 0); // 코드 발송 성공
        } else {
            json.put("result", 1); // 이메일이 이미 존재
        }

        return ResponseEntity.ok(json);
    }


    @PostMapping("/user/auth")
    public ResponseEntity<Map<String, Object>> authEmailCode(@RequestParam String auth, HttpSession session) {
        String code = (String) session.getAttribute("code");
        Map<String, Object> json = new HashMap<>();

        if (code != null && code.equals(auth)) {
            json.put("result", 1); // 인증 코드가 일치
        } else {
            json.put("result", 0); // 인증 코드 불일치
        }

        return ResponseEntity.ok(json);
    }


}
