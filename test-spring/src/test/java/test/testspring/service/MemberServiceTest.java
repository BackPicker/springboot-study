package test.testspring.service;

import org.assertj.core.api.Assertions;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.testspring.domain.Member;
import test.testspring.repository.MemoryMemberRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemoryMemberRepository repository;
    MemberService          memberService;


    @BeforeEach
    public void beforeAct() {
        repository    = new MemoryMemberRepository();
        memberService = new MemberService(repository);
    }

    @AfterEach
    public void afterAct() {
        repository.clearStore();
    }

    @Test
    void join() {

        Member test1 = new Member();
        test1.setName("spring1");
        Long memberId = memberService.join(test1);

        Member result = memberService.findOne(memberId).get();

        Assertions.assertThat(test1).isEqualTo(result);
    }


    @Test
    public void dupliTest() {
        Member test1 = new Member();
        test1.setName("spring1");

        Member test2 = new Member();
        test2.setName("spring1");

        memberService.join(test1);


        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(test2));

        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");

    }

    @Test
    void findAll() {
        Member test1 = new Member();
        test1.setName("spring1");

        Member test2 = new Member();
        test2.setName("spring2");

        memberService.join(test1);
        memberService.join(test2);

        List<Member> result = memberService.findAll();

        Assertions.assertThat(result.size()).isEqualTo(2);

    }

    @Test
    void findOne() {
        Member test1 = new Member();
        test1.setName("spring1");

        Member test2 = new Member();
        test2.setName("spring2");

        Long memberid = memberService.join(test1);
        memberService.join(test2);

        Member result = memberService.findOne(memberid).get();

        Assertions.assertThat(test1).isEqualTo(result);

    }
}