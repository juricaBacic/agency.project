package agency.controller;

import agency.dto.*;
import agency.entity.HeistMember;
import agency.services.interfaces.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import java.net.URLEncoder;
import java.util.Optional;

@RestController
public class HeistMemberController {

    private HeistMemberService heistMemberService;
    private HeistMemberSkillService heistMemberSkillService;
    private SkillService skillService;
    private CheckMembersForConfirmService checkMembersForConfirmService;
    private EmailService emailService;
    private HeistOutcomeService heistOutcomeService;

    public HeistMemberController(HeistMemberService heistMemberService, HeistMemberSkillService heistMemberSkillService, SkillService skillService,
                                 CheckMembersForConfirmService checkMembersForConfirmService, EmailService emailService, HeistOutcomeService heistOutcomeService) {
        this.heistMemberService = heistMemberService;
        this.heistMemberSkillService = heistMemberSkillService;
        this.skillService = skillService;
        this.checkMembersForConfirmService = checkMembersForConfirmService;
        this.emailService = emailService;
        this.heistOutcomeService = heistOutcomeService;
    }


    @PostMapping("/member")
    public ResponseEntity<HeistMember> savePotentialHeistMember(@RequestBody HeistMemberDTO heistMemberDTO) throws URISyntaxException {

        HeistMember heistMember = heistMemberService.saveHeistMember(heistMemberDTO);

        emailService.sendEmailToMember(heistMemberDTO.getEmail());

        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setLocation(new URI("/heistMember" + URLEncoder.encode(heistMember.getName(), "UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }


    @PutMapping("/member/{email}/skills")
    public ResponseEntity<HeistMemberSkillDTO> updateMemberSkills (@RequestBody HeistMemberDTO heistMemberDTO, @PathVariable String email) {

        heistMemberSkillService.updateMemberSkill(email,heistMemberDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @DeleteMapping("/member/{email}/skills/{skillName}")
    public ResponseEntity<HeistMemberSkillDTO> deleteMemberSkills (@PathVariable String email, @PathVariable String skillName ) throws URISyntaxException{

        heistMemberSkillService.deleteMemberSkillByMemberAndSkill(email, skillName);

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/heist/find/{email}/member")
    public Optional<HeistMember> findHeistMemberById(@PathVariable String  email){

        return  heistMemberService.findHeistMemberByStatusAndId(email);

    }
    @GetMapping("/heist/{name}/eligible_members")
    public EligibleMembersDTO findEligibleHeistMember(@PathVariable String  name){

        return  heistMemberService.findEligibleHeistMember(name);

    }

    @PutMapping("/heist/{name}/members")
    public ResponseEntity<String> addMembersForHeist (@RequestBody CheckAndConfirmForHeistDTO members, @PathVariable String name){


        HttpStatus status = checkMembersForConfirmService.checkAndAddHeistMembersForHeist(members.getMembers(), name);

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(headers, status);
    }
}
