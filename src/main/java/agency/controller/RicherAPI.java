package agency.controller;

import agency.dto.HeistDTO;
import agency.dto.HeistMemberDTO;
import agency.dto.HeistSkillDTO;
import agency.dto.HeistMemberSkillDTO;
import agency.entity.Heist;
import agency.entity.HeistMember;
import agency.entity.HeistMemberSkill;
import agency.entity.HeistSkill;
import agency.enumeration.Status;
import agency.services.converters.HeistConverter;
import agency.services.converters.HeistMemberConverter;
import agency.services.converters.HeistSkillConverter;
import agency.services.converters.MemberSkillConverter;
import agency.services.interfaces.HeistMemberService;
import agency.services.interfaces.HeistService;
import agency.services.interfaces.HeistSkillService;
import agency.services.interfaces.HeistMemberSkillService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
public class RicherAPI {

    HeistMemberService heistMemberService;
    HeistMemberConverter heistMemberConverter;
    HeistSkillConverter heistSkillConverter;
    HeistService heistService;
    HeistConverter heistConverter;
    HeistMemberSkillService heistMemberSkillService;
    MemberSkillConverter memberSkillConverter;
    HeistSkillService heistSkillService;

    public RicherAPI(HeistMemberService heistMemberService, HeistMemberConverter heistMemberConverter, HeistSkillConverter heistSkillConverter, HeistService heistService, HeistConverter heistConverter,
                     HeistMemberSkillService heistMemberSkillService, MemberSkillConverter memberSkillConverter, HeistSkillService heistSkillService) {
        this.heistMemberService = heistMemberService;
        this.heistMemberConverter = heistMemberConverter;
        this.heistSkillConverter = heistSkillConverter;
        this.heistService = heistService;
        this.heistConverter = heistConverter;
        this.heistMemberSkillService = heistMemberSkillService;
        this.memberSkillConverter = memberSkillConverter;
        this.heistSkillService = heistSkillService;
    }

    @GetMapping("/member/{email}")
    public HeistMemberDTO getMemberById(@PathVariable String email, HttpServletResponse response) throws URISyntaxException {

        Optional<HeistMember> heistMemberById = heistMemberService.findHeistMemberById(email);

        if (heistMemberById.isPresent()) {
            response.setStatus(200);
            return heistMemberConverter.toDto(heistMemberById.get());
        }
        response.setStatus(404);
        return null;
    }

    @GetMapping("/heist/api/{name}")
    public HeistDTO getHeistByName(@PathVariable String name, HttpServletResponse response) throws URISyntaxException {

        Optional<Heist> heistOptional = heistService.getHeistById(name);

        if (heistOptional.isPresent()) {
            response.setStatus(200);

            return heistConverter.toDto(heistOptional.get());
        }
        response.setStatus(404);
        return null;
    }

    @GetMapping("/member/api/{email}/skills")
    public Set<HeistMemberSkillDTO> getMemberSkillsById(@PathVariable String email, HttpServletResponse response) throws URISyntaxException {

        Set<HeistMemberSkill> memberSkillsByHeistMemberId = heistMemberSkillService.getMemberSkillByMemberId(email);
        Set<HeistMemberSkillDTO> heistMemberSkillDTOS = new HashSet<>();

        if (!CollectionUtils.isEmpty(memberSkillsByHeistMemberId)) {
            response.setStatus(200);
            for (HeistMemberSkill heistMemberSkill : memberSkillsByHeistMemberId)
            {
                heistMemberSkillDTOS.add(memberSkillConverter.toDto(heistMemberSkill));
            }

            return heistMemberSkillDTOS;
        }
        response.setStatus(404);
        return null;
    }
    @GetMapping("/heist/api/{name}/members")
    public HeistDTO getHeistWithNameStatusANdSkillsByName(@PathVariable String name, HttpServletResponse response) throws URISyntaxException {

        Optional<Heist> heistOptional = heistService.getHeistWithNameStatusAndSkillsByName(name);

        if (heistOptional.isPresent()) {
            response.setStatus(200);

            return heistConverter.toDtoForNameSkillAndStatus(heistOptional.get());
        }
        response.setStatus(404);
        return null;
    }
    @GetMapping("/heist/api/{name}/skills")
    public Set<HeistSkillDTO> getHeistSkillByHeistId(@PathVariable String name, HttpServletResponse response) throws URISyntaxException {

        Set<HeistSkill> heistOptional = heistSkillService.heistSkillByHeistId(name);

        Set<HeistSkillDTO> heistSkillDTOS = new HashSet<>();

        if (!CollectionUtils.isEmpty(heistOptional)) {
            response.setStatus(200);
            for (HeistSkill heistSkill : heistOptional)
            {
                heistSkillDTOS.add(heistSkillConverter.toDto(heistSkill));
            }

            return heistSkillDTOS;
        }
        response.setStatus(404);

        return null;
    }


    @GetMapping("/heist/api/{name}/status")
    public Status getHeistStatusByHeistId(@PathVariable String name, HttpServletResponse response) throws URISyntaxException {

        Optional<Status> heistOptional = heistService.getHeistStatusByHeistId(name);

        if (heistOptional.isPresent()) {
            response.setStatus(200);
            return heistOptional.get();
        }
        response.setStatus(404);
        return null;
    }
}
