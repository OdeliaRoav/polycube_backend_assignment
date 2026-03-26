package com.polycube.payment.member.controller;

import com.polycube.payment.member.dto.CreateMemberRequest;
import com.polycube.payment.member.dto.MemberResponse;
import com.polycube.payment.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member", description = "회원 관련 API")
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(summary = "회원 생성", description = "회원을 실제로 생성합니다.")
    @PostMapping
    public MemberResponse createMember(@Valid @RequestBody CreateMemberRequest request) {
        return memberService.createMember(request);
    }

    @Operation(summary = "회원 단건 조회", description = "회원 ID로 실제 회원 정보를 조회합니다.")
    @GetMapping("/{memberId}")
    public MemberResponse getMember(@PathVariable Long memberId) {
        return memberService.getMember(memberId);
    }
}