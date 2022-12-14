package study.datajpa.MemberRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberQuerydslRepository;
import study.datajpa.repository.MemberJpaRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    MemberQuerydslRepository memberQuerydslRepository;

    @Test
    public void testMember(){
        Member member = new Member("memberA");
        Member savedMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(savedMember.getId());
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUserName()).isEqualTo(member.getUserName());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUD(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        //단건 조회 검증
        Member findMember1 = memberJpaRepository.find(member1.getId());
        Member findMember2 = memberJpaRepository.find(member2.getId());
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        //리스트 조회 검증
        List<Member> all = memberJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        //카운트 검증
        long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2);

        //삭제 검증
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);

        long deletedCount = memberJpaRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    public void findByUserNameAndAgeGreaterThen(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);

        List<Member> result = memberJpaRepository.findByUserNameAndAgeGreaterThen("AAA", 15);

        assertThat(result.get(0).getUserName()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }

  @Test
  public void paging() throws Exception{
      //given
      memberJpaRepository.save(new Member("member1", 10));
      memberJpaRepository.save(new Member("member2", 10));
      memberJpaRepository.save(new Member("member3", 10));
      memberJpaRepository.save(new Member("member4", 10));
      memberJpaRepository.save(new Member("member5", 10));

      int age = 10;
      int offset = 0;
      int limit = 3;

      //when
      List members = memberJpaRepository.findByPage(age, offset, limit);
      long totalCount = memberJpaRepository.totalCount(age);
      // 페이지 계산 공식 적용...
      // totalPage = totalCnt / size...
      // 마지막 페이지
      // 최초 페이지

      //then
      assertThat(members.size()).isEqualTo(3);
      assertThat(totalCount).isEqualTo(5);

  }

  @Test
  public void bulkUpdate() throws Exception{
      //given
      memberJpaRepository.save(new Member("member1", 10));
      memberJpaRepository.save(new Member("member2", 19));
      memberJpaRepository.save(new Member("member3", 20));
      memberJpaRepository.save(new Member("member4", 21));
      memberJpaRepository.save(new Member("member5", 40));
      //when
      int resultCount = memberJpaRepository.bulkAgePlus(20);
      //then
      assertThat(resultCount).isEqualTo(3);

  }

  @Test
  public void basicTest(){
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