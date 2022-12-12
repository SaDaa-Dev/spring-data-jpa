package study.datajpa.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberQuerydslRepositoryTest {

    EntityManager em;

    @Autowired
    MemberQuerydslRepository memberQuerydslRepository;

    @Test
    public void basicTest() throws Exception{
        Member member = new Member("member1", 10);
        memberQuerydslRepository.save(member);

        Member findMember = memberQuerydslRepository.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);

        List<Member> result1 = memberQuerydslRepository.findAll_Querydsl();
        assertThat(result1).containsExactly(member);

        List<Member> result2 = memberQuerydslRepository.findByUserName_Querydsl("member1");
        assertThat(result2).containsExactly(member);
    }






}