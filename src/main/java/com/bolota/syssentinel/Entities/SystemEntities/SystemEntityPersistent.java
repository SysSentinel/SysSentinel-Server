package com.bolota.syssentinel.Entities.SystemEntities;

import com.bolota.syssentinel.Entities.SystemEntitiesDTOs.SystemEntityDTO;
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
public class SystemEntityPersistent {
    @Id
    @Column(nullable=false, updatable=false)
    private String UUID;
    private String name;
    private String os;
    private String host;
    private String cpu;
    private List<String> gpu;
    private double memRamMax;
    public SystemEntityPersistent(SystemEntityDTO se){
        this.UUID       = se.getUUID();
        this.name       = se.getName();
        this.os         = se.getOs();
        this.host       = se.getHost();
        this.cpu        = se.getCpu();
        this.gpu        = se.getGpu();
        this.memRamMax  = se.getMemRamMax();
    }
}
