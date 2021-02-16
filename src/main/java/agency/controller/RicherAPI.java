package agency.controller;

import agency.dto.HeistMemberDTO;
import agency.entity.HeistMember;
import agency.services.implementations.HeistMemberConverter;
import agency.services.interfaces.HeistMemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.util.UriEncoder;

import javax.servlet.http.HttpServletResponse;

import java.net.URISyntaxException;
import java.util.Optional;

@RestController
public class RicherAPI {

    HeistMemberService heistMemberService;
    HeistMemberConverter heistMemberConverter;

    public RicherAPI(HeistMemberService heistMemberService, HeistMemberConverter heistMemberConverter) {
        this.heistMemberService = heistMemberService;
        this.heistMemberConverter = heistMemberConverter;
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

}
