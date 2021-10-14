package elo7.spaceprobe.service.impl;

import elo7.spaceprobe.domain.Field;
import elo7.spaceprobe.domain.LandReport;
import elo7.spaceprobe.domain.probe.Probe;
import elo7.spaceprobe.service.spec.NavigationService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class NavigationServiceImpl implements NavigationService {

    private Field field;

    @Override
    public String createField(int x, int y) {
        field = Field.builder()
                .fieldXAxis(x)
                .fieldYAxis(y)
                .probes(new ArrayList<Probe>())
                .build();

        StringBuilder field = new StringBuilder("Terreno criado: {" + x + "," + y + "}");
        return field.toString();
    }

    @Override
    public LandReport landProbe(Probe probe){
        String landDetails;
        ArrayList<Probe> currentProbes = field.getProbes();
        ArrayList<int[]> landedProbeLocations = getProbeLocations(currentProbes.size());
        int[] landingShipLocationPreview = {probe.getX(), probe.getY()};

        Boolean hasPermissionToLand = hasActivityPermission(landedProbeLocations, landingShipLocationPreview);

        probe.setId(currentProbes.size());

        if(hasPermissionToLand){
            currentProbes.add(probe);
            field.setProbes(currentProbes);
            landDetails = "Pouso foi um sucesso!";
        } else {
            landDetails = "Pouso abortado por risco de acidentes.";
        }


        return LandReport.builder()
                .field(field)
                .landSuccess(hasPermissionToLand)
                .details(landDetails)
                .build();
    };

    @Override
    public Boolean moveProbe(int probeId, String instructions){
        Boolean movementHappens = true;
        String[] organizedInstructions = instructions.split(",");
        Probe selectedProbe = field.getProbes().stream().filter(probe -> probe.getId() == probeId).findFirst().get();

        for (String instruction: organizedInstructions) {
            switch(instruction){
                case "L":
                    selectedProbe.turnLeft();
                    break;
                case "R":
                    selectedProbe.turnRight();
                    break;
                case "M":
                    movementHappens = moveIfYouCan(selectedProbe);
                    break;
            }

            if(!movementHappens){
                return movementHappens;
            }
        }

        return movementHappens;
    }

    @Override
    public Field getField() {
        return this.field;
    }

    private Boolean moveIfYouCan(Probe probe){
        ArrayList<int[]> currentProbeLocations = getProbeLocations(probe.getId());
        int[] selectedProbeNextLocation = probe.getNextPosition();

        if(!hasActivityPermission(currentProbeLocations, selectedProbeNextLocation)) {
            return false;
        }

        probe.move(
                Array.getInt(selectedProbeNextLocation, 0),
                Array.getInt(selectedProbeNextLocation, 1)
                );

        return true;
    }

    private Boolean hasActivityPermission(java.util.List<int[]> currentProbeLocations, int[] selectedProbeNextLocation) {
        if(currentProbeLocations.size() > 0){
            for (int[] stoppedProbeLocation : currentProbeLocations) {
                if(cantMove(selectedProbeNextLocation, stoppedProbeLocation)){
                    return false;
                }
            }
        } else {
            if(reachedTheEdges(selectedProbeNextLocation)){
                return false;
            }
        }
        return true;
    }

    private Boolean hasLandPermission(java.util.List<int[]> landedProbeLocations, int[] landingProbeLocationPreview) {
        if(landedProbeLocations.size() > 0){
            for (int[] stoppedProbeLocation : landedProbeLocations) {
                if(cantMove(landingProbeLocationPreview, stoppedProbeLocation)){
                    return false;
                }
            }
        } else {
            if(reachedTheEdges(landingProbeLocationPreview)){
                return false;
            }
        }
        return true;
    }

    private boolean cantMove(int[] selectedProbeNextLocation, int[] stoppedProbeLocation) {
        return Arrays.equals(selectedProbeNextLocation, stoppedProbeLocation) ||
                reachedTheEdges(selectedProbeNextLocation);
    }

    private boolean reachedTheEdges(int[] selectedProbeNextLocation) {
        int nextX = Array.getInt(selectedProbeNextLocation,0);
        int nextY = Array.getInt(selectedProbeNextLocation,1);

        Boolean reachedTheEdges =   nextX > this.field.getFieldXAxis() ||
                                    nextX < 0 ||
                                    nextY > this.field.getFieldYAxis() ||
                                    nextY < 0;

        return reachedTheEdges;
    }

    private ArrayList<int[]> getProbeLocations(int selectedProbeId){
        ArrayList<int[]> currentProbeLocations = new ArrayList<int[]>();

        for (Probe probe : this.field.getProbes()){
            if(!probe.getId().equals(selectedProbeId)){
                int[] coordinates = {probe.getX(), probe.getY()};
                currentProbeLocations.add(coordinates);
            }
        }

        return currentProbeLocations;
    }


}
