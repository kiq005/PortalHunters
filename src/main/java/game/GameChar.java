package game;

import java.util.ArrayList;

/**
 * Created by kaique on 08/07/16.
 */
public class GameChar {
    private String name;
    private String location;
    private char sex;

    private ArrayList<String> knowLocations;
    // TODO: Know locations list
    public int HP, MP, CR, XP, level;   // Health, Magic Power, Cristal Power, Experience, Level
    public int STR, MAG, AGI, RES, LCK; // Strength, Magic, Agility, Resistance, Luck

    public GameChar(String name, char sex, String location){
        this.name = name;
        this.sex = sex;
        this.location = location;

        this.HP = 10;
        this.MP = 10;
        this.CR = 10;
        this.XP = 0;
        this.level = 1;

        knowLocations = new ArrayList<>();

        STR = MAG = AGI = RES = LCK = 1;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public ArrayList<String> getKnowLocations() {
        return knowLocations;
    }

    public void setKnowLocations(ArrayList<String> knowLocations) {
        this.knowLocations = knowLocations;
    }
}
