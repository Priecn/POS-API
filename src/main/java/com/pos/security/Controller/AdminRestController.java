package com.pos.security.Controller;

import com.pos.security.model.POSUser;
import com.pos.security.utility.POSTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    private String tokenHeader;
    private POSTokenUtil posTokenUtil;
    private UserDetailsService userDetailsService;

    @Autowired
    public AdminRestController(@Value("${jwt.header}") String tokenHeader,
                               POSTokenUtil posTokenUtil,
                               @Qualifier("POSUserDe" +
                                                "tailsService")UserDetailsService userDetailsService) {
        this.tokenHeader = tokenHeader;
        this.posTokenUtil = posTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping(value = "/details")
    public POSUser getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = posTokenUtil.getUsernameFromToken(token);
        POSUser user = (POSUser) userDetailsService.loadUserByUsername(username);
        return user;
    }
}
