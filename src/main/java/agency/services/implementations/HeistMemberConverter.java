package agency.services.implementations;

import agency.dto.HeistMemberDTO;
import agency.dto.MemberSkillDTO;
import agency.entity.HeistMember;
import agency.entity.MemberSkill;
import agency.repository.HeistMemberRepository;
import agency.repository.MemberSkillRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Component
public class HeistMemberConverter {

    MemberSkillRepository memberSkillRepository;
    MemberSkillConverter memberSkillConverter;

    public HeistMemberConverter(MemberSkillRepository memberSkillRepository, MemberSkillConverter memberSkillConverter) {
        this.memberSkillRepository = memberSkillRepository;
        this.memberSkillConverter = memberSkillConverter;
    }

    public HeistMemberDTO toDto(HeistMember heistMember) {

        HeistMemberDTO heistMemberDTO = new HeistMemberDTO();

        heistMemberDTO.setEmail(heistMember.getEmail());
        heistMemberDTO.setName(heistMember.getName());
        heistMemberDTO.setSex(heistMember.getSex());
        heistMemberDTO.setStatus(heistMember.getStatus());
        heistMemberDTO.setMainSkill(heistMember.getMainSkill().getName());

        Set<MemberSkill> memberSkillsByMember = memberSkillRepository.findMemberSkillsByMember(heistMember);
        Set<MemberSkillDTO> skills = new HashSet<>();

        for (MemberSkill memberSkills : memberSkillsByMember) {

            skills.add(memberSkillConverter.toDto(memberSkills));
        }
        heistMemberDTO.setSkills(skills);

        return heistMemberDTO;

    }
}


