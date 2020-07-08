package com.springdemo.service;

import com.springdemo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.springdemo.model.Account;
import com.springdemo.model.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Transactional(readOnly=true)
@Service("JwtUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {

    //@Inject AccountDao accountDao;
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

//    @Transactional
//    public Account findByUsername(String name) {
//        return accountRepository.findByUsername(name);
//    }

    /**
     * should return an org.springframework.security.core.userdetails.User Object.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //List<Account> accountList = accountRepository.findByUsername(username);
        //Account account = null;
//        if (accountList != null)
//            account = accountList.get(0);
        Account account = accountRepository.findByUsername(username);

        if(account==null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }
        else if (account.getRoles().isEmpty()) {
            throw new UsernameNotFoundException("User " + username + " has no authorities");
        }

        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        User user = new org.springframework.security.core.userdetails.User( //user implements UserDetail
                account.getUsername(),
                //account.getPassword().toLowerCase(),
                passwordEncoder.encode(account.getPassword().toLowerCase()), //Jabbari: because we have saved plian password in DB!
                account.isEnabled(),
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                getAuthorities(account.getRoles()));
        return user;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRolesAsList(roles));
        return authList;
    }

    public List<String> getRolesAsList(Set<Role> roles) {
        List <String> rolesAsList = new ArrayList<String>();
        for(Role role : roles){
            rolesAsList.add(role.getName());
        }
        return rolesAsList;
    }

    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

}
