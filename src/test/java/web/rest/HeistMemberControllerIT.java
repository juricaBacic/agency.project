package web.rest;

import agency.ProjectApplication;
import agency.controller.HeistMemberController;
import agency.dto.*;
import agency.entity.*;
import agency.enumeration.Sex;
import agency.repository.HeistMemberRepository;
import agency.repository.HeistMemberSkillRepository;
import agency.services.converters.HeistMemberConverter;
import agency.services.interfaces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.validation.Validator;
import java.util.Collections;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = {ProjectApplication.class, TestConfiguration.class})
public class HeistMemberControllerIT {


    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
    @Autowired
    private HeistMemberService heistMemberService;
    @Autowired
    private HeistMemberSkillService heistMemberSkillService;
    @Autowired
    private SkillService skillService;
    @Autowired
    private CheckMembersForConfirmService checkMembersForConfirmService;
    @Autowired
    EmailService emailService;
    @Autowired
    HeistOutcomeService heistOutcomeService;
    @Autowired
    HeistMemberRepository heistMemberRepository;
    @Autowired
    HeistMemberSkillRepository heistMemberSkillRepository;
    @Autowired
    HeistMemberConverter heistMemberConverter;


    private Validator validator;
    private MockMvc heistMemberControllerMvc;


    //HeistMember
    private static final String EMAIL = "helsinki@ag04.com";
    private static final String NAME = "Helsinki";
    private static final Sex SEX = Sex.M;
    private static final String MAIN_SKILL_NAME = "combat";

    private static final String DEFAULT_NAME = "Fábrica Nacional de Moneda y Timbre";

    //HeistMemberSkill\
    private static final String MEMBER_EMAIL = "Helsinki@ag04.com";
    private static final String TWO_STAR_LEVEL = "**";
    private static final String FOUR_STAR_LEVEL = "****";
    private static final String SKILL_DRIVING = "driving";
    private static final String SKILL_COMBAT = "combat";


    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        final HeistMemberController heistMemberController = new HeistMemberController(heistMemberService, heistMemberSkillService, skillService,
                checkMembersForConfirmService, emailService, heistOutcomeService);
        this.heistMemberControllerMvc = MockMvcBuilders.standaloneSetup(heistMemberController)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter)
                .setValidator((org.springframework.validation.Validator) validator).build();
    }

    public HeistMemberDTO addNewHeistMember() {

        HeistMemberDTO heistMemberDTO = new HeistMemberDTO();

        heistMemberDTO.setName(NAME);
        heistMemberDTO.setEmail(EMAIL);
        heistMemberDTO.setSex(SEX);
        heistMemberDTO.setMainSkill(MAIN_SKILL_NAME);
        HeistMemberSkillDTO heistMemberSkillDTO = new HeistMemberSkillDTO();
        heistMemberSkillDTO.setLevel(TWO_STAR_LEVEL);
        heistMemberSkillDTO.setName(SKILL_DRIVING);
        heistMemberDTO.setSkills(Collections.singleton(heistMemberSkillDTO));

        return heistMemberDTO;

    }


    public HeistMemberDTO updatedHeistMemberSkills() {

        HeistMemberDTO heistMemberDTO = new HeistMemberDTO();

        HeistMemberSkillDTO heistMemberSkillDTO = new HeistMemberSkillDTO();
        heistMemberSkillDTO.setLevel(FOUR_STAR_LEVEL);
        heistMemberSkillDTO.setName(SKILL_COMBAT);
        heistMemberDTO.setSkills(Collections.singleton(heistMemberSkillDTO));

        return heistMemberDTO;

    }


    @Test
    void saveMember() throws Exception {

        heistMemberControllerMvc.perform(MockMvcRequestBuilders.post("/member")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(addNewHeistMember())))
                .andExpect(status().isCreated());

        HeistMember heistMember = heistMemberRepository.findById(MEMBER_EMAIL).get();
        assertThat(heistMember.getName().equals(NAME));
        assertThat(heistMember.getEmail().equals(MEMBER_EMAIL));
        assertThat(heistMember.getSex().equals(SEX));
        assertThat(heistMember.getMainSkill().equals(MAIN_SKILL_NAME));

    }


    @Test
    @Transactional
    void updateHeistSkillIT() throws Exception {

        heistMemberSkillService.updateMemberSkill(EMAIL, updatedHeistMemberSkills());

        heistMemberControllerMvc.perform(MockMvcRequestBuilders.put("/member/{email}/skills", MEMBER_EMAIL)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(addNewHeistMember())))
                .andExpect(status().is2xxSuccessful());

        Set<HeistMemberSkill> memberSkillsByMemberEmail = heistMemberSkillRepository.findMemberSkillsByMemberEmail(MEMBER_EMAIL);

        for (HeistMemberSkill heistMemberSkill : memberSkillsByMemberEmail) {

            assertThat(heistMemberSkill.getSkill().getName().equals(SKILL_COMBAT));
            assertThat(heistMemberSkill.getLevel().equals(FOUR_STAR_LEVEL));
        }
    }


    @Test
    @Transactional
    void deleteHeistSkillIT() throws Exception {

        heistMemberControllerMvc.perform(MockMvcRequestBuilders.delete("/member/{email}/skills/{skillName}", MEMBER_EMAIL, SKILL_COMBAT)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(addNewHeistMember())))
                .andExpect(status().is2xxSuccessful());

        Set<HeistMemberSkill> memberSkillsByMemberEmail = heistMemberSkillRepository.findMemberSkillsByMemberEmail(MEMBER_EMAIL);

        for (HeistMemberSkill hms : memberSkillsByMemberEmail) {

            assertThat(!hms.getSkill().equals(SKILL_COMBAT));

        }
    }


    @Test
    @Transactional
    void findHeistMemberByIdIT() throws Exception {


        heistMemberControllerMvc.perform(MockMvcRequestBuilders.get("/heist/find/{email}/member/", EMAIL)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(addNewHeistMember())))
                .andExpect(status().is2xxSuccessful());

        HeistMember byId = heistMemberRepository.findById(EMAIL).get();

        assertThat(byId.getEmail().equals(EMAIL));
        assertThat(byId.getSex().equals(SEX));
        assertThat(byId.getName().equals(NAME));

    }


    @Test
    @Transactional
    void findEligibleHeistMemberIT() throws Exception {


        heistMemberControllerMvc.perform(MockMvcRequestBuilders.get("/heist/{name}/eligible_members", DEFAULT_NAME)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(addNewHeistMember())))
                .andExpect(status().is2xxSuccessful());

        EligibleMembersDTO eligibleHeistMember = heistMemberService.findEligibleHeistMember(DEFAULT_NAME);

        assertThat(eligibleHeistMember.getMembers().equals(EMAIL));


    }


    @Test
    @Transactional
    void addMembersForHeistIT() throws Exception {


        heistMemberControllerMvc.perform(MockMvcRequestBuilders.put("/heist/{name}/members", DEFAULT_NAME)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(addNewHeistMember().getEmail())))
                .andExpect(status().is4xxClientError());


    }


}

