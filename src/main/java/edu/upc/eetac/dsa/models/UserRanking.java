package edu.upc.eetac.dsa.models;

import edu.upc.eetac.dsa.util.RandomUtils;

public class UserRanking {
    private String name;
    private Integer points;

    public UserRanking() {}

    public UserRanking(String name, int points) {

        this.name = name;
        this.points = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoints(Integer points) { this.points = points; }
}
