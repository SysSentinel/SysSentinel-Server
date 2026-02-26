package com.bolota.syssentinel.Entities.DTOs;

import com.bolota.syssentinel.Entities.SystemEntities.SystemEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class SystemEntityDTO {
    @Id
    @Column(nullable=false, updatable=false)
    private String UUID;
    private String name;
    private String os;
    private String host;
    private String cpu;
    private List<String> gpu;
    private double memRamMax;
    public SystemEntityDTO(SystemEntity se){
        this.UUID       = se.getUUID();
        this.name       = se.getName();
        this.os         = se.getOs();
        this.host       = se.getHost();
        this.cpu        = se.getCpu();
        this.gpu        = se.getGpu();
        this.memRamMax  = se.getMemRamMax();
    }
}
