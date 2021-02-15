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
import org.junit.Before;
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
    private HeistSkillService heistSkillService;
    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private Validator validator;
    private MockMvc heistControllerMvc;
    @Autowired
    private HeistRepository heistRepository;

    private LocalDateTime START_DATE_TIME = LocalDateTime.of(2020, 5, 15, 12, 10, 1);
    private LocalDateTime END_DATE_TIME = LocalDateTime.of(2020, 5, 16, 11, 15, 35);



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

        ResultActions actions = heistControllerMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/heist"))
                .andDo(print());

        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].status").value(addNewHeist().getStatus()))
                .andExpect(jsonPath("$.[0].location").value(addNewHeist().getLocation()))
                .andExpect(jsonPath("$.[0].name").value(addNewHeist().getName()))
                .andExpect(jsonPath("$.[0].startTime").value(addNewHeist().getStartTime()))
                .andExpect(jsonPath("$.[0].endTime").value(addNewHeist().getEndTime()));

    }

}
