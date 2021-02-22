package agency.services.interfaces;

import org.springframework.http.HttpStatus;

import java.util.List;

public interface CheckMembersForConfirmService {


    HttpStatus checkAndAddHeistMembersForHeist(List<String> membersList, String name);
}
