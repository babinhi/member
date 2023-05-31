package com.icia.member.repository;


import com.icia.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
//     interface에서 정의할수 있는 메서드는 추상메서드(실행블럭이 없는 리턴타입, 매개변수만 정의가능)
    /*
     select => findBy
     원하는 쿼리
     select * from member_table where member_email=?
      */


    Optional<MemberEntity> findByMemberEmail(String memberEmail);
    // 원하는 쿼리
    //     select * from member_table where member_email=? and member_password=?
    Optional<MemberEntity> findByMemberEmailAndMemberPassword(String memberEmail, String memberPassword);

    // 대소문자 중요함 (틀릴경우 찾지 못할수도 있다)
}
