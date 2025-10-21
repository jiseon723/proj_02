package com._2.proj_02.domain.member.repository;

import com._2.proj_02.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
