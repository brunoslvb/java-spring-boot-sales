package br.com.sales.api.controllers;

import br.com.sales.api.dtos.CredentialsDTO;
import br.com.sales.api.dtos.TokenDTO;
import br.com.sales.domain.entities.User;
import br.com.sales.exceptions.InvalidPasswordException;
import br.com.sales.security.jwt.JwtService;
import br.com.sales.services.implementations.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody @Valid User user){
        String passwordEncoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncoded);
        return userService.save(user);
    }

    @PostMapping("auth")
    public TokenDTO auth(@RequestBody CredentialsDTO credentials){

        User user = User.builder().login(credentials.getLogin()).password(credentials.getPassword()).build();

        try {
            UserDetails userLogged = userService.auth(user);
            String token = jwtService.generateToken(user);
            return new TokenDTO(user.getLogin(), token);
        } catch (UsernameNotFoundException | InvalidPasswordException ex){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        }

    }
}
