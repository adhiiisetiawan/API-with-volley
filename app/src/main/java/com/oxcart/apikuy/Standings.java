package com.oxcart.apikuy;

public class Standings {
    private int tablePosition;
    private String clubName;
    private String logoClub;
    private int playedGames;
    private int goalDifference;
    private int points;

    public Standings() {
    }

    public Standings(int tablePosition, String clubName, String logoClub, int playedGames, int goalDifference, int points) {
        this.tablePosition = tablePosition;
        this.clubName = clubName;
        this.logoClub = logoClub;
        this.playedGames = playedGames;
        this.goalDifference = goalDifference;
        this.points = points;
    }

    public int getTablePosition() {
        return tablePosition;
    }

    public void setTablePosition(int tablePosition) {
        this.tablePosition = tablePosition;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getLogoClub() {
        return logoClub;
    }

    public void setLogoClub(String logoClub) {
        this.logoClub = logoClub;
    }

    public int getPlayedGames() {
        return playedGames;
    }

    public void setPlayedGames(int playedGames) {
        this.playedGames = playedGames;
    }

    public int getGoalDifference() {
        return goalDifference;
    }

    public void setGoalDifference(int goalDifference) {
        this.goalDifference = goalDifference;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
