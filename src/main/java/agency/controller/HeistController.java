package agency.controller;

import agency.dto.HeistDTO;
import agency.dto.HeistSkillDTO;
import agency.entity.*;
import agency.enumeration.OutcomeStatus;
import agency.services.implementations.HeistStartManuallyImpl;
import agency.services.interfaces.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.util.UriEncoder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

@RestController
public class HeistController {

    private HeistService heistService;
    private HeistSkillService heistSkillService;
    private HeistStartManuallyImpl heistStartManually;
    private AutomaticHeistStartService automaticHeistStartService;
    private HeistOutcomeService heistOutcomeService;

    public HeistController(HeistService heistService, HeistSkillService heistSkillService,
                           HeistStartManuallyImpl heistStartManually, AutomaticHeistStartService automaticHeistStartService, HeistOutcomeService heistOutcomeService) {
        this.heistService = heistService;
        this.heistSkillService = heistSkillService;
        this.heistStartManually = heistStartManually;
        this.automaticHeistStartService = automaticHeistStartService;
        this.heistOutcomeService = heistOutcomeService;
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

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PutMapping("/heist/{name}/start")
    public ResponseEntity<Heist> startHeistManually (@PathVariable String name) throws URISyntaxException{

        heistStartManually.startHeistManually(name);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PutMapping("/heist/{name}/outcome")
    public ResponseEntity<String> outcomeOfHeistStatus (@PathVariable String name) throws URISyntaxException{

        OutcomeStatus outcomeStatus = heistOutcomeService.outcomeOfTheHeist(name);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI("/heist/" + UriEncoder.encode(name) +  "/outcome"));

        if(outcomeStatus != null){
            return new ResponseEntity<>(outcomeStatus.toString(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

}
