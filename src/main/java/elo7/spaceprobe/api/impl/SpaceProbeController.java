package elo7.spaceprobe.api.impl;

import elo7.spaceprobe.domain.LandReport;
import elo7.spaceprobe.domain.builder.ProbeBuilder;
import elo7.spaceprobe.service.spec.NavigationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spaceprobe")
@Api(value = "SpaceProbe", tags = "SpaceProbe")
public class SpaceProbeController {

    @Autowired
    private NavigationService navigationService;

    @Autowired
    private ProbeBuilder probeBuilder;

    @GetMapping("/field")
    public ResponseEntity<?> getField(){
        return ResponseEntity.ok(navigationService.getField());
    }

    @PostMapping("/field")
    public ResponseEntity<?> createField(@RequestParam(value = "xAxis", defaultValue = "1") Integer xAxis,
                                         @RequestParam(value = "yAxis", defaultValue = "1") Integer yAxis){
        return ResponseEntity.ok(navigationService.createField(xAxis, yAxis));
    }

    @PostMapping("/land")
    public ResponseEntity<?> landProbe(@RequestParam(value = "name", defaultValue = "Humberto") String name,
                                       @RequestParam(value = "x", defaultValue = "1") Integer x,
                                       @RequestParam(value = "y", defaultValue = "1") Integer y,
                                       @RequestParam(value = "direction", defaultValue = "N") String direction){

        LandReport landReport = navigationService.landProbe(probeBuilder.createProbe(name, x, y, direction));

        if(landReport.getLandSuccess()){
            return ResponseEntity.status(HttpStatus.CREATED).body(landReport);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(landReport);
    }

    @PostMapping("/move")
    public ResponseEntity<?> moveProbe(@RequestParam(value = "probeId", defaultValue = "0") int id,
                                       @RequestParam(value = "instructions", defaultValue = "L,R,M,R,L,M,M") String instructions){
        Boolean moveResult = navigationService.moveProbe(id, instructions);

        if(moveResult){
            return ResponseEntity.ok("Movimento realizado como esperado!");
        }

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Movimento não ocorreu conforme esperado! Consulte os detalhes do terreno para mais informações.");
    }

}
