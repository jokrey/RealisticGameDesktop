package jokrey.game.realistic_game.engines;

import jokrey.utilities.animation.util.AEColor;

import java.text.DecimalFormat;

public class PlayerStats implements Comparable<PlayerStats> {
    public AEColor color;

    protected int rounds_won = 0;
    public void add_win() {rounds_won++;}
    protected int number_of_kills = 0; public void add_kill() {number_of_kills++;}
    protected double damage_dealt = 0; public void add_dealt_damage(double damage) {damage_dealt+=damage;}
    protected double damage_received = 0; public void add_received_damage(double damage) {damage_received+=damage;}
    protected int number_of_deaths = 0; public void add_death() {number_of_deaths++;}

    protected int longest_streak = 0;
    public void calc_streak(int streakLength) {
        if(streakLength>longest_streak)
            longest_streak=streakLength;
    }

    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return number_of_kills+"|"+number_of_deaths+"("+rounds_won+",+"+df.format(damage_dealt)+",-"+df.format(damage_received)+")";
    }

    @Override public int compareTo(PlayerStats playerStats) {
        return Integer.compare(playerStats.getCompareNumber(), getCompareNumber());
    }

    private int getCompareNumber() {
        return rounds_won*2 + number_of_kills - number_of_deaths/2;
    }
}
