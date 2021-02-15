package agency.services.implementations;

import agency.dto.HeistDTO;
import agency.dto.HeistMemberDTO;
import agency.dto.HeistSkillDTO;
import agency.entity.Heist;
import agency.entity.HeistMember;
import agency.entity.HeistSkill;
import agency.entity.Skill;
import agency.enumeration.Status;
import agency.repository.HeistMemberRepository;
import agency.services.interfaces.HeistMemberService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HeistMemberServiceImpl implements HeistMemberService {

    private HeistMemberRepository heistMemberRepository;


    public HeistMemberServiceImpl(HeistMemberRepository heistMemberRepository) {
        this.heistMemberRepository = heistMemberRepository;
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
    public Optional<HeistMember>findHeistMemberByStatusAndId(String email) {

        if (heistMemberRepository.findById(email).get().getStatus().equals(Status.AVAILABLE) || heistMemberRepository.findById(email).get().getStatus().equals(Status.EXPIRED)) {

         return heistMemberRepository.findById(email);

        }

        return null;
    }

}
