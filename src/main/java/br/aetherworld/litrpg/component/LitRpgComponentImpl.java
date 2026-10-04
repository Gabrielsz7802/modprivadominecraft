package br.aetherworld.litrpg.component;

import br.aetherworld.litrpg.network.ModNetworking;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public final class LitRpgComponentImpl implements LitRpgComponent, AutoSyncedComponent {
    private final Entity provider;

    private int level = 1;
    private int experience = 0;
    private int experienceMax = 100;
    private int freePoints = 0;
    private int systemPoints = 0;
    private double mana = 100.0;

    private int strength = 10;
    private int agility = 10;
    private int vitality = 10;
    private int intelligence = 10;
    private int perception = 10;

    public LitRpgComponentImpl(Entity provider) {
        this.provider = provider;
    }

    @Override public int getLevel() { return level; }
    @Override public void setLevel(int value) { level = Math.max(1, value); sync(); }

    @Override public int getExperience() { return experience; }
    @Override public void setExperience(int value) { experience = Math.max(0, value); sync(); }

    @Override public int getExperienceMax() { return experienceMax; }
    @Override public void setExperienceMax(int value) { experienceMax = Math.max(1, value); sync(); }

    @Override public int getFreePoints() { return freePoints; }
    @Override public void setFreePoints(int value) { freePoints = Math.max(0, value); sync(); }

    @Override public int getSystemPoints() { return systemPoints; }
    @Override public void setSystemPoints(int value) { systemPoints = Math.max(0, value); sync(); }

    @Override public double getMana() { return Math.min(mana, getMaxMana()); }
    @Override public void setMana(double value) {
        mana = Math.max(0.0, Math.min(value, getMaxMana()));
        sync();
    }

    public double getMaxMana() {
        return 100.0 + intelligence * 10.0;
    }

    @Override public int getStrength() { return strength; }
    @Override public int getAgility() { return agility; }
    @Override public int getVitality() { return vitality; }
    @Override public int getIntelligence() { return intelligence; }
    @Override public int getPerception() { return perception; }

    @Override
    public boolean spendAttributePoint(String attribute) {
        if (freePoints <= 0) return false;

        switch (attribute) {
            case "forca" -> strength++;
            case "agilidade" -> agility++;
            case "vitalidade" -> vitality++;
            case "inteligencia" -> intelligence++;
            case "percepcao" -> perception++;
            default -> { return false; }
        }

        freePoints--;
        mana = Math.min(mana, getMaxMana());
        sync();
        return true;
    }

    @Override
    public boolean spendSystemPoints(int amount) {
        if (amount <= 0 || systemPoints < amount) return false;
        systemPoints -= amount;
        sync();
        return true;
    }

    @Override
    public void addExperience(int amount) {
        if (amount > 0) {
            experience += amount;
            sync();
        }
    }

    @Override
    public void addSystemPoints(int amount) {
        if (amount > 0) {
            systemPoints += amount;
            sync();
        }
    }

    @Override
    public void restoreMana(double amount) {
        if (amount > 0) {
            mana = Math.min(getMaxMana(), mana + amount);
            sync();
        }
    }

    @Override
    public boolean consumeMana(double amount) {
        if (amount <= 0.0 || mana < amount) return false;
        mana -= amount;
        sync();
        return true;
    }

    @Override
    public void readData(ValueInput input) {
        level = Math.max(1, input.getInt("level").orElse(1));
        experience = Math.max(0, input.getInt("experience").orElse(0));
        experienceMax = Math.max(1, input.getInt("experienceMax").orElse(100));
        freePoints = Math.max(0, input.getInt("freePoints").orElse(0));
        systemPoints = Math.max(0, input.getInt("systemPoints").orElse(0));
        mana = Math.max(0.0, input.getDouble("mana").orElse(100.0));
        strength = Math.max(1, input.getInt("strength").orElse(10));
        agility = Math.max(1, input.getInt("agility").orElse(10));
        vitality = Math.max(1, input.getInt("vitality").orElse(10));
        intelligence = Math.max(1, input.getInt("intelligence").orElse(10));
        perception = Math.max(1, input.getInt("perception").orElse(10));
        mana = Math.min(mana, getMaxMana());
    }

    @Override
    public void writeData(ValueOutput output) {
        output.putInt("level", level);
        output.putInt("experience", experience);
        output.putInt("experienceMax", experienceMax);
        output.putInt("freePoints", freePoints);
        output.putInt("systemPoints", systemPoints);
        output.putDouble("mana", mana);
        output.putInt("strength", strength);
        output.putInt("agility", agility);
        output.putInt("vitality", vitality);
        output.putInt("intelligence", intelligence);
        output.putInt("perception", perception);
    }

    private void sync() {
        if (ModNetworking.IS_READY) {
            br.aetherworld.litrpg.component.ModComponents.LITRPG.sync(provider);
        }
    }
}
