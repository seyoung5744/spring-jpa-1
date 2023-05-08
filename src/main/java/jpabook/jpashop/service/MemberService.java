package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true) // JPA에서의 데이터 변경에 관한 로직이 있을 시, Transaction이 필수적으로 요구됨.
                                //조회만 하는 service에서 readOnly설정을 해주면 좀 더 최적화와 성능개선을 기대할 수 있다.
@RequiredArgsConstructor
public class MemberService {

//    @Autowired
//    private MemberRepository memberRepository;

    // 더 나은 Injection 방법 : Constructor Injection
    private final MemberRepository memberRepository; //final을 넣으면 컴파일 시점에 주입이 제대로 되는지 확인이 가능하다.

    /**
     * 회원 가입
     */
    @Transactional // Class 레벨의 어노테이션보다 Method 레벨의 어노테이션이 우선권을 갖는다.
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findMember(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
