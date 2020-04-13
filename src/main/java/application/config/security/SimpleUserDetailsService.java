package application.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("simpleUserDetailService")
public class SimpleUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (null == username) throw new UsernameNotFoundException("User not found");
		if (!username.equals("user") && !username.equals("admin")) throw new UsernameNotFoundException("User not found");
		List<GrantedAuthority> authorities = new ArrayList();
        if (username.equals("user")) {
        	authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        	String password = "user";
        	return new User(username, password, authorities);
        }		
        if (username.equals("admin")) {
        	authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        	String password = "admin";
        	return new User(username, password, authorities);        	
        }
		return null;
	}

}
