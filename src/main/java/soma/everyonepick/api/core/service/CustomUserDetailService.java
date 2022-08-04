package soma.everyonepick.api.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soma.everyonepick.api.core.oauth2.dto.SecurityUser;
import soma.everyonepick.api.user.entity.User;
import soma.everyonepick.api.user.service.UserService;

@RequiredArgsConstructor
@Service("userDetailsService")
public class CustomUserDetailService implements UserDetailsService {
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String clientId) throws UsernameNotFoundException {
        User user = userService.findByClientId(clientId);
        return new SecurityUser(user);
    }
}
