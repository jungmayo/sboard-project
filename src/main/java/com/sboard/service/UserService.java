package com.sboard.service;

import com.sboard.dto.UserDTO;
import com.sboard.entity.User;
import com.sboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@SpringBootApplication
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private final JavaMailSender mailSender;

    public void insertUser(UserDTO userDTO){
         User user = userDTO.toEntity();
         userRepository.save(user);


    }
    public String sendEmailCode(String email) {
        // 인증코드 생성
        int code = ThreadLocalRandom.current().nextInt(100000, 1000000);

        // 이메일 기본정보
        String title = "Sboard 이메일 인증코드입니다.";
        String content = "인증코드는 " + code + "입니다.";

        // 메일 발송
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("wlgus5946@gmail.com");
        message.setTo(email);
        message.setSubject(title);
        message.setText(content);

        // 이메일 발송
        mailSender.send(message);

        return String.valueOf(code); // 인증 코드를 반환
    }

//    public String selectUser(UserDTO userDTO) {
//        Optional<User> opt = userRepository.findById(userDTO.getUid());
//
//        if(opt.isPresent()){
//            User user = opt.get();
//
//        }
//        return null;
//    }
}


