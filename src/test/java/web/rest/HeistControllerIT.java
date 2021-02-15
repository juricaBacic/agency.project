package web.rest;

import agency.ProjectApplication;
import agency.controller.HeistController;
import agency.entity.Heist;
import agency.enumeration.Status;
import agency.repository.HeistRepository;
import agency.services.interfaces.HeistService;
import agency.services.interfaces.HeistSkillService;
import agency.services.interfaces.SkillService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import javax.xml.validation.Validator;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = {ProjectApplication.class, TestConfiguration.class})
public class HeistControllerIT {

    private HeistService heistService;
    private SkillService skillService;
    private HeistSkillService heistSkillService;
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
    private Validator validator;
    private MockMvc heistControllerMvc;
    private HeistRepository heistRepository;

    private LocalDateTime START_DATE_TIME = LocalDateTime.of(2020, 5, 15, 12, 10, 1);
    private LocalDateTime END_DATE_TIME = LocalDateTime.of(2020, 5, 16, 11, 15, 35);


    public HeistControllerIT(HeistService heistService, SkillService skillService, HeistSkillService heistSkillService, PageableHandlerMethodArgumentResolver pageableArgumentResolver,
                             MappingJackson2HttpMessageConverter jacksonMessageConverter, Validator validator, MockMvc heistControllerMvc, HeistRepository heistRepository) {

        this.heistService = heistService;
        this.skillService = skillService;
        this.heistSkillService = heistSkillService;
        this.pageableArgumentResolver = pageableArgumentResolver;
        this.jacksonMessageConverter = jacksonMessageConverter;
        this.validator = validator;
        this.heistControllerMvc = heistControllerMvc;
        this.heistRepository = heistRepository;

    }

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        final HeistController heistController = new HeistController(heistService, skillService, heistSkillService);
        this.heistControllerMvc = MockMvcBuilders.standaloneSetup(heistController)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter)
                .setValidator((org.springframework.validation.Validator) validator).build();

    }


    public Heist addNewHeist() {

        Heist heist = new Heist();

        heist.setStatus(Status.PLANNING);
        heist.setLocation("Croatia");
        heist.setName("Zagreb main bank");
        heist.setStartTime(START_DATE_TIME);
        heist.setEndTime(END_DATE_TIME);

        return heist;
    }


    @Test
    @Transactional
    void checkImportOfHeistIT() throws Exception {

        heistRepository.saveAndFlush(addNewHeist());

        ResultActions actions = heistControllerMvc.perform(MockMvcRequestBuilders.get("localhost:8080/heist"))
                .andDo(print());

        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].status").value(addNewHeist().getStatus()))
                .andExpect(jsonPath("$.[0].location").value(addNewHeist().getLocation()))
                .andExpect(jsonPath("$.[0].name").value(addNewHeist().getName()))
                .andExpect(jsonPath("$.[0].startTime").value(addNewHeist().getStartTime()))
                .andExpect(jsonPath("$.[0].endTime").value(addNewHeist().getEndTime()));

    }

    @Test
    void checkIsHeistSavedTest() {

        Optional<Heist> heistFind = heistRepository.findById("Fábrica Nacional de Moneda y Timbre");

        Heist heistCustom = new Heist();

        heistCustom.setName("Bank of Madrid");
        heistCustom.setStatus(Status.AVAILABLE);
        heistCustom.setLocation("Madrid");
        heistCustom.setStartTime(START_DATE_TIME);
        heistCustom.setEndTime(END_DATE_TIME);

        Heist hst = heistRepository.save(heistCustom);

        Assert.assertNotNull(heistFind);
        Assert.assertEquals(hst.getName(), heistCustom.getName());
        Assert.assertEquals(hst.getStartTime(), heistCustom.getStartTime());
        Assert.assertEquals(hst.getLocation(), heistCustom.getLocation());
        Assert.assertEquals(hst.getStatus(), heistCustom.getStatus());


    }
}
