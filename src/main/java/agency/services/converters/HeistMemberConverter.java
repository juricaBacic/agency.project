package agency.services.converters;

import agency.dto.HeistMemberDTO;
import agency.dto.HeistMemberSkillDTO;
import agency.entity.HeistMember;
import agency.entity.HeistMemberSkill;
import agency.repository.HeistMemberSkillRepository;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

@Component
public class HeistMemberConverter {

    HeistMemberSkillRepository heistMemberSkillRepository;
    MemberSkillConverter memberSkillConverter;

    public HeistMemberConverter(HeistMemberSkillRepository heistMemberSkillRepository, MemberSkillConverter memberSkillConverter) {
        this.heistMemberSkillRepository = heistMemberSkillRepository;
        this.memberSkillConverter = memberSkillConverter;
    }

    public HeistMemberDTO toDto(HeistMember heistMember) {

        HeistMemberDTO heistMemberDTO = new HeistMemberDTO();

        heistMemberDTO.setEmail(heistMember.getEmail());
        heistMemberDTO.setName(heistMember.getName());
        heistMemberDTO.setSex(heistMember.getSex());
        heistMemberDTO.setStatus(heistMember.getStatus());
        heistMemberDTO.setMainSkill(heistMember.getMainSkill().getName());

        Set<HeistMemberSkill> memberSkillsByHeistMember = heistMemberSkillRepository.findMemberSkillsByMember(heistMember);
        Set<HeistMemberSkillDTO> skills = new HashSet<>();

        for (HeistMemberSkill heistMemberSkills : memberSkillsByHeistMember) {

            skills.add(memberSkillConverter.toDto(heistMemberSkills));
        }
        heistMemberDTO.setSkills(skills);

        return heistMemberDTO;

    }
}


