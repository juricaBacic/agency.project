package agency.controller;

import agency.dto.HeistDTO;
import agency.dto.HeistSkillDTO;
import agency.entity.*;
import agency.services.implementations.HeistStartManuallyImpl;
import agency.services.interfaces.AutomaticHeistStartService;
import agency.services.interfaces.HeistService;
import agency.services.interfaces.HeistSkillService;
import agency.services.interfaces.SkillService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

@RestController
public class HeistController {

    private HeistService heistService;
    private SkillService skillService;
    private HeistSkillService heistSkillService;
    private HeistStartManuallyImpl heistStartManually;
    private AutomaticHeistStartService automaticHeistStartService;

    public HeistController(HeistService heistService, SkillService skillService,
                           HeistSkillService heistSkillService, HeistStartManuallyImpl heistStartManually, AutomaticHeistStartService automaticHeistStartService) {
        this.heistService = heistService;
        this.skillService = skillService;
        this.heistSkillService = heistSkillService;
        this.heistStartManually = heistStartManually;
        this.automaticHeistStartService = automaticHeistStartService;
    }

    @PostMapping("/heist")
    public ResponseEntity<Heist> saveHeist(@RequestBody HeistDTO heistDTO) throws URISyntaxException {

        Heist heist = heistService.saveHeist(heistDTO);

        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setLocation(new URI("/heist" + URLEncoder.encode(heist.getName(), "UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/heist/{name}/skills")
    public ResponseEntity<HeistSkillDTO> updateHeistSkills (@RequestBody HeistDTO heistDTO, @PathVariable String name) throws URISyntaxException, UnsupportedEncodingException {

        heistSkillService.updateHeistSkill(heistDTO,name);

    //    HttpHeaders headers = new HttpHeaders();
    //    headers.setLocation(new URI("/heist/" + URLEncoder.encode(heistDTO.getName(), "UTF-8") + "/skills"));

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PutMapping("/heist/{name}/start")
    public ResponseEntity<Heist> startHeistManually (@PathVariable String name) throws URISyntaxException{

        heistStartManually.startHeistManually(name);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
