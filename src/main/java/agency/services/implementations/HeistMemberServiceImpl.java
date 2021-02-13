package agency.services.implementations;

import agency.entity.HeistMember;
import agency.repository.HeistMemberRepository;
import agency.services.interfaces.HeistMemberService;
import org.springframework.stereotype.Service;

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
}
