package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository= new MemoryMemberRepository();

    @AfterEach
    public void after(){
        repository.clearStore();
    }

    @Test
    public void save(){
//        given
        Member member = new Member();
        member.setName("spring");

//        when
        repository.save(member);

//        then
        Member findMember = repository.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void findByName(){
//        given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);
        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

//        when
        Member findMember = repository.findByName("spring1").get();

//        then
        assertThat(findMember).isEqualTo(member1);
    }

    @Test
    public void findAll(){
//        given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);
        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

//        when
        List<Member> result = repository.findAll();

//        then
        assertThat(result).extracting("name").containsExactly("spring1","spring2");
        assertThat(result.size()).isEqualTo(2);
    }

}