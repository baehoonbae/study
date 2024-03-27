package com.example.postproject.controller;

import com.example.postproject.domain.Member;
import com.example.postproject.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @GetMapping(value = "/login")
    public String loginPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "아이디 혹은 비밀번호를 확인해 주세요.");
        }
        return "login-page";
    }

    // 로그인
    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestParam String username,
                                        @RequestParam String password,
                                        HttpSession session,
                                        Model model) {
        // 사용자 인증
        boolean isAuthenticated = memberService.authenticateUser(username, password);
        if (isAuthenticated) {
            // 로그인 성공 시 세션에 사용자 정보 저장
            session.setAttribute("username", username);

            // 로그인 된 상태로 변경
            model.addAttribute("isLoggedIn", true);

            return ResponseEntity.ok().body("Login Successful");
        } else {
            // 인증 실패 시 에러 메시지 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    // 로그아웃
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/logout")
    public String logout(HttpSession session, Model model) {

        // 세션 무효화
        session.invalidate();

        // 로그아웃 상태로 변경
        model.addAttribute("isLoggedIn", false);

        return "redirect:/"; // 로그아웃 후 리다이렉트할 경로
    }
}
