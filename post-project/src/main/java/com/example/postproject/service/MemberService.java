package com.example.postproject.service;

import com.example.postproject.domain.Member;
import com.example.postproject.repository.MemberRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원 가입
    @Transactional
    public void joinMember(Member member) {
        String rawPassword = member.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        member.setPassword(encodedPassword);
        memberRepository.save(member);
    }

    // 로그인 시 사용자 인증
    public boolean authenticateUser(String username, String password) {
        Member member = memberRepository.findByUsername(username);

        if (member == null) {
            return false;
        }

        return passwordEncoder.matches(password, member.getPassword());
    }

    // 로그인

    // 로그아웃
    // 회원 정보 조회
    // 회원 정보 수정
    // 회원 가입 시 id 중복 확인 하기
    // 회원 가입 시 닉네임 중복 확인 하기
    // 로그인 시 아이디, 비밀번호 인증

    // 회원 탈퇴


    public boolean isUsernameDuplicate(String username) {
        Member existingMember = memberRepository.findByUsername(username);
        return existingMember != null; // 입력한 id 가 이미 존재할 시 true 반환
    }

    public boolean isNicknameDuplicate(String nickname) {
        Member existingMember = memberRepository.findByNickname(nickname);
        return existingMember != null; // 입력한 nickname 가 이미 존재할 시 true 반환
    }
}