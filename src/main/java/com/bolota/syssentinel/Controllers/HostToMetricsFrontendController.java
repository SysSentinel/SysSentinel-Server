package com.bolota.syssentinel.Controllers;


import com.bolota.syssentinel.Entities.DTOs.SystemEntityDTO;
import com.bolota.syssentinel.Entities.DTOs.SystemVolatileEntityDTO;
import com.bolota.syssentinel.Entities.SystemEntities.SystemProcessEntity;
import com.bolota.syssentinel.Entities.SystemEntities.SystemVolatileEntity;
import com.bolota.syssentinel.Entities.UserEntities.UserEntity;
import com.bolota.syssentinel.Resource.SystemEntityResources;
import com.bolota.syssentinel.Resource.SystemVolatileEntityResources;
import com.bolota.syssentinel.Resource.UserEntityResources;
import com.bolota.syssentinel.Service.SysSentinelService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.bolota.syssentinel.Service.SysSentinelService.genJSON;

@RequestMapping("/api/metrics")
@RestController
public class HostToMetricsFrontendController {

    @Autowired
    SystemEntityResources ser;

    @Autowired
    SystemVolatileEntityResources sver;

    @Autowired
    UserEntityResources uer;

    @GetMapping("/systems")
    public ResponseEntity<Page<SystemEntityDTO>> sendSystems(@AuthenticationPrincipal Jwt jwt, @RequestHeader("login") String login, @PageableDefault(size = 10) Pageable pageable){
        if (jwt == null) return ResponseEntity.status(409).build();
        if (login == null) return ResponseEntity.status(404).build();
        if (!jwt.getSubject().equals(login)) return ResponseEntity.status(401).build();
        ArrayList<String> systemsUUIDs = uer.getUserEntityByLogin(login).getSystemsInPossession();
        System.out.println(systemsUUIDs);
        ArrayList<SystemEntityDTO> systems = new ArrayList<>();
        for(String uuids: systemsUUIDs){
            systems.add(ser.getByUUID(uuids));
        }
        return ResponseEntity.ok(toPage(systems,pageable));
    }
    @GetMapping("/systemVolatileInfo")
    public ResponseEntity<SystemVolatileEntityDTO> sendSystemVolatileInfo(@AuthenticationPrincipal Jwt jwt, @RequestHeader("login") String login, @RequestParam String uuid, @RequestParam(defaultValue = "cpuLoad,desc") String sort) throws JsonProcessingException {
        System.out.println("teste 1");
        if (jwt == null) return ResponseEntity.status(409).build();
        System.out.println("teste 2");
        if (login == null) return ResponseEntity.status(404).build();
        System.out.println("teste 3");
        if (!jwt.getSubject().equals(login)) return ResponseEntity.status(401).build();
        System.out.println("teste 4");
        System.out.println(uuid);
        if (!sver.existsByUUID(uuid)) return ResponseEntity.status(404).build();
        System.out.println("teste 5");
        SystemVolatileEntityDTO svedto = sver.getByUUID(uuid);
        SystemProcessEntity[] arr = new ObjectMapper().readValue(svedto.getSystemProcessEntities(), SystemProcessEntity[].class);
        List<SystemProcessEntity> spel = new ArrayList<>(Arrays.asList(arr));
        String[] sortMethods = sort.split(",");
        /*
    private String  name;
    private int     pid;
    private double  residentMem; // em MBs
    private double  virtualMem;  // em GBs
    private double  cpuLoad;
    */
        switch (sortMethods[0]){
            case "name":
                if (sortMethods[1].equals("desc")){
                    spel.sort(Comparator.comparing(SystemProcessEntity::getName).reversed());
                }
                else {
                    spel.sort(Comparator.comparing(SystemProcessEntity::getName));
                }
                break;
            case "pid":
                if (sortMethods[1].equals("desc")){
                    spel.sort(Comparator.comparingInt(SystemProcessEntity::getPid).reversed());
                }
                else {
                    spel.sort(Comparator.comparingInt(SystemProcessEntity::getPid));
                }
                break;
            case "residentMem":
                if (sortMethods[1].equals("desc")){
                    spel.sort(Comparator.comparingDouble(SystemProcessEntity::getResidentMem).reversed());
                }
                else {
                    spel.sort(Comparator.comparingDouble(SystemProcessEntity::getResidentMem));
                }
                break;
            case "virtualMem":
                if (sortMethods[1].equals("desc")){
                    spel.sort(Comparator.comparingDouble(SystemProcessEntity::getVirtualMem).reversed());
                }
                else {
                    spel.sort(Comparator.comparingDouble(SystemProcessEntity::getVirtualMem));
                }
                break;
            case "cpuLoad":
                if (sortMethods[1].equals("desc")){
                    spel.sort(Comparator.comparingDouble(SystemProcessEntity::getCpuLoad).reversed());
                }
                else {
                    spel.sort(Comparator.comparingDouble(SystemProcessEntity::getCpuLoad));
                }
                break;
        }
        System.out.println(spel);
        System.out.println();
        svedto.setSystemProcessEntities(genJSON(spel));
        return ResponseEntity.ok(svedto);
    }
    @Modifying
    @Transactional
    @PostMapping("/systemRegister")
    public ResponseEntity<Void> registerHandler(@AuthenticationPrincipal Jwt jwt, @RequestHeader("login") String login, @RequestParam String uuid){
        if (login == null) return ResponseEntity.badRequest().build();
        if (login.equals(" ") ||login.isEmpty()) return ResponseEntity.badRequest().build();
        if (!uer.existsByLogin(login)) return ResponseEntity.status(409).build();
        if (!jwt.getSubject().equals(login)) return ResponseEntity.status(401).build();
        if (!ser.existsByUUID(uuid)) return ResponseEntity.status(404).build();
        System.out.println(uuid);
        UserEntity ue = uer.getUserEntityByLogin(login);
        ue.addSystem(uuid);
        uer.save(ue);
        return ResponseEntity.ok().build();
    }
    public static <T> Page<T> toPage(List<T> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), list.size());

        if (start > end) {
            return new PageImpl<>(List.of(), pageable, list.size());
        }
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }
}
