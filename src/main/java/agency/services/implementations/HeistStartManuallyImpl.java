package agency.services.implementations;

import agency.entity.Heist;
import agency.enumeration.Status;
import agency.repository.HeistRepository;
import agency.services.interfaces.HeistStartManually;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HeistStartManuallyImpl implements HeistStartManually {


    private HeistRepository heistRepository;

    public HeistStartManuallyImpl(HeistRepository heistRepository) {
        this.heistRepository = heistRepository;
    }

    @Override
    public HttpStatus startHeistManually(String name) {

        Optional<Heist> findHeist = heistRepository.findById(name);

        Heist heist = findHeist.get();

        if (heist.getStatus().equals(Status.PLANNING)) {

            heist.setStatus(Status.READY);

            heistRepository.save(heist);

        } else return HttpStatus.METHOD_NOT_ALLOWED;

        return HttpStatus.NOT_FOUND;
    }
}
