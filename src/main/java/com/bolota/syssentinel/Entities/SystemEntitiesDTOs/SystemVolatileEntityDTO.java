package com.bolota.syssentinel.Entities.SystemEntitiesDTOs;
import com.bolota.syssentinel.Entities.SystemEntities.SystemProcessEntity;
import com.bolota.syssentinel.Entities.SystemEntities.SystemVolatileEntityPersistent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class SystemVolatileEntityDTO {
    private String UUID;
    private HashMap<String, String> basicComputerInfo;
    private HashMap<String, Double> internetCurrentUsage;
    private HashMap<String, String> internetAdapters;
    private ArrayList<SystemProcessEntity> systemProcessEntities;
    public SystemVolatileEntityDTO(SystemVolatileEntityPersistent sveDto) throws JsonProcessingException {
        this.UUID = sveDto.getUUID();
        this.basicComputerInfo = new ObjectMapper().readValue(sveDto.getBasicComputerInfo(), HashMap.class);
        this.internetAdapters = new ObjectMapper().readValue(sveDto.getInternetAdapters(), HashMap.class);
        this.internetCurrentUsage = new ObjectMapper().readValue(sveDto.getInternetCurrentUsage(), HashMap.class);
        this.systemProcessEntities = new ObjectMapper().readValue(sveDto.getSystemProcessEntities(), ArrayList.class);
    }
}
