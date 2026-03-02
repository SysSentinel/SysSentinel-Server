package com.bolota.syssentinel.Entities.SystemEntitiesDTOs;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class SystemEntityDTO {
    private String UUID;
    private String name;
    private String os;
    private String host;
    private String cpu;
    private List<String> gpu;
    private double memRamMax;
    private SystemVolatileEntityDTO systemVolatileEntityDTO;
}