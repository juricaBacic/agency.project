package agency.controller;

import agency.dto.CheckAndConfirmForHeistDTO;
import agency.dto.EligibleMembersDTO;
import agency.dto.HeistMemberDTO;
import agency.dto.HeistMemberSkillDTO;
import agency.entity.HeistMember;
import agency.entity.HeistMemberSkill;
import agency.entity.Skill;
import agency.enumeration.OutcomeStatus;
import agency.services.interfaces.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.util.UriEncoder;
import java.net.URI;
import java.net.URISyntaxException;

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
    public ResponseEntity<HeistMemberDTO> savePotentialHeistMember(@RequestBody HeistMemberDTO heistMemberDTO) throws URISyntaxException {

        for (HeistMemberSkillDTO skillDTO : heistMemberDTO.getSkills()) {
            Skill skill = new Skill();
            skill.setName(skillDTO.getName());
            skillService.saveSkill(skill);
        }

        HeistMember heistMember = new HeistMember();
        heistMember.setEmail(heistMemberDTO.getEmail());
        heistMember.setName(heistMemberDTO.getName());
        heistMember.setSex(heistMemberDTO.getSex());
        heistMember.setStatus(heistMemberDTO.getStatus());
        heistMember.setMainSkill(new Skill(heistMemberDTO.getMainSkill()));

        HeistMember member = heistMemberService.saveHeistMember(heistMember);

        heistMemberDTO.getSkills().forEach(memberSkillDTO -> {
            Skill skill = new Skill();
            skill.setName(memberSkillDTO.getName());

            HeistMemberSkill heistMemberSkill = new HeistMemberSkill();
            heistMemberSkill.setLevel(memberSkillDTO.getLevel());
            heistMemberSkill.setSkill(skill);
            heistMemberSkill.setMember(member);

            heistMemberSkillService.saveMemberSkill(heistMemberSkill);


        });

        emailService.sendEmailToMember(heistMemberDTO.getEmail());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI("/member" + heistMemberDTO.getEmail()));

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/member/{email}/skills")
    public ResponseEntity<HeistMemberSkillDTO> updateMemberSkills (@RequestBody HeistMemberDTO heistMemberDTO, @PathVariable String email) throws URISyntaxException{

        heistMemberSkillService.updateMemberSkill(email,heistMemberDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI("/member/" + heistMemberDTO.getEmail() +  "/skills"));

        return new ResponseEntity<>(headers, HttpStatus.CREATED);

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
