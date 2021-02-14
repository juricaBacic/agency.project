package agency.services.implementations;

import agency.dto.HeistMemberDTO;
import agency.entity.HeistMember;
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
            System.out.println("Ispunio je uvijet i usao u loop");
         return heistMemberRepository.findById(email);

        }

        return null;
    }

}
