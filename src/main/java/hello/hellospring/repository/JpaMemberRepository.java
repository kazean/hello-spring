package hello.hellospring.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.hellospring.domain.Member;
import hello.hellospring.domain.QMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static hello.hellospring.domain.QMember.*;

public class JpaMemberRepository implements MemberRepository{
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        /*Member findMember = em.find(Member.class, id);
        return Optional.ofNullable(findMember);*/
        Member findMember = queryFactory
                .selectFrom(QMember.member)
                .where(memberIdEq(id))
                .fetchOne();
        return Optional.ofNullable(findMember);
    }

    @Override
    public Optional<Member> findByName(String name) {
        /*Member findMember = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getSingleResult();
        return Optional.ofNullable(findMember);
        */
        Member findMember = queryFactory.selectFrom(QMember.member)
                .where(memberNameEq(name))
                .fetchOne();
        return Optional.ofNullable(findMember);
    }

    @Override
    public List<Member> findAll() {
        /*return em.createQuery("select m from Member m",Member.class)
                .getResultList();*/
        return queryFactory.selectFrom(member)
                .fetch();
    }

    private BooleanExpression memberNameEq(String name) {
        if(StringUtils.hasText(name)){
            return member.name.eq(name);
        }
        return null;
    }

    private BooleanExpression memberIdEq(Long id) {
        return id != null ? member.id.eq(id) : null;
    }
}
