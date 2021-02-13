package agency.controller;

import agency.dto.HeistDTO;
import agency.dto.HeistMemberDTO;
import agency.dto.HeistSkillDTO;
import agency.dto.MemberSkillDTO;
import agency.entity.*;
import agency.repository.HeistSkillRepository;
import agency.services.interfaces.HeistService;
import agency.services.interfaces.HeistSkillService;
import agency.services.interfaces.SkillService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

@Controller
public class HeistController {

    private HeistService heistService;
    private SkillService skillService;
    private HeistSkillService heistSkillService;

    public HeistController(HeistService heistService, SkillService skillService,  HeistSkillService heistSkillService) {
        this.heistService = heistService;
        this.skillService = skillService;
        this.heistSkillService = heistSkillService;
    }

    @PostMapping("/heist")
    public ResponseEntity<HeistMemberDTO> savePotentialHeistMember(@RequestBody HeistDTO heistDTO) throws URISyntaxException {

        Heist heist = new Heist();

        heist.setName(heistDTO.getName());
        heist.setLocation(heistDTO.getLocation());
        heist.setEndTime(heistDTO.getEndTime());
        heist.setStartTime(heistDTO.getStartTime());

        heistService.saveHeist(heist);


        for (HeistSkillDTO heistSkillDTO : heistDTO.getSkills()) {
            Skill skill = new Skill();
            skill.setName(heistSkillDTO.getName());
            skillService.saveSkill(skill);

            HeistSkill heistSkill = new HeistSkill();
            heistSkill.setHeist(heist);
            heistSkill.setLevel(heistSkillDTO.getLevel());
            heistSkill.setMember(heistSkillDTO.getMembers());
            heistSkill.setSkill(skill);

            heistSkillService.saveHeistSkill(heistSkill);

        }

        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setLocation(new URI("/heist" + URLEncoder.encode(heist.getName(), "UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
