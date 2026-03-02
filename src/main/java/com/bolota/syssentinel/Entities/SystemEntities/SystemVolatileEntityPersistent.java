package com.bolota.syssentinel.Entities.SystemEntities;

import com.bolota.syssentinel.Entities.SystemEntitiesDTOs.SystemVolatileEntityDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.bolota.syssentinel.Service.SysSentinelService.genJSON;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SystemVolatileEntityPersistent {
    @Id
    @Column(nullable=false, updatable=false)
    private String UUID;
    private String basicComputerInfo;
    private String internetCurrentUsage;    // Hashmap to json
    private String internetAdapters;        // Hashmap to json
    @Lob
    private String systemProcessEntities;   // ArrayList<SystemProcessEntity>

    public SystemVolatileEntityPersistent(SystemVolatileEntityDTO sve){
        this.UUID = sve.getUUID();
        this.basicComputerInfo = genJSON(sve.getBasicComputerInfo());
        this.internetCurrentUsage = genJSON(sve.getInternetCurrentUsage());
        this.internetAdapters = genJSON(sve.getInternetAdapters());
        this.systemProcessEntities = genJSON(sve.getSystemProcessEntities());
    }
}
