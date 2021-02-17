package agency.services.implementations;


import agency.dto.EligibleMembersDTO;
import agency.entity.Heist;
import agency.entity.HeistMember;


import agency.entity.HeistSkill;
import agency.entity.MemberSkill;
import agency.enumeration.Status;
import agency.repository.HeistMemberRepository;
import agency.repository.HeistRepository;
import agency.repository.HeistSkillRepository;
import agency.repository.MemberSkillRepository;
import agency.services.converters.EligibleMembersConverter;
import agency.services.interfaces.HeistMemberService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class HeistMemberServiceImpl implements HeistMemberService {

    private HeistMemberRepository heistMemberRepository;
    private HeistSkillRepository heistSkillRepository;
    private HeistRepository heistRepository;
    private MemberSkillRepository memberSkillRepository;
    private EligibleMembersConverter eligibleMembersConverter;



    public HeistMemberServiceImpl(HeistMemberRepository heistMemberRepository, HeistRepository heistRepository,
                                  HeistSkillRepository heistSkillRepository, MemberSkillRepository memberSkillRepository,
                                  EligibleMembersConverter eligibleMembersConverter) {

        this.heistMemberRepository = heistMemberRepository;
        this.heistRepository = heistRepository;
        this.heistSkillRepository = heistSkillRepository;
        this.memberSkillRepository = memberSkillRepository;
        this.eligibleMembersConverter = eligibleMembersConverter;

    }

    @Override
    public HeistMember saveHeistMember(HeistMember heistMember) {


        return heistMemberRepository.save(heistMember);

    }

    @Override
    public Optional<HeistMember> findHeistMemberById(String memberId) {

        return heistMemberRepository.findById(memberId);
    }

    @Override
    public Optional<HeistMember> findHeistMemberByStatusAndId(String email) {

        if (heistMemberRepository.findById(email).get().getStatus().equals(Status.AVAILABLE) || heistMemberRepository.findById(email).get().getStatus().equals(Status.EXPIRED)) {

            return heistMemberRepository.findById(email);

        }

        return null;
    }



    @Override
    public EligibleMembersDTO findEligibleHeistMember(String heistName) {

        Set<HeistMember> heistMembersSet = new HashSet<>();

        Optional<Heist> heistOptional = heistRepository.findById(heistName);

        Set<HeistSkill> heistSkillByHeist = heistSkillRepository.findHeistSkillByHeist(heistOptional.get());

        if (heistOptional.isPresent()) {

            Set<HeistMember> heistMembersByStatus = heistMemberRepository.findHeistMemberByStatusOrStatus(Status.AVAILABLE, Status.EXPIRED);


            for (HeistMember heistMember : heistMembersByStatus) {

                Set<MemberSkill> memberSkillByMember = memberSkillRepository.findMemberSkillsByMember(heistMember);

                for (HeistSkill heistSkill : heistSkillByHeist) {

                    for (MemberSkill memberSkill : memberSkillByMember) {

                        if (heistSkill.getSkill().equals(memberSkill.getSkill()) && heistSkill.getLevel().length() <= memberSkill.getLevel().length()) {

                            heistMembersSet.add(heistMember);

                            break;
                        }

                    }

                }
            }

        }
        return eligibleMembersConverter.toDto(heistMembersSet, heistSkillByHeist);
    }
}






