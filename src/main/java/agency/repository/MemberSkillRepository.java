package agency.repository;

import agency.entity.HeistMember;
import agency.entity.MemberSkill;
import agency.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberSkillRepository extends JpaRepository<MemberSkill, Long> {

    int deleteMemberSkillByMemberAndSkill(HeistMember member, Skill skill);


}
