package hello.springtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService    memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    LogRepository    logRepository;

    /**
     * MemberService : @Transactional:OFF
     * MemberRepository : @Transactional:ON
     * LogRepository : @Transactional:ON
     */
    @Test
    void outerTxOff_success() {
        //given
        String USERNAME = "outerTxOff_success";

        //when
        memberService.joinV1(USERNAME);

        //then
        assertTrue(memberRepository.find(USERNAME)
                           .isPresent());
        assertTrue(logRepository.find(USERNAME)
                           .isPresent());
    }

    @Test
    void outerTxOff_Fail() {
        //given
        String USERNAME = "로그예외_OuterTxOff_Fail";

        //when
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> memberService.joinV1(USERNAME))
                .isInstanceOf(RuntimeException.class);
        //then
        assertTrue(memberRepository.find(USERNAME)
                           .isPresent());
        assertTrue(logRepository.find(USERNAME)
                           .isEmpty());


    }
}