package agency.services.converters;


import agency.dto.EligibleMembersDTO;
import agency.dto.HeistMemberDTO;
import agency.dto.HeistSkillDTO;
import agency.entity.HeistMember;
import agency.entity.HeistSkill;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


@Component
public class EligibleMembersConverter {

    private HeistMemberConverter heistMemberConverter;
    private HeistSkillConverter heistSkillConverter;

    public EligibleMembersConverter(HeistMemberConverter heistMemberConverter, HeistSkillConverter heistSkillConverter) {
        this.heistMemberConverter = heistMemberConverter;
        this.heistSkillConverter = heistSkillConverter;
    }

    public EligibleMembersDTO toDto (Set<HeistMember> heistMemberSet, Set<HeistSkill> heistSkillsSet) {

        EligibleMembersDTO eligibleMembersDTO = new EligibleMembersDTO();

        Set<HeistMemberDTO> heistMembers = new HashSet<>();

        Set<HeistSkillDTO> heistSkillSet = new HashSet<>();


        for (HeistMember heistMember : heistMemberSet) {

            heistMembers.add(heistMemberConverter.toDto(heistMember));
        }

        for (HeistSkill heistSkill : heistSkillsSet) {

            heistSkillSet.add(heistSkillConverter.toDto(heistSkill));
        }

        eligibleMembersDTO.setMembers(heistMembers);
        eligibleMembersDTO.setSkills(heistSkillSet);


        return  eligibleMembersDTO;
    }


}
