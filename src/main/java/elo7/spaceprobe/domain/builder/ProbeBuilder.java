package elo7.spaceprobe.domain.builder;

import elo7.spaceprobe.domain.enumeration.Compass;
import elo7.spaceprobe.domain.probe.Probe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProbeBuilder {

    public Probe createProbe(String name,
                            Integer x,
                            Integer y,
                            String direction){

        return Probe.builder()
                .name(name)
                .x(x)
                .y(y)
                .direction(Compass.valueOf(direction.toUpperCase()))
                .build();

    }

}
