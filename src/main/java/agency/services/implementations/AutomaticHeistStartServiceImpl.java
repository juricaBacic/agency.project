package agency.services.implementations;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import agency.dto.HeistDTO;
import agency.entity.Heist;
import agency.enumeration.Status;
import agency.repository.HeistRepository;
import agency.services.interfaces.AutomaticHeistStartService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@EnableScheduling
@Configuration
@Service
public class AutomaticHeistStartServiceImpl implements AutomaticHeistStartService {


    HeistRepository heistRepository;
    CurrentDateTime currentDateTime;


    public AutomaticHeistStartServiceImpl(HeistRepository heistRepository, CurrentDateTime currentDateTime) {
        this.heistRepository = heistRepository;
        this.currentDateTime = currentDateTime;
    }

    @Override
    @Scheduled(fixedDelay = 1000)
    public void startHeistStatusAutomatically() {

        LocalDateTime localDateTime = LocalDateTime.now();

        List<Heist> findAllHeist = heistRepository.findAll();

        if (!findAllHeist.isEmpty()) {

            for (Heist heist : findAllHeist) {

                if (localDateTime.isAfter(heist.getStartTime()) && localDateTime.isBefore(heist.getEndTime())) {

                    heist.setStatus(Status.IN_PROGRESS);
                    heistRepository.save(heist);
                }
                else if (localDateTime.isAfter(heist.getEndTime())) {
                    heist.setStatus(Status.FINISHED);
                    heistRepository.save(heist);
                }
            }
        }

    }
}
