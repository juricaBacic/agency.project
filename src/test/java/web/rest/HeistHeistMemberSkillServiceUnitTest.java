package web.rest;

import agency.ProjectApplication;
import agency.dto.HeistMemberDTO;
import agency.entity.HeistMember;
import agency.entity.HeistMemberSkill;
import agency.repository.HeistMemberRepository;
import agency.repository.HeistMemberSkillRepository;
import agency.services.converters.HeistMemberConverter;
import agency.services.interfaces.HeistMemberSkillService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

import java.util.Optional;
import java.util.Set;

@SpringBootTest(classes = {ProjectApplication.class, TestConfiguration.class})
public class HeistHeistMemberSkillServiceUnitTest {

    private static final String HEIST_MEMBER_EMAIL = "helsinki@ag04.com";
    private static final String HEIST_MEMBER_SKILL ="driving";


    /*
     Set<HeistMemberSkill> getMemberSkillByMemberId(String email);
     */

    @Autowired
    HeistMemberSkillService heistMemberSkillService;
    @Autowired
    HeistMemberSkillRepository heistMemberSkillRepository;
    @Autowired
    HeistMemberConverter heistMemberConverter;
    @Autowired
    HeistMemberRepository heistMemberRepository;

    @Test
    void saveMemberSkillTest(){

        Set<HeistMemberSkill> memberSkillsByHeistMemberEmail = heistMemberSkillRepository.findMemberSkillsByMemberEmail(HEIST_MEMBER_EMAIL);

        for (HeistMemberSkill heistMemberSkill : memberSkillsByHeistMemberEmail) {

            heistMemberSkillService.saveMemberSkill(heistMemberSkill);

            Assert.assertNotNull(memberSkillsByHeistMemberEmail);
            Assert.assertNotNull(heistMemberSkill);
            Assert.assertTrue(!heistMemberSkill.getLevel().isEmpty());
            Assert.assertTrue(!heistMemberSkill.getSkill().getName().isEmpty());
        }
    }

    @Test
    void updateMemberSkillTest(){

        HeistMember heistMemberById = heistMemberRepository.findById(HEIST_MEMBER_EMAIL).get();

        Assert.assertTrue(!heistMemberById.getMainSkill().getName().isEmpty());

        HeistMemberDTO heistMemberToDTO = heistMemberConverter.toDto(heistMemberById);

        Assert.assertNotNull(heistMemberToDTO);

        heistMemberSkillService.updateMemberSkill(HEIST_MEMBER_EMAIL,heistMemberToDTO);

        Assert.assertNotEquals(heistMemberToDTO.getMainSkill(), heistMemberById.getMainSkill());


    }


    @Test
    void deleteMemberSkillTest(){

        Set<HeistMemberSkill> memberSkillByMemberId = heistMemberSkillService.getMemberSkillByMemberId(HEIST_MEMBER_EMAIL);

        for (HeistMemberSkill heistMemberSkill: memberSkillByMemberId) {

            heistMemberSkillService.deleteMemberSkillByMemberAndSkill(HEIST_MEMBER_EMAIL,HEIST_MEMBER_SKILL);

            Assert.assertTrue(!heistMemberSkill.getSkill().getName().contains(HEIST_MEMBER_SKILL));

        }

    }

    @Test
    void getsHeistMemberSkillBYId(){

        Set<HeistMemberSkill> memberSkillByMemberId = heistMemberSkillService.getMemberSkillByMemberId(HEIST_MEMBER_EMAIL);

        Assert.assertNotNull(memberSkillByMemberId);
        Assert.assertTrue(memberSkillByMemberId.size() > 0);


    }



}
