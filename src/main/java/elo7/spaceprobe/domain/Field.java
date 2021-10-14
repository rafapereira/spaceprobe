package elo7.spaceprobe.domain;

import elo7.spaceprobe.domain.probe.Probe;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@Builder
public class Field {

    private int fieldXAxis;
    private int fieldYAxis;
    private ArrayList<Probe> probes;

}
