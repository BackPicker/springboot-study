package test.core.order;

import test.core.discount.DiscountPolicy;
import test.core.discount.RateDiscountPolicy;
import test.core.member.MemberRepository;
import test.core.member.MemberRepositoryImpl;
import test.core.member.MemberVo;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository ;
    private final DiscountPolicy   discountPolicy ;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy   = discountPolicy;
    }

    @Override
    public OrderVo createOrder(Long memberId, String itemName, int itemPrice) {
        MemberVo member   = memberRepository.findById(memberId);
        int      discount = discountPolicy.discount(member, itemPrice);

        return new OrderVo(member.getId(), itemName, itemPrice, discount);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
