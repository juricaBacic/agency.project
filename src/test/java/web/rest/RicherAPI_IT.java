package web.rest;

import agency.ProjectApplication;
import agency.controller.RicherAPI;
import agency.entity.Heist;
import agency.entity.HeistMember;
import agency.entity.HeistMemberSkill;
import agency.entity.HeistSkill;
import agency.enumeration.Sex;
import agency.enumeration.Status;
import agency.repository.HeistMemberRepository;
import agency.repository.HeistMemberSkillRepository;
import agency.repository.HeistRepository;
import agency.services.converters.HeistConverter;
import agency.services.converters.HeistMemberConverter;
import agency.services.converters.HeistSkillConverter;
import agency.services.converters.MemberSkillConverter;
import agency.services.interfaces.HeistMemberService;
import agency.services.interfaces.HeistMemberSkillService;
import agency.services.interfaces.HeistService;
import agency.services.interfaces.HeistSkillService;
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

import javax.xml.validation.Validator;
import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {ProjectApplication.class, TestConfiguration.class})
public class RicherAPI_IT {

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
    @Autowired
    HeistMemberService heistMemberService;
    @Autowired
    HeistMemberConverter heistMemberConverter;
    @Autowired
    HeistSkillConverter heistSkillConverter;
    @Autowired
    HeistService heistService;
    @Autowired
    HeistConverter heistConverter;
    @Autowired
    HeistMemberSkillService heistMemberSkillService;
    @Autowired
    MemberSkillConverter memberSkillConverter;
    @Autowired
    HeistSkillService heistSkillService;
    @Autowired
    HeistMemberRepository heistMemberRepository;
    @Autowired
    HeistRepository heistRepository;
    @Autowired
    HeistMemberSkillRepository heistMemberSkillRepository;

    private Validator validator;
    private MockMvc richerApiControllerMvc;

    private static final String HEIST_MEMBER_NAME = "Helsinki";
    private final static String HEIST_MEMBER_EMAIL = "helsinki@ag04.com";
    private final static String HEIST_NAME = "Fábrica Nacional de Moneda y Timbre";
    private static final Sex SEX = Sex.M;
    private static final String MAIN_SKILL_NAME = "combat";
    private static final String TWO_STAR_LEVEL = "**";
    private static final String FOUR_STAR_LEVEL = "****";
    private static final String SKILL_DRIVING = "driving";
    private static final String SKILL_COMBAT = "combat";


    private static final String DEFAULT_NAME = "Fábrica Nacional de Moneda y Timbre";
    private static final LocalDateTime DEFAULT_END_DATE_TIME = LocalDateTime.of(2020, 9, 10, 18, 00, 00);
    private static final String DEFAULT_LOCATION = "Spain";
    private static final LocalDateTime DEFAULT_START_DATE_TIME = LocalDateTime.of(2020, 9, 05, 22, 00, 00);
    private static final Status PLANNING_STATUS = Status.PLANNING;
    private static final String SKILL_NAME = "driving";
    private static final String SKILL_LEVEL_FOUR_STAR = "****";
    private static final String FAILED = "FAILED";

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        final RicherAPI richerAPI = new RicherAPI(heistMemberService, heistMemberConverter, heistSkillConverter, heistService, heistConverter,
                heistMemberSkillService, memberSkillConverter, heistSkillService);
        this.richerApiControllerMvc = MockMvcBuilders.standaloneSetup(richerAPI)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter)
                .setValidator((org.springframework.validation.Validator) validator).build();
    }


    @Test
    void saveHeistIT() throws Exception {

        richerApiControllerMvc.perform(MockMvcRequestBuilders.get("/member/{email}/", HEIST_MEMBER_EMAIL)
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        HeistMember heistMember = heistMemberRepository.findById(HEIST_MEMBER_EMAIL).get();

        assertThat(heistMember.getEmail().equals(HEIST_MEMBER_EMAIL));
    }

    @Test
    void getHeistByNameIT() throws Exception {

        richerApiControllerMvc.perform(MockMvcRequestBuilders.get("/heist/api/{name}", HEIST_NAME)
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        Heist heist = heistRepository.findById(HEIST_NAME).get();

        assertThat(heist.getName().equals(HEIST_NAME));
    }


    @Test
    void getMemberSkillsByIdIT() throws Exception {

        richerApiControllerMvc.perform(MockMvcRequestBuilders.get("/member/api/{email:.+}/skills", HEIST_MEMBER_EMAIL)
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        Set<HeistMemberSkill> memberSkillsByMemberEmail = heistMemberSkillRepository.findMemberSkillsByMemberEmail(HEIST_MEMBER_EMAIL);

        for (HeistMemberSkill memberSkill : memberSkillsByMemberEmail) {

            assertThat(memberSkill.getLevel().equals(TWO_STAR_LEVEL));
            assertThat(memberSkill.getMember().getName().equals(HEIST_MEMBER_NAME));

        }
    }

    @Test
    void getHeistWithNameStatusAndSkillsByNameIT() throws Exception {

        richerApiControllerMvc.perform(MockMvcRequestBuilders.get("/heist/api/{name}/members", HEIST_NAME)
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());


        Heist heistWithNameStatusAndSkillsByName = heistService.getHeistWithNameStatusAndSkillsByName(HEIST_NAME).get();

        assertThat(heistWithNameStatusAndSkillsByName.getName().equals(HEIST_NAME));
        assertThat(heistWithNameStatusAndSkillsByName.getStartTime().equals(DEFAULT_START_DATE_TIME));
        assertThat(heistWithNameStatusAndSkillsByName.getEndTime().equals(DEFAULT_END_DATE_TIME));
        assertThat(heistWithNameStatusAndSkillsByName.getLocation().equals(DEFAULT_LOCATION));


    }

    @Test
    void getHeistSkillByHeistIdIT() throws Exception {

        richerApiControllerMvc.perform(MockMvcRequestBuilders.get("/heist/api/{name}/skills", HEIST_NAME)
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());


        Set<HeistSkill> heistSkillSet = heistSkillService.heistSkillByHeistId(HEIST_NAME);

        for (HeistSkill heistSkill : heistSkillSet) {

            assertThat(heistSkill.getLevel().equals(FOUR_STAR_LEVEL));
            assertThat(heistSkill.getHeist().getStartTime().equals(DEFAULT_START_DATE_TIME));
        }


    }

    @Test
    void getHeistStatusByHeistIdIT() throws Exception {

        richerApiControllerMvc.perform(MockMvcRequestBuilders.get("/heist/api/{name}/status", HEIST_NAME)
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        String heistStatusByHeistId = heistService.getHeistStatusByHeistId(HEIST_NAME).get().toString();

        assertThat(heistStatusByHeistId.equals(PLANNING_STATUS));

    }
}
