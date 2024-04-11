package edu.evgen.game.ship;
public enum Size {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4);
    private Integer size;

    Size(Integer size) {
        this.size = size;
    }
    public Integer getSize(){return size;}
}
