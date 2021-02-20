package web.rest;

import org.junit.Assert;
import agency.ProjectApplication;
import agency.dto.HeistDTO;
import agency.entity.Heist;
import agency.enumeration.Status;
import agency.repository.HeistRepository;
import agency.services.converters.HeistConverter;
import agency.services.interfaces.AutomaticHeistStartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.record.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;


import java.time.LocalDateTime;

@SpringBootTest(classes = {ProjectApplication.class, TestConfiguration.class})
public class AutomaticHeistStartServiceUnitTest {



    private static final  String NAME = "Fábrica Nacional de Moneda y Timbre";
    private static final  String LOCATION = "Madrid";
    private static final Status STATUS = Status.PLANNING;
    private static final LocalDateTime START_DATE_TIME = LocalDateTime.of(2020, 5, 15, 12, 10, 1);
    private static final  LocalDateTime END_DATE_TIME = LocalDateTime.of(2020, 5, 16, 11, 15, 35);
    private static final Status STATUS_AVAILABLE = Status.AVAILABLE;


    @Autowired
    private HeistRepository heistRepository;
    @Autowired
    private AutomaticHeistStartService automaticHeistStartService;
    @Autowired
    private HeistConverter heistConverter;

    Record record;


    public HeistDTO newHeistDTO(){

        Heist heist = heistRepository.findById(NAME).get();

        HeistDTO heistCustom = heistConverter.toDto(heist);

        return heistCustom;

    }

    @Test
    void startHeistAutomaticallyAndChangeStatusTest(){

       automaticHeistStartService.startHeistAutomaticallyAndChangeStatus();
       //record

    }





}
