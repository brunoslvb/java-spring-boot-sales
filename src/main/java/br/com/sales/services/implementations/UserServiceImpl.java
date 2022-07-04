package br.com.sales.services.implementations;

import br.com.sales.domain.entities.User;
import br.com.sales.domain.repositories.UserRepository;
import br.com.sales.exceptions.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userFound = userRepository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados"));

        String[] roles = userFound.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return org.springframework.security.core.userdetails.User
            .builder()
            .username(userFound.getLogin())
            .password(userFound.getPassword())
            .roles(roles)
            .build();
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public UserDetails auth(User user){
        UserDetails userDetails = loadUserByUsername(user.getLogin());

        if(passwordEncoder.matches(user.getPassword(), userDetails.getPassword()))
            return userDetails;

        throw new InvalidPasswordException("Senha inválida");
    }

}
