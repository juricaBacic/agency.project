package agency.services.implementations;

import agency.entity.Heist;
import agency.entity.HeistMember;
import agency.enumeration.Status;
import agency.repository.HeistMemberRepository;
import agency.repository.HeistRepository;
import agency.services.interfaces.CheckMembersForConfirmService;
import agency.services.interfaces.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CheckMembersForConfirmServiceImpl implements CheckMembersForConfirmService {


    private HeistRepository heistRepository;
    private HeistMemberRepository heistMemberRepository;
    private EmailService emailService;

    public CheckMembersForConfirmServiceImpl(HeistRepository heistRepository, HeistMemberRepository heistMemberRepository, EmailService emailService) {
        this.heistRepository = heistRepository;
        this.heistMemberRepository = heistMemberRepository;
        this.emailService = emailService;
    }

    @Override
    public HttpStatus checkAndAddHeistMembersForHeist(List<String> membersList, String name) {

        Optional<Heist> checkHeist = heistRepository.findById(name);
        Set<HeistMember> heistMembers = new HashSet<>();

        if (checkHeist.isPresent()) {

            Heist heist = checkHeist.get();
            if (!heist.getStatus().equals(Status.PLANNING)) {

                return HttpStatus.METHOD_NOT_ALLOWED;
            }


            for (String memberName : membersList) {

                HeistMember member = heistMemberRepository.findHeistMemberWhoWillParticipateInHeist(memberName);


                if (member != null) {

                    if (CollectionUtils.isEmpty(member.getHeists())) {

                        heistMembers.add(member);
                    } else {

                        for (Heist assignedHeist : member.getHeists()) {

                            if (heist.getStartTime().isBefore(assignedHeist.getEndTime())
                                    && heist.getEndTime().isAfter(assignedHeist.getStartTime())) {

                                return HttpStatus.BAD_REQUEST;

                            } else {

                                heistMembers.add(member);

                            }
                        }
                    }


                } else {
                    return HttpStatus.BAD_REQUEST;
                }
            }

            Set<HeistMember> heistMembers1 = heist.getHeistMembers();
            heistMembers1.addAll(heistMembers);
            heist.setStatus(Status.READY);
            heistRepository.save(heist);
            for (HeistMember heistMemberOne:heistMembers1) {

                emailService.sendEmailToMember(heistMemberOne.getEmail());

            }
            return HttpStatus.NO_CONTENT;

        } else {
            return HttpStatus.NOT_FOUND;
        }
    }
}
