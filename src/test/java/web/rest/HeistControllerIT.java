package web.rest;

import agency.ProjectApplication;
import agency.controller.HeistController;
import agency.entity.Heist;
import agency.entity.HeistMember;
import agency.enumeration.Status;
import agency.repository.HeistRepository;
import agency.services.implementations.HeistStartManuallyImpl;
import agency.services.interfaces.HeistService;
import agency.services.interfaces.HeistSkillService;
import agency.services.interfaces.HeistStartManually;
import agency.services.interfaces.SkillService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static web.rest.TestUtil.createFormattingConversionService;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    private Heist heist;

    private Validator validator;
    private MockMvc heistControllerMvc;


    private static final String DEFAULT_NAME = "Fábrica Nacional de Moneda y Timbre";
    private static final LocalDateTime DEFAULT_END_DATE_TIME = LocalDateTime.of(2020, 9, 10, 18, 00, 00);
    private static final String DEFAULT_LOCATION = "Spain";
    private static final LocalDateTime DEFAULT_START_DATE_TIME = LocalDateTime.of(2020, 9, 05, 22, 00, 00);
    private static  final Status DEFAULT_STATUS = Status.PLANNING;



    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        final HeistController heistController = new HeistController(heistService, skillService, heistSkillService,heistStartManuallyImpl);
        this.heistControllerMvc = MockMvcBuilders.standaloneSetup(heistController)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter)
                .setValidator((org.springframework.validation.Validator) validator).build();

    }


    @Test
    @Transactional
    void checkImportOfHeistIT() throws Exception {

        int dbSizeBeforeCreate = heistRepository.findAll().size();

        heistControllerMvc.perform(MockMvcRequestBuilders.post("/heist")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heist)))
                .andExpect(status().isCreated());

       List<Heist> heistList = heistRepository.findAll();
       assertThat(heistList).hasSize(dbSizeBeforeCreate).hasSize(2);

    }

}
