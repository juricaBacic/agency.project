package agency.services.implementations;

import agency.entity.Heist;
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
import java.util.Optional;

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

        //provjeriti status heist membera koji se nalazi u listi (pustiti dalje samo status AVAILABLE)
        //provjeriti level jeli veci ili jednak zahtjevu heista
        //da sadrzi bar jedan skill koji je naveden u listi skillova heista
        //ako su svi sa liste tu i zadovoljavaju se svi uvijeti onda promjeniti i heist u rdy

        Optional<Heist> heistOptional = heistRepository.findById(name);

        if (heistMemberList.size() > 1) {

            for (HeistMember heistMember : heistMemberList) {

                Optional<HeistMember> heistMember1 = heistMemberRepository.findById(heistMember.getName());

                if (heistMember1.get().getStatus().toString().matches(Status.AVAILABLE.toString())) {

                    //heistOptional.get()

                }
            }
        }
    }


}

