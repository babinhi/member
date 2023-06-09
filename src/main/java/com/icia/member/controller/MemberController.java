package com.icia.member.controller;


import com.icia.member.dto.MemberDTO;
import com.icia.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")

public class MemberController {
    private final MemberService memberService;

    @GetMapping("/save")
    public String saveForm() {
        return "memberPages/memberSave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        memberService.save(memberDTO);
        return "index";
    }

    @GetMapping("/")
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "memberPages/memberList";
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "redirectURI", defaultValue = "/member/mypage") String redirectURI
                            , Model model) {
        model.addAttribute("redirectURI", redirectURI);
        return "memberPages/memberLogin";
    }

    @PostMapping("/login")
    public String memberLogin(@ModelAttribute MemberDTO memberDTO, HttpSession session,
                              @RequestParam("redirectURI") String redirectURI) {
        boolean loginResult = memberService.login(memberDTO);
        if (loginResult) {
            session.setAttribute("loginEmail", memberDTO.getMemberEmail());
//            return "memberPages/memberMain";
            // 로그인 성공하면 사용자가 직전에 요청항 주소로 redirect
            return "redirect:"+redirectURI;
        } else {
            return "memberPages/memberLogin";
        }
    }
//    @GetMapping("/update/{id}")
//    public String updateForm(@PathVariable Long id, Model model){
//        MemberDTO memberDTO = memberService.findById(id);
//        model.addAttribute("member", memberDTO);
//        return "memberPages/memberUpdate";
//    }
//    @PostMapping("/update")
//    public String update(@ModelAttribute MemberDTO memberDTO) {
//        memberService.update(memberDTO);
//        return "memberPages/memberList";
//    }

    //    @GetMapping("/delete/{id}")
//    public String delete(@PathVariable Long id) {
//        memberService.delete(id);
//        return "redirect:/";
//    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 세션에 담긴 값 전체 삭제
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/login/axios")
    public ResponseEntity memberLoginAxios(@RequestBody MemberDTO memberDTO, HttpSession session) throws Exception {
        memberService.loginAxios(memberDTO);
        session.setAttribute("loginEmail", memberDTO.getMemberEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/axios/{id}")
    public ResponseEntity datailAxios(@PathVariable Long id) throws Exception {
        MemberDTO memberDTO = memberService.findById(id);
        return new ResponseEntity<>(memberDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        memberService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping("/update/{id}")
//    public String updateForm(@PathVariable Long id, Model model) {
//        MemberDTO memberDTO = memberService.findById(id);
//        model.addAttribute("member", memberDTO);
//        return "memberPages/memberUpdate";
//    }


//    @PostMapping("/update")
//    public String update(@ModelAttribute MemberDTO memberDTO) {
//        memberService.update(memberDTO);
//        return "memberPages/memberList";
//    }

    @PutMapping("/update/axios")
    public ResponseEntity updateAxios(@RequestBody MemberDTO memberDTO) throws Exception {
        memberService.update(memberDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 아래는 선생님
    @GetMapping("/mypage")
    public String myPage() {
        return "memberPages/memberMain";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "memberPages/memberDetail";
    }

    @GetMapping("/update")
    public String updateForm(HttpSession session, Model model) {
        String loginEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.findByMemberEmail(loginEmail);
        model.addAttribute("member", memberDTO);
//        model.addAttribute("member", memberService.findByMemberEmail(loginEmail)); 위에 두줄을 한번에 처리하는 방법

        return "memberPages/memberUpdate";
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("dup-check")
    public ResponseEntity emailcheck(@RequestBody MemberDTO memberDTO){
//        memberService.findByMemberEmail(memberDTO.getMemberEmail());
//        return new ResponseEntity<>(HttpStatus.OK);
        boolean result = memberService.emailCheck(memberDTO.getMemberEmail());
        if(result){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
