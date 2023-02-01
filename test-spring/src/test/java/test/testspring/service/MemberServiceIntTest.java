package test.testspring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import test.testspring.domain.Member;
import test.testspring.repository.JPAMemberRepository;
import test.testspring.repository.MemberRepository;
import test.testspring.repository.MemoryMemberRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntTest {

    @Autowired
    private MemberService    memberService;
    @Autowired
    private MemberRepository repository;

    @DisplayName("회원가입 테스트")
    @Test
    void join() {

        Member test1 = new Member();
        test1.setName("spring1");
        Long memberId = memberService.join(test1);

        Member result = memberService.getOne(memberId).get();

        Assertions.assertThat(test1).isEqualTo(result);
    }

    @Test
    public void duplTest() {
        Member test1 = new Member();
        test1.setName("spring1");
        memberService.join(test1);

        Member test2 = new Member();
        test2.setName("spring1");

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(test2));

        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");

    }

    @Test
    void getOne() {
        Member test1 = new Member();
        test1.setName("spring1");
        Long memberId = memberService.join(test1);

        Member result = memberService.getOne(memberId).get();

        Assertions.assertThat(test1).isEqualTo(result);
    }

    @Test
    void getAll() {
    }
}