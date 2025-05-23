package restservices.security;

import model.Voluntar;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import persistance.Repo.VoluntarRepo;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final VoluntarRepo repo;

    public UserDetailsServiceImpl(VoluntarRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Voluntar v = repo.findByUsername(username);
        if (v == null) {
            throw new UsernameNotFoundException("Nu există user: " + username);
        }
        return new User(
                v.getUsername(),
                v.getPassword(),         // deja bcrypt-uit în DB
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
