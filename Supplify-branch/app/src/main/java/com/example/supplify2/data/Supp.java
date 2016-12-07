package com.example.supplify2.data;

/**
 * Created by George on 12/6/2016.
 */

public class Supp {
    /** Member variables for a single supplement */
    private int _ID;
    private String name;
    private String description;
    private String dosage;
    private String effect;

    /** Default constructor */
    public Supp() {
        _ID = 0;
        name = "";
        description = "";
        dosage = "";
        effect = "";
    }

    /** Constructor */
    public Supp( int newID,
                 String newName,
                 String newDescription,
                 String newDosage,
                 String newEffect) {
        _ID = newID;
        name = newName;
        description = newDescription;
        dosage = newDosage;
        effect = newEffect;
    }

    /** Accesssor methods */
    public int get_ID() {
        return _ID;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getDosage() {
        return dosage;
    }
    public String getEffect() {
        return effect;
    }

    /** Mutator methods */
    public void set_ID(int newID) {
        _ID = newID;
    }
    public void setName(String newName) {
        name = newName;
    }
    public void setDescription(String newDescription) {
        description = newDescription;
    }
    public void setDosage(String newDosage) {
        dosage = newDosage;
    }
    public void setEffect(String newEffect) {
        effect = newEffect;
    }
}
