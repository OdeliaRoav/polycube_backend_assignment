package com.polycube.payment.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Member", description = "회원 관련 API")
@RestController
@RequestMapping("/members")
public class MemberController {

    @Operation(summary = "회원 생성", description = "테스트용 회원 생성 API")
    @PostMapping
    public Map<String, Object> createMember(
            @RequestParam String name,
            @RequestParam String grade
    ) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "회원 생성 성공");
        response.put("name", name);
        response.put("grade", grade);
        return response;
    }

    @Operation(summary = "회원 단건 조회", description = "회원 ID로 회원 정보를 조회하는 테스트용 API")
    @GetMapping("/{memberId}")
    public Map<String, Object> getMember(@PathVariable Long memberId) {
        Map<String, Object> response = new HashMap<>();
        response.put("memberId", memberId);
        response.put("name", "test-user");
        response.put("grade", "VIP");
        return response;
    }

    @Operation(summary = "회원 목록 조회", description = "테스트용 회원 목록 조회 API")
    @GetMapping
    public Map<String, Object> getMembers() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "회원 목록 조회 성공");
        response.put("count", 2);
        return response;
    }
}