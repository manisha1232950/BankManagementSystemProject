
package com.BankManagementSystemProject.controller;

 import com.BankManagementSystemProject.entity.JwtAuthRequest;
 import com.BankManagementSystemProject.entity.JwtAuthResponse;
 import com.BankManagementSystemProject.security.CustomUserDetailsService;

 import com.BankManagementSystemProject.security.JwtTokenHelper;
 import
        org.springframework.beans.factory.annotation.Autowired; import
        org.springframework.http.HttpStatus; import
        org.springframework.http.ResponseEntity; import
        org.springframework.security.authentication.AuthenticationManager; import
        org.springframework.security.authentication.
                UsernamePasswordAuthenticationToken; import
        org.springframework.security.core.userdetails.UserDetails;
 import
        org.springframework.web.bind.annotation.PostMapping; import
        org.springframework.web.bind.annotation.RequestBody; import
        org.springframework.web.bind.annotation.RequestMapping; import
        org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final CustomUserDetailsService customUserDetailService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;


    @Autowired private AuthenticationManager authenticationManager;

    AuthController(CustomUserDetailsService customUserDetailService) {
        this.customUserDetailService = customUserDetailService; }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(
            @RequestBody JwtAuthRequest request) {

        this.authenticate(request.getUsername(),request.getPassword());
        UserDetails userDetails =
                this.customUserDetailService.loadUserByUsername(request.getUsername());
        String token = this.jwtTokenHelper.generateToken(userDetails);
        JwtAuthResponse response=new JwtAuthResponse(); response.setToken(token);
        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);


    }

    private void authenticate(String username, String password) {

        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(username, password);

        try {
            this.authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            throw new RuntimeException("Invalid username or password");
        }}

}


