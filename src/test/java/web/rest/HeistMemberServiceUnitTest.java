package web.rest;

import agency.ProjectApplication;
import agency.dto.EligibleMembersDTO;
import agency.dto.HeistMemberDTO;
import agency.entity.HeistMember;
import agency.enumeration.Status;
import agency.repository.HeistMemberRepository;
import agency.services.converters.HeistMemberConverter;
import agency.services.interfaces.HeistMemberService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;


@SpringBootTest(classes = {ProjectApplication.class, TestConfiguration.class})
public class HeistMemberServiceUnitTest {

    @Autowired
    HeistMemberService heistMemberService;
    @Autowired
    HeistMemberRepository heistMemberRepository;
    @Autowired
    HeistMemberConverter heistMemberConverter;


    private static final String HEIST_MEMBER_NAME = "Helsinki";
    private static final String HEIST_NAME = "Fábrica Nacional de Moneda y Timbre";
    private static final String EMAIL = "helsinki@ag04.com";
    private static final Status STATUS = Status.AVAILABLE;


    public HeistMemberDTO converterEntityToDto() {

        HeistMember heistMember = heistMemberRepository.findById(EMAIL).get();

        HeistMemberDTO heistMemberDTO = heistMemberConverter.toDto(heistMember);

        return heistMemberDTO;

    }

    @Test
    void saveHeistMemberTest() {

        HeistMember findById = heistMemberRepository.findById(EMAIL).get();

        HeistMember saveHeistMemberService = heistMemberService.saveHeistMember(converterEntityToDto());

        heistMemberRepository.saveAndFlush(saveHeistMemberService);

        Assert.assertEquals(findById.getEmail(), saveHeistMemberService.getEmail());
        Assert.assertEquals(findById.getName(), saveHeistMemberService.getName());
        Assert.assertEquals(findById.getStatus(), saveHeistMemberService.getStatus());
        Assert.assertEquals(findById.getSex(), saveHeistMemberService.getSex());

    }


    @Test
    void findHeistMemberByIdTest() {

        HeistMember heistMemberById = heistMemberService.findHeistMemberById(EMAIL).get();

        Assert.assertNotNull(heistMemberById);
        Assert.assertEquals(heistMemberById.getStatus(), STATUS);
        Assert.assertEquals(heistMemberById.getName(), HEIST_MEMBER_NAME);
        Assert.assertEquals(heistMemberById.getEmail(), EMAIL);

    }

    @Test
    void findHeistMemberByStatusAndIdTest() {

        HeistMember heistMemberByStatusAndId = heistMemberService.findHeistMemberByStatusAndId(EMAIL).get();

        Assert.assertNotNull(heistMemberByStatusAndId);
        Assert.assertTrue(heistMemberByStatusAndId.getStatus() == STATUS);
        Assert.assertEquals(heistMemberByStatusAndId.getEmail(), EMAIL);
    }

    @Test
    void findEligibleHeistMemberTest() {

        EligibleMembersDTO eligibleHeistMember = heistMemberService.findEligibleHeistMember(HEIST_NAME);

        Assert.assertNotNull(eligibleHeistMember);
        Assert.assertTrue(eligibleHeistMember.getMembers().size() > 0);

    }

}
