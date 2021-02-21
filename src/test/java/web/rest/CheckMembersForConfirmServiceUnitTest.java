package web.rest;


import agency.entity.HeistMember;
import agency.services.interfaces.CheckMembersForConfirmService;
import org.junit.Assert;
import agency.ProjectApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.HttpStatus;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootTest(classes = {ProjectApplication.class, TestConfiguration.class})
public class CheckMembersForConfirmServiceUnitTest {

    private static final  String NAME = "Fábrica Nacional de Moneda y Timbre";
    private List<String> memberList = Arrays.asList("helsinki@ag04.com", "Moscow@ag04.com");

    @Autowired
    public CheckMembersForConfirmService checkMembersForConfirmService;


    @Test
     void checkHeistMembersTest(){

        HttpStatus checkHeistMembers = checkMembersForConfirmService.checkHeistMembers(memberList, NAME);

        Assert.assertNotNull(checkHeistMembers);
        Assert.assertEquals(checkHeistMembers, HttpStatus.METHOD_NOT_ALLOWED);


    }


}
