package com.example.postproject.repository;

import com.example.postproject.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUsername(String username); // 아이디로 회원 찾기

    Member findByNickname(String nickname); // 닉네임으로 회원 찾기
}
