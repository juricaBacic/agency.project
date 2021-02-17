package web.rest;

import agency.ProjectApplication;
import agency.controller.HeistController;
import agency.dto.HeistDTO;
import agency.dto.HeistSkillDTO;
import agency.entity.Heist;
import agency.entity.HeistSkill;
import agency.enumeration.Status;
import agency.repository.HeistRepository;
import agency.repository.HeistSkillRepository;
import agency.services.implementations.HeistStartManuallyImpl;
import agency.services.interfaces.HeistService;
import agency.services.interfaces.HeistSkillService;
import agency.services.interfaces.SkillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import javax.xml.validation.Validator;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;


import static org.assertj.core.api.Assertions.assertThat;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {ProjectApplication.class, TestConfiguration.class})
public class HeistControllerIT {


    @Autowired
    private HeistService heistService;
    @Autowired
    private SkillService skillService;
    @Autowired
    private HeistStartManuallyImpl heistStartManuallyImpl;
    @Autowired
    private HeistSkillService heistSkillService;
    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
    @Autowired
    private HeistRepository heistRepository;
    @Autowired
    private HeistStartManuallyImpl heistStartManually;

    private Validator validator;
    private MockMvc heistControllerMvc;


    private static final String DEFAULT_NAME = "Fábrica Nacional de Moneda y Timbre";
    private static final LocalDateTime DEFAULT_END_DATE_TIME = LocalDateTime.of(2020, 9, 10, 18, 00, 00);
    private static final String DEFAULT_LOCATION = "Spain";
    private static final LocalDateTime DEFAULT_START_DATE_TIME = LocalDateTime.of(2020, 9, 05, 22, 00, 00);
    private static final Status DEFAULT_STATUS = Status.PLANNING;
    private static final String SKILL_NAME = "driving";
    private static final String SKILL_LEVEL_FOUR_STAR = "****";

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        final HeistController heistController = new HeistController(heistService, skillService, heistSkillService, heistStartManuallyImpl);
        this.heistControllerMvc = MockMvcBuilders.standaloneSetup(heistController)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter)
                .setValidator((org.springframework.validation.Validator) validator).build();

    }

    public HeistDTO addNewHeist() {

        HeistDTO heistDTO = new HeistDTO();

        heistDTO.setStatus(Status.PLANNING);
        heistDTO.setLocation(DEFAULT_LOCATION);
        heistDTO.setName(DEFAULT_NAME);
        heistDTO.setStartTime(DEFAULT_START_DATE_TIME);
        heistDTO.setEndTime(DEFAULT_END_DATE_TIME);
        HeistSkillDTO heistSkillDTO = new HeistSkillDTO();
        heistSkillDTO.setName(SKILL_NAME);
        heistSkillDTO.setMembers(1);
        heistSkillDTO.setLevel(SKILL_LEVEL_FOUR_STAR);
        heistDTO.setSkills(Collections.singleton(heistSkillDTO));

        return heistDTO ;

    }

    public HeistDTO checkHeistSkillUpdate() {

        HeistDTO heistDTO = new HeistDTO();
        HeistSkillDTO heistSkillDTO = new HeistSkillDTO();
        heistDTO.setSkills(Collections.singleton(heistSkillDTO));
        heistSkillDTO.setLevel(SKILL_LEVEL_FOUR_STAR);
        heistSkillDTO.setMembers(1);
        heistSkillDTO.setName(SKILL_NAME);

        return heistDTO;
    }

    @Test
    void checkImportOfHeistIT() throws Exception {

        heistControllerMvc.perform(MockMvcRequestBuilders.post("/heist")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(addNewHeist())))
                .andExpect(status().isCreated());

        Heist heist = heistRepository.findById(DEFAULT_NAME).get();
        assertThat(heist.getName().equals(DEFAULT_NAME));
        assertThat(heist.getStartTime().isBefore(DEFAULT_END_DATE_TIME));
        assertThat(heist.getEndTime().isAfter(DEFAULT_START_DATE_TIME));

    }

    @Test
    @Transactional
    void updateHeistSkillIT() throws Exception {

        heistSkillService.updateHeistSkill(checkHeistSkillUpdate(), DEFAULT_NAME);

        heistControllerMvc.perform(MockMvcRequestBuilders.put("/heist/{name}/skills", DEFAULT_NAME)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(addNewHeist())))
                .andExpect(status().is2xxSuccessful());

        Set<HeistSkill> heistSkills = heistSkillService.heistSkillByHeistId(DEFAULT_NAME);
        for (HeistSkill heistSkill1 : heistSkills) {
            assertThat(heistSkill1.getSkill().getName().equals(SKILL_NAME));
            assertThat(heistSkill1.getLevel().equals(SKILL_LEVEL_FOUR_STAR));
            assertThat(heistSkill1.getMember()).isGreaterThan(0);
        }

    }

    @Test
    @Transactional
    void startHeistManuallyIT() throws Exception{

        heistStartManually.startHeistManually(DEFAULT_NAME);

        heistControllerMvc.perform(MockMvcRequestBuilders.put("/heist/{name}/start", DEFAULT_NAME)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(addNewHeist())))
                .andExpect(status().is2xxSuccessful())
                .andExpect(status().isCreated());

        Heist heist = heistRepository.findById(DEFAULT_NAME).get();
        assertThat(heist.getStatus().equals(Status.IN_PROGRESS));

    }
}
