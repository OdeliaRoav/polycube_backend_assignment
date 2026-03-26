package com.polycube.payment.member.service;

import com.polycube.payment.member.dto.CreateMemberRequest;
import com.polycube.payment.member.dto.MemberResponse;
import com.polycube.payment.member.entity.Member;
import com.polycube.payment.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public MemberResponse createMember(CreateMemberRequest request) {
        Member member = new Member(
                request.getName(),
                request.getGrade()
        );

        Member savedMember = memberRepository.save(member);
        return MemberResponse.from(savedMember);
    }

    public MemberResponse getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. id=" + memberId));

        return MemberResponse.from(member);
    }
}