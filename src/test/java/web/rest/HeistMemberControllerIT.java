package web.rest;


import agency.ProjectApplication;
import agency.controller.HeistController;
import agency.controller.HeistMemberController;
import agency.enumeration.Status;
import agency.repository.HeistRepository;
import agency.services.implementations.HeistStartManuallyImpl;
import agency.services.interfaces.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
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

@SpringBootTest(classes = {ProjectApplication.class, TestConfiguration.class})
public class HeistMemberControllerIT {


    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
    @Autowired
    private HeistRepository heistRepository;
    @Autowired
    private HeistStartManuallyImpl heistStartManually;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private HeistMemberService heistMemberService;
    @Autowired
    private MemberSkillService memberSkillService;
    @Autowired
    private SkillService skillService;
    @Autowired
    private CheckMembersForConfirmService checkMembersForConfirmService;


    private Validator validator;
    private MockMvc heistMemberControllerMvc;


    //HeistMember
    private static final String EMAIL = "Helsinki@ag04.com";
    private static final String NAME = "Helsinki";
    private static final String SEX = "M";
    private static final Status STATUS = Status.AVAILABLE;
    private static final String MAIN_SKILL_NAME = "combat";

    //MemberSkill\
    private static final String TWO_STAR_LEVEL = "**";
    private static final String MEMBER_EMAIL = "Helsinki@ag04.com";
    private static final String SKILL_NAME = "driving";


    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        final HeistMemberController heistMemberController = new HeistMemberController(modelMapper,heistMemberService,skillService,memberSkillService,checkMembersForConfirmService);
        this.heistMemberControllerMvc = MockMvcBuilders.standaloneSetup(heistMemberController)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter)
                .setValidator((org.springframework.validation.Validator) validator).build();
    }


}
