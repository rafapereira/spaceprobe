package elo7.spaceprobe.domain.probe;

import elo7.spaceprobe.domain.enumeration.Compass;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class Probe {

    private Integer id;

    private String name;

    private Integer x;

    private Integer y;

    private Compass direction;

    public void turnRight(){
     this.direction = this.direction ==  Compass.W ? Compass.N : Compass.values()[direction.ordinal()+1];
    }

    public void turnLeft(){
        this.direction = this.direction ==  Compass.N ? Compass.W : Compass.values()[direction.ordinal()-1];
    }

    public int[] getNextPosition(){
        Integer nextX = this.x;
        Integer nextY = this.y;

        switch (this.direction){
            case N:
                nextY++;
                break;
            case E:
                nextX++;
                break;
            case S:
                nextY--;
                break;
            case W:
                nextX--;
                break;

            default: break;
        }

        int[] nextPosition = {nextX, nextY};

        return nextPosition;
    }

    public void move(int newX, int newY){
        this.x = newX;
        this.y = newY;
    };

}
