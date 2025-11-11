package booking_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class AuditorAwareImpl implements AuditorAware {

    public String getAuthenticatedUserId(){
        return "";
    }

    @Override
    public Optional getCurrentAuditor() {
        return Optional.of(UUID.randomUUID());
    }
}
