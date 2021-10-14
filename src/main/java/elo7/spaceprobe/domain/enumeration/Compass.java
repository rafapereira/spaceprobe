package elo7.spaceprobe.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@AllArgsConstructor
public enum Compass {

    N("NORTH"), E("EAST"), S("SOUTH"), W("WEST");

    String description;

}
