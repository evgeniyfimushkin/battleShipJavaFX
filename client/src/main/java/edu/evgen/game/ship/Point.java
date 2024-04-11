package edu.evgen.game.ship;

import lombok.Data;

@Data
public class Point {
    private Integer x;
    private Integer y;

    public Point(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

        private Boolean isHit;
}
