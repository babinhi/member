package com.icia.member.service;

import com.icia.member.dto.MemberDTO;
import com.icia.member.entity.MemberEntity;
import com.icia.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
        return memberRepository.save(memberEntity).getId();
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity memberEntity: memberEntityList) {
            memberDTOList.add(MemberDTO.toDTO(memberEntity));
        }
        return memberDTOList;
    }

    public boolean login(MemberDTO memberDTO) {
        Optional<MemberEntity> memberEntity =
                memberRepository.findByMemberEmailAndMemberPassword(memberDTO.getMemberEmail(), memberDTO.getMemberPassword());
        if (memberEntity.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    public void delete(Long id){
        memberRepository.deleteById(id);
    }

    public void update(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toUpdateEntity(memberDTO);
        memberRepository.save(memberEntity);

    }

    public void loginAxios(MemberDTO memberDTO) {
        // chaining method(체이닝 메서드) -> 꼬리에 꼬리를 무는
        memberRepository.findByMemberEmailAndMemberPassword(memberDTO.getMemberEmail(), memberDTO.getMemberPassword())
                        .orElseThrow(() -> new NoSuchElementException("이메일 또는 패스워드가 일치하지 않습니다"));
    }


    public MemberDTO findById(Long id) {
//        -> : 익명함수 처리 자바스크립트의 () 자리
        MemberEntity memberEntity = memberRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return MemberDTO.toDTO(memberEntity);
//        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
//        if(optionalMemberEntity.isPresent()){
//            MemberEntity memberEntity = optionalMemberEntity.get();
//            return MemberDTO.toDTO(memberEntity);
//        }else {
//            return null;
//        } 또 다른 표현

    }

    public MemberDTO findByMemberEmail(String loginEmail) {
        //조회를 하면서 없으면 예외처리, 있으면 MemberEntity 리턴
        MemberEntity memberEntity = memberRepository.findByMemberEmail(loginEmail).orElseThrow(()-> new NoSuchElementException());
        // 있는경우 DTO로 변환하며 컨트롤러로 변환
        return MemberDTO.toDTO(memberEntity);

    }

    public boolean emailCheck(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if(optionalMemberEntity.isEmpty()){
            return true;
        }else {
            return false;
        }
    }
}
