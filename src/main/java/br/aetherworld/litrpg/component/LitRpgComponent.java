package br.aetherworld.litrpg.component;

import org.ladysnake.cca.api.v3.component.ComponentV3;

public interface LitRpgComponent extends ComponentV3 {
    int getLevel();
    void setLevel(int value);
    int getExperience();
    void setExperience(int value);
    int getExperienceMax();
    void setExperienceMax(int value);
    int getFreePoints();
    void setFreePoints(int value);
    int getSystemPoints();
    void setSystemPoints(int value);
    double getMana();
    void setMana(double value);

    int getStrength();
    int getAgility();
    int getVitality();
    int getIntelligence();
    int getPerception();

    boolean spendAttributePoint(String attribute);
    void addLevelUpStats();
    boolean spendSystemPoints(int amount);
    void addExperience(int amount);
    void addSystemPoints(int amount);
    void restoreMana(double amount);
    boolean consumeMana(double amount);
}
