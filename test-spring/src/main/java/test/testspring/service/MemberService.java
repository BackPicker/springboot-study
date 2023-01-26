package test.testspring.service;

import test.testspring.domain.Member;
import test.testspring.repository.MemberRepository;
import test.testspring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(Member member) {

        memberDuplicate(member);

        memberRepository.save(member);


        return member.getId();
    }

    private void memberDuplicate(Member member) {
        memberRepository.findbyName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    public List<Member> findAll() {


        return memberRepository.findAll();

    }

    public Optional<Member> findOne(Long memberId) {


        return memberRepository.findbyId(memberId);
    }
}
