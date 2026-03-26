package com.polycube.payment.member.dto;

import com.polycube.payment.member.entity.Member;
import com.polycube.payment.member.entity.MemberGrade;

public class MemberResponse {

    private Long memberId;
    private String name;
    private MemberGrade grade;

    public MemberResponse(Long memberId, String name, MemberGrade grade) {
        this.memberId = memberId;
        this.name = name;
        this.grade = grade;
    }

    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getGrade()
        );
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public MemberGrade getGrade() {
        return grade;
    }
}