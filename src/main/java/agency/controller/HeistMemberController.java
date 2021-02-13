package agency.controller;

import agency.dto.HeistMemberDTO;
import agency.dto.MemberSkillDTO;
import agency.dto.SkillDTO;
import agency.entity.HeistMember;
import agency.entity.MemberSkill;
import agency.entity.Skill;
import agency.services.interfaces.HeistMemberService;
import agency.services.interfaces.MemberSkillService;
import agency.services.interfaces.SkillService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
public class HeistMemberController {


    private ModelMapper modelMapper;
    private HeistMemberService heistMemberService;
    private MemberSkillService memberSkillService;
    private SkillService skillService;


    public HeistMemberController(ModelMapper modelMapper, HeistMemberService heistMemberService, SkillService skillService, MemberSkillService memberSkillService) {
        this.modelMapper = modelMapper;
        this.heistMemberService = heistMemberService;
        this.skillService = skillService;
        this.memberSkillService = memberSkillService;

        Converter<String, Skill> skillConverter = mappingContext -> {
            Skill skill = new Skill();
            skill.setName(mappingContext.getSource());
            return skill;
        };
        modelMapper.createTypeMap(HeistMemberDTO.class, HeistMember.class).addMappings(mapper -> mapper.using(skillConverter).map(HeistMemberDTO::getMainSkill, HeistMember::setMainSkill));

    }


    @PostMapping("/member")
    public ResponseEntity<HeistMemberDTO> savePotentialHeistMember(@RequestBody HeistMemberDTO heistMemberDTO) throws URISyntaxException {

        for (MemberSkillDTO skillDTO : heistMemberDTO.getSkills()) {
            Skill skill = new Skill();
            skill.setName(skillDTO.getName());
            skillService.saveSkill(skill);
        }

        HeistMember member = heistMemberService.saveHeistMember(modelMapper.map(heistMemberDTO, HeistMember.class));

        heistMemberDTO.getSkills().forEach(memberSkillDTO -> {
            Skill skill = new Skill();
            skill.setName(memberSkillDTO.getName());

            MemberSkill memberSkill = new MemberSkill();
            memberSkill.setLevel(memberSkillDTO.getLevel());
            memberSkill.setSkill(skill);
            memberSkill.setMember(member);

            memberSkillService.saveMemberSkill(memberSkill);
        });

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI("/member" + heistMemberDTO.getEmail()));

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/member/{email}/skills")
    public ResponseEntity<MemberSkillDTO> updateMemberSkills (@RequestBody HeistMemberDTO heistMemberDTO, @PathVariable String email) throws URISyntaxException{

        memberSkillService.updateMemberSkill(email,heistMemberDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI("/member/" + heistMemberDTO.getEmail() +  "/skills"));

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    @DeleteMapping("/member/{email}/skills/{skillName}")
    public ResponseEntity<MemberSkillDTO> updateMemberSkills (@PathVariable String email,@PathVariable String skillName ) throws URISyntaxException{

        memberSkillService.deleteMemberSkillByMemberAndSkill(email, skillName);

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

}
