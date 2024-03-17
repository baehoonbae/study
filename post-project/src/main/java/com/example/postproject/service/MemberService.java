package com.example.postproject.service;

import com.example.postproject.domain.Member;
import com.example.postproject.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원 가입
    public Member joinMember(Member member) {
        return memberRepository.save(member);
    }

    // 로그인

    // 로그아웃
    // 회원 정보 조회
    // 회원 정보 수정
    // 회원 탈퇴


    // 회원 가입 시 id 중복 확인 하기
    public boolean isUsernameDuplicate(String username) {
        Member existingMember = memberRepository.findByUsername(username);
        return existingMember != null; // 입력한 id 가 이미 존재할 시 true 반환
    }

    // 회원 가입 시 닉네임 중복 확인 하기
    public boolean isNicknameDuplicate(String nickname) {
        Member existingMember = memberRepository.findByNickname(nickname);
        return existingMember != null; // 입력한 nickname 가 이미 존재할 시 true 반환
    }

}