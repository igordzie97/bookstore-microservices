package com.agh.bookstoreAccounts.User;

import com.agh.bookstoreAccounts.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class UserRestController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtConfig jwtConfig;

    @GetMapping("/registration")
    public String registration(Model model) {
        return "To register user perform a POST request";
    }


    @PostMapping(value = "/test")
    public ResponseEntity<String> postFoos() {
        return new ResponseEntity<>("Your message here", HttpStatus.OK);

    }

    @PostMapping(value = "/registration")
    public String registerUserAccount(@Valid User userData, HttpServletRequest request) {
        log.info("Registering user account with information: {}", userData);
        userService.save(userData, true);
        return "user added";
    }

    @RequestMapping(value = "/logged/sessionRoles", method = RequestMethod.GET)
    @ResponseBody
    public LoggedUserDTO currentUserNameSimple(@RequestParam String header) {
        log.info(header);
        LoggedUserDTO loggedUserDTO = new LoggedUserDTO();

        String token = header.replace(jwtConfig.getPrefix(), "");
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret().getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            log.info(username);
            log.info("claims, {}", claims);
            if(username != null) {
                @SuppressWarnings("unchecked")
                List<String> authorities = (List<String>) claims.get("authorities");
                Set<String> roles = authorities.stream().collect(Collectors.toSet());
                log.info("{}", roles);
                Long userid = userRepository.findByUsername(claims.getSubject()).getId();
                loggedUserDTO.setRoles(roles);
                loggedUserDTO.setUser_id(userid);

                return loggedUserDTO;
            }
        } catch (Exception e) {
            log.info("ERror" + e.getMessage());
            return null;
        }
        return null;
    }


    //TEST SESSION
    @RequestMapping(value = "/logged/session", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity currentUserNameSimple(HttpServletRequest request) {
        String header = request.getHeader(jwtConfig.getHeader());
        return getResponseSession(header);
    }

    private ResponseEntity getResponseSession(String header) {
        String token = header.replace(jwtConfig.getPrefix(), "");
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret().getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            log.info(username);
            if(username != null) {
                @SuppressWarnings("unchecked")
                List<String> authorities = (List<String>) claims.get("authorities");
                Set<String> roles = authorities.stream().collect(Collectors.toSet());
                log.info("{}", roles);
                return ResponseEntity.status(HttpStatus.OK).body(roles);
            }
        } catch (Exception e) {
            log.info("ERror" + e.getMessage());
            return (ResponseEntity<Set<String>>) ResponseEntity.ok();
        }
        return null;
    }

    //getID
    @RequestMapping(value = "/logged/getID", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Long> getUserID(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username;
        Object principal = auth.getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        log.info(username);
        return ResponseEntity.ok(userRepository.findByUsername(username).getId());
    }



    @GetMapping("/logged/test_tylko_admin")
    public String login() {
        return "OK - dostep tylko dla zalogowanego";
    }
}
