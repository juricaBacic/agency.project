package agency.services.implementations;

import agency.entity.Heist;
import agency.entity.HeistMember;
import agency.entity.HeistSkill;
import agency.enumeration.OutcomeStatus;
import agency.enumeration.Status;
import agency.repository.HeistMemberRepository;
import agency.repository.HeistRepository;
import agency.repository.HeistSkillRepository;
import agency.services.interfaces.HeistOutcomeService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Service
public class HeistOutcomeServiceImpl implements HeistOutcomeService {

    HeistRepository heistRepository;
    HeistSkillRepository heistSkillRepository;
    HeistMemberRepository heistMemberRepository;


    public HeistOutcomeServiceImpl(HeistRepository heistRepository, HeistSkillRepository heistSkillRepository, HeistMemberRepository heistMemberRepository) {
        this.heistRepository = heistRepository;
        this.heistSkillRepository = heistSkillRepository;
        this.heistMemberRepository = heistMemberRepository;
    }


    @Override
    public OutcomeStatus outcomeOfTheHeist(String name) {

        Optional<Heist> heistById = heistRepository.findById(name);

        if (heistById.isPresent()) {
            Heist heist = heistById.get();
            Set<HeistSkill> findHeistSkill = heistSkillRepository.findHeistSkillByHeist(heist);

            int memberSum = 0;

            for (HeistSkill heistSkill : findHeistSkill) {

                memberSum += heistSkill.getMember();
            }
            Set<HeistMember> heistMembers = heist.getHeistMembers();
            float heistMemberPercentage = (float)heistMembers.size() / (float)memberSum;

            Random random = new Random();

            if (Float.compare(heistMemberPercentage, 0.5f) < 0) {

                for (HeistMember member : heistMembers) {
                    Status status = random.nextBoolean() ? Status.INCARCERATED : Status.EXPIRED;
                    member.setStatus(status);
                    heistMemberRepository.save(member);

                }
                return OutcomeStatus.FAILED;

            } else if (Float.compare(heistMemberPercentage, 0.5f) >= 0 && Float.compare(heistMemberPercentage, 0.75f) < 0) {

                int i = 0;
                int lastEleIndex;
                boolean hasFailed = random.nextBoolean();
                if(hasFailed){
                     lastEleIndex = 2 / 3 * heistMembers.size();
                }else {
                    lastEleIndex = 1 / 3 * heistMembers.size();
                }

                for (HeistMember member : heistMembers) {

                    if (i < lastEleIndex) {

                        break;
                    }
                    Status status = random.nextBoolean() ? Status.INCARCERATED : Status.EXPIRED;
                    member.setStatus(status);
                    i++;
                    heistMemberRepository.save(member);
                }
                if(hasFailed){
                    return OutcomeStatus.FAILED;
                } else {
                    return OutcomeStatus.SUCCEEDED;
                }

            } else if (Float.compare(heistMemberPercentage, 0.75f) >= 0 && Float.compare(heistMemberPercentage, 1f) < 0) {

                int lastEleIndex = 1 / 3 * heistMembers.size();
                int i = 0;

                for (HeistMember member : heistMembers) {

                    if (i < lastEleIndex) {

                        break;
                    }
                    Status status = Status.INCARCERATED;
                    member.setStatus(status);
                    i++;
                    heistMemberRepository.save(member);
                }
                return OutcomeStatus.SUCCEEDED;

            } else {

                return OutcomeStatus.SUCCEEDED;
            }
        }

        return null;
    }
}
