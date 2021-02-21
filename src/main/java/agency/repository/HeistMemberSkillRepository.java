package agency.repository;

import agency.entity.HeistMember;
import agency.entity.HeistMemberSkill;
import agency.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Set;

@Repository
public interface HeistMemberSkillRepository extends JpaRepository<HeistMemberSkill, Long> {

    int deleteMemberSkillByMemberAndSkill(HeistMember member, Skill skill);

    Set<HeistMemberSkill> findMemberSkillsByMember(HeistMember heistMember);

    Set<HeistMemberSkill> findMemberSkillsByMemberEmail(String email);

}
