package com.example.postproject.controller;

import com.example.postproject.domain.Member;
import com.example.postproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;


    // 회원 가입 페이지 로드
    @GetMapping(value = "/post/join")
    public String joinPage() {
        return "join-page";
    }

    // 회원 가입
    @PostMapping(value = "/post/join", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String join(@ModelAttribute Member member) {
        memberService.joinMember(member);
        return "redirect:/";
    }

    // 아이디 중복 확인
    @PostMapping(value = "/check-username")
    public ResponseEntity<String> checkUsername(@RequestParam String username) {
        if (memberService.isUsernameDuplicate(username)) {
            return ResponseEntity.ok("* 아이디: 이미 존재하는 아이디입니다.");
        } else {
            return ResponseEntity.ok("");
        }
    }

    // 닉네임 중복 확인
    @PostMapping(value = "/check-nickname")
    public ResponseEntity<String> checkNickname(@RequestParam String nickname) {
        if (memberService.isNicknameDuplicate(nickname)) {
            return ResponseEntity.ok("* 닉네임: 이미 존재하는 닉네임입니다.");
        } else {
            return ResponseEntity.ok("");
        }
    }

    // 로그인 페이지 로드
    @GetMapping(value = "/post/login")
    public String loginPage() {
        return "login-page";
    }

    // 로그인
    @PostMapping(value = "/post/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String login(@ModelAttribute Member member) {
        memberService.joinMember(member);
        return "redirect:/";
    }


}
