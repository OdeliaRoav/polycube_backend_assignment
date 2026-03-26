package com.polycube.payment.member.dto;

import com.polycube.payment.member.entity.MemberGrade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateMemberRequest {

    @NotBlank(message = "회원 이름은 필수입니다.")
    private String name;

    @NotNull(message = "회원 등급은 필수입니다.")
    private MemberGrade grade;

    public CreateMemberRequest() {
    }

    public CreateMemberRequest(String name, MemberGrade grade) {
        this.name = name;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public MemberGrade getGrade() {
        return grade;
    }
}