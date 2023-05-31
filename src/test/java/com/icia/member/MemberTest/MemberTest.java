package com.icia.member.MemberTest;

import com.icia.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("repository method 테스트")
    public void repositoryTest() {

    /*
     select => findBy
     원하는 쿼리
     select * from member_table where member_email=?
      */
        memberRepository.findByMemberEmail("aaa");

        //     select * from member_table where member_email=? and member_password=?
        memberRepository.findByMemberEmailAndMemberPassword("aaa", "1234");
    }
}
