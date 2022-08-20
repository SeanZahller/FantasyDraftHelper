package com.example.drafthelper;

import javafx.beans.property.SimpleStringProperty;

public class Player {

    private SimpleStringProperty rank;
    private SimpleStringProperty name;
    private SimpleStringProperty team;
    private SimpleStringProperty position;

    public Player () {
    }

    public Player (String s1, String s2, String s3, String s4) {

        rank = new SimpleStringProperty(s1);
        name = new SimpleStringProperty(s2);
        position = new SimpleStringProperty(s3);
        team = new SimpleStringProperty(s4);
    }

    public String getRank() {

        return rank.get();
    }
    public void setRank(String s) {

        rank.set(s);
    }

    public String getName() {

        return name.get();
    }
    public void setName(String s) {

        name.set(s);
    }

    public String getPosition() {

        return position.get();
    }
    public void setPosition(String s) {

        position.set(s);
    }

    public String getTeam() {

        return team.get();
    }
    public void setTeam(String s) {

        team.set(s);
    }


}