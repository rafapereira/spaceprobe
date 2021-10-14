package elo7.spaceprobe.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Instruction {

    LEFT("L"), RIGHT("R"), MOVE("M");

    private String value;

}
