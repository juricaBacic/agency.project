package web.rest;

import agency.ProjectApplication;
import agency.controller.HeistMemberController;
import agency.controller.RicherAPI;
import agency.dto.HeistMemberDTO;
import agency.entity.HeistMember;
import agency.enumeration.Sex;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import javax.xml.validation.Validator;
import java.net.URISyntaxException;
import java.util.Optional;

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

    private Validator validator;
    private MockMvc richerApiControllerMvc;

    private static final String HEIST_MEMBER_NAME = "Helsinki";
    private final static String HEIST_MEMBER_EMAIL ="helsinki@ag04.com";
    private final static String HEIST_NAME = "Fábrica Nacional de Moneda y Timbre";
    private static final Sex SEX = Sex.M;
    private static final String MAIN_SKILL_NAME = "combat";

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        final RicherAPI richerAPI = new RicherAPI(heistMemberService, heistMemberConverter, heistSkillConverter,heistService, heistConverter,
                 heistMemberSkillService,  memberSkillConverter, heistSkillService);
        this.richerApiControllerMvc = MockMvcBuilders.standaloneSetup(richerAPI)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter)
                .setValidator((org.springframework.validation.Validator) validator).build();
    }


    @Test
    @Transactional
    void getMemberByIdIT() throws Exception {

        richerApiControllerMvc.perform(MockMvcRequestBuilders.get("/member/{email}", HEIST_MEMBER_EMAIL)
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().is2xxSuccessful())
                .andExpect(status().isOk());

        HeistMember heistMemberById = heistMemberService.findHeistMemberById(HEIST_MEMBER_EMAIL).get();

        assertThat(heistMemberById.getName().equals(HEIST_MEMBER_NAME));
        assertThat(heistMemberById.getEmail().equals(HEIST_MEMBER_EMAIL));
        assertThat(heistMemberById.getSex().equals(SEX));
        assertThat(heistMemberById.getMainSkill().equals(MAIN_SKILL_NAME));

    }
}
