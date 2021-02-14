package agency.services.implementations;

import agency.entity.HeistMember;
import agency.enumeration.Status;
import agency.repository.HeistMemberRepository;
import agency.repository.HeistRepository;
import agency.repository.HeistSkillRepository;
import agency.repository.MemberSkillRepository;
import agency.services.interfaces.ConfirmMembersForHeistService;
import agency.services.interfaces.MemberSkillService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfirmMembersForHeistServiceImpl implements ConfirmMembersForHeistService {

    HeistRepository heistRepository;
    HeistMemberRepository heistMemberRepository;
    HeistSkillRepository heistSkillRepository;
    MemberSkillRepository memberSkillRepository;
    MemberSkillService memberSkillService;

    public ConfirmMembersForHeistServiceImpl(HeistRepository heistRepository, HeistMemberRepository heistMemberRepository, HeistSkillRepository heistSkillRepository,
                                             MemberSkillRepository memberSkillRepository, MemberSkillService memberSkillService) {
        this.heistRepository = heistRepository;
        this.heistMemberRepository = heistMemberRepository;
        this.heistSkillRepository = heistSkillRepository;
        this.memberSkillRepository = memberSkillRepository;
        this.memberSkillService = memberSkillService;
    }

    @Override
    public void updateHeistMemberForHeistIfMeetsRequiredConditions(List<HeistMember> heistMemberList, String name) {

        if (heistMemberList.size() > 1 || name.matches(name)) {

            for (HeistMember heistMember : heistMemberList) {

                if (heistMember.getStatus().toString().matches(Status.AVAILABLE.toString())) {

                }
            }
        }
    }


}

