package com.bolota.syssentinel.Controllers;

import com.bolota.syssentinel.Entities.UserEntities.UserEntity;
import com.bolota.syssentinel.Resource.SystemEntityResources;
import com.bolota.syssentinel.Resource.UserEntityResources;
import org.aspectj.weaver.IClassFileProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;


@RequestMapping("/api/user")
@RestController
public class HostToUserFrontendController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserEntityResources uer;

    @Autowired
    SystemEntityResources ser;

    @Autowired
    JwtEncoder jwtEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> loginHandler(@RequestParam HashMap<String,String> loginInfo){
        if (loginInfo==null) return ResponseEntity.badRequest().build();

        if (loginInfo.get("login") == null || loginInfo.get("password") == null) return ResponseEntity.badRequest().build();

        if (!uer.existsByLogin(loginInfo.get("login"))) return ResponseEntity.notFound().build();

        UserEntity ue = uer.getUserEntityByLogin(loginInfo.get("login"));
        if (!passwordEncoder.matches(loginInfo.get("password").trim(),ue.getPasswordHash())) return ResponseEntity.status(401).build();

        return ResponseEntity.ok(issueLoginToken(loginInfo.get("login")));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerHandler(@RequestParam HashMap<String,String> loginInfo){
        if (loginInfo==null) return ResponseEntity.badRequest().build();

        if (loginInfo.get("login") == null || loginInfo.get("password") == null) return ResponseEntity.badRequest().build();

        if (loginInfo.get("login").trim().isEmpty()||loginInfo.get("password").trim().isEmpty()) return ResponseEntity.badRequest().build();

        if (uer.existsByLogin(loginInfo.get("login"))) return ResponseEntity.status(409).build();

        String psswrd = passwordEncoder.encode(loginInfo.get("password").trim());
        uer.save(new UserEntity(loginInfo.get("login"), psswrd));
        System.out.println("register loginfo: "+loginInfo + "           " + psswrd);
        return ResponseEntity.ok(issueLoginToken(loginInfo.get("login")));
    }
    @Modifying
    @GetMapping("/remove")
    public ResponseEntity<HashMap> removeUUIDFromUser(@RequestParam ("user") String user, @RequestParam ("systemUUID") String uuid){
        UserEntity ue = uer.getUserEntityByLogin(user);
        HashMap<String,Boolean> hm = new HashMap<>();
        if (ser.existsByUUID(uuid) && ue.getSystemsInPossession().contains(uuid)){
            hm.put("DeleteResult",ue.getSystemsInPossession().remove(uuid));
            uer.save(ue);
            return new ResponseEntity<>(hm, HttpStatusCode.valueOf(200));
        }
        hm.put("DeleteResult", false);
        return new ResponseEntity<>(hm, HttpStatusCode.valueOf(404));
    }
    public String issueLoginToken(String login) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("SysSentinelHost")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(60L * 60L * 3L))
                .subject(login)
                .claim("roles", List.of("USER"))
                .build();
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header,claims)).getTokenValue();
    }
}
