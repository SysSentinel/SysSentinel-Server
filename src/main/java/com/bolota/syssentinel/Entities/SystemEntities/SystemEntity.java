package com.bolota.syssentinel.Entities.SystemEntities;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class SystemEntity {
    private String UUID;
    private String name;
    private String os;
    private String host;
    private String cpu;
    private List<String> gpu;
    private double memRamMax;
    private SystemVolatileEntity systemVolatileEntity;
}