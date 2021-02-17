package agency.controller;

import agency.dto.HeistDTO;
import agency.dto.HeistMemberDTO;
import agency.dto.HeistSkillDTO;
import agency.dto.MemberSkillDTO;
import agency.entity.Heist;
import agency.entity.HeistMember;
import agency.entity.MemberSkill;
import agency.services.converters.HeistConverter;
import agency.services.converters.HeistMemberConverter;
import agency.services.converters.HeistSkillConverter;
import agency.services.converters.MemberSkillConverter;
import agency.services.interfaces.HeistMemberService;
import agency.services.interfaces.HeistService;
import agency.services.interfaces.MemberSkillService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.Set;

@RestController
public class RicherAPI {

    HeistMemberService heistMemberService;
    HeistMemberConverter heistMemberConverter;
    HeistSkillConverter heistSkillConverter;
    HeistService heistService;
    HeistConverter heistConverter;
    MemberSkillService memberSkillService;
    MemberSkillConverter memberSkillConverter;

    public RicherAPI(HeistMemberService heistMemberService, HeistMemberConverter heistMemberConverter, HeistSkillConverter heistSkillConverter, HeistService heistService,
                     HeistConverter heistConverter, MemberSkillService memberSkillService, MemberSkillConverter memberSkillConverter) {
        this.heistMemberService = heistMemberService;
        this.heistMemberConverter = heistMemberConverter;
        this.heistSkillConverter = heistSkillConverter;
        this.heistService = heistService;
        this.heistConverter = heistConverter;
        this.memberSkillService = memberSkillService;
        this.memberSkillConverter = memberSkillConverter;
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
    public MemberSkillDTO getMemberSkillsById(@PathVariable String email, HttpServletResponse response) throws URISyntaxException {

        Optional<MemberSkill> memberSkillsByMemberId = memberSkillService.getMemberSkillByMemberId(email);

        if (memberSkillsByMemberId.isPresent()) {
            response.setStatus(200);
            return memberSkillConverter.toDto(memberSkillsByMemberId.get());
        }
        response.setStatus(404);
        return null;
    }
}
