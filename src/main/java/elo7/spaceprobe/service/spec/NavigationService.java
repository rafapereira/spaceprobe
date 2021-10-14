package elo7.spaceprobe.service.spec;

import elo7.spaceprobe.domain.Field;
import elo7.spaceprobe.domain.LandReport;
import elo7.spaceprobe.domain.probe.Probe;

public interface NavigationService {

    String createField(int x, int y);

    LandReport landProbe(Probe probe);

    Boolean moveProbe(int probeId, String instructions);

    Field getField();

}
