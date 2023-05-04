package com.sudarshan.edgeservice.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class UserController {

	/*@GetMapping("user")
	public Mono<User> getUser() {
	  return ReactiveSecurityContextHolder.getContext()
			  							.map(SecurityContext::getAuthentication)
			  							.map(authentication -> (OidcUser) authentication.getPrincipal())
			  							.map(oidcUser -> new User(
													        oidcUser.getPreferredUsername(),
													        oidcUser.getGivenName(),
													        oidcUser.getFamilyName(),
													        List.of("employee", "customer")
			  												)
			  								);
	}*/
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@GetMapping("user")
	public Mono<User> getUser(@AuthenticationPrincipal OidcUser oidcUser) {
		log.info("Fetching information about the currently authenticated user");
		var user = new User( 
				oidcUser.getPreferredUsername(),
				oidcUser.getGivenName(),
				oidcUser.getFamilyName(),
				oidcUser.getClaimAsStringList("roles")
				//List.of("employee", "customer") //hard coded for testing
				);
		return Mono.just(user); 
	}

}
