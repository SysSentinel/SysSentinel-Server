package com.bolota.syssentinel.Entities.UserEntities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false,length=60)
    private String passwordHash;
    private ArrayList<String> systemsInPossession;

    public UserEntity(String login,String passwordHash){
        this.login = login;
        this.passwordHash = passwordHash;
        this.systemsInPossession = new ArrayList<>();
    }
    public void addSystem(String uuid){
        systemsInPossession.add(uuid);
    }
    public boolean isSystemInPossession(String uuid){
        for (String uuidPossession : systemsInPossession){
            if (uuid.equals(uuidPossession)){
                return true;
            }
        }
        return false;
    }
    public boolean removeSystem(String uuid){
        for (String uuidPossession : systemsInPossession){
            if (uuid.equals(uuidPossession)){
                systemsInPossession.remove(uuidPossession);
                return true;
            }
        }
        return false;
    }
}
