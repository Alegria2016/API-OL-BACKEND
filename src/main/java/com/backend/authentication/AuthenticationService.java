package com.backend.authentication;

import com.backend.config.JwtService;
import com.backend.dto.UserResponseDTO;
import com.backend.model.User;
import com.backend.enums.Role;
import com.backend.repository.UserRepository;
import com.backend.util.Constant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;


    private final PasswordEncoder passwordEncoder;
    public static final ModelMapper modelMapper = new ModelMapper();

    public AuthenticationService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public AuthenticationResponse register(RegisterRequest request){
        var user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(Constant.USER_ROLE)); //
        userRepository.save(user);
        String token = jwtService.generateToken(user, generateExtraClaims(user));
        boolean isSuccess = true;
        UserResponseDTO u = modelMapper.map(user, UserResponseDTO.class);
        return  new AuthenticationResponse(token,isSuccess,u);
    }




    public AuthenticationResponse login(AuthenticationRequest authenticationRequest){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(), authenticationRequest.getPassword()
        );
        authenticationManager.authenticate(authToken);
        User user = userRepository.findByEmail(authenticationRequest.getEmail()).get();
        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        boolean isSuccess = true;
        UserResponseDTO u = modelMapper.map(user, UserResponseDTO.class);
        return new AuthenticationResponse(jwt,isSuccess,u);
    }


   UserResponseDTO getUserProfileByToken(String token){
      String email = jwtService.extractUsername(getTokenFormRequest(token));
      System.out.println("EMAIL: "+ email);
       User user = userRepository.findByEmail(email).get();
       return  modelMapper.map(user, UserResponseDTO.class);

   }




    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("role", user.getRole().name());
        return extraClaims;
    }


    private String getTokenFormRequest(String token) {

        if(StringUtils.hasText(token) && token.startsWith("Bearer ") ){
            return token.substring(7);

        }

        return null;

    }
}
