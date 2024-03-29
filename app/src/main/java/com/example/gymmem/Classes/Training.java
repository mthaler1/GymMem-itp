package com.example.gymmem.Classes;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Training implements Serializable {
    private String name;
    @ServerTimestamp
    private Date dateStart, dateEnd;
    private List<TrainingSet> sets;
    TrainingType type;
    UUID id;

    public Training(String name, TrainingType type, Date dateStart) {
        this.setName(name);
        this.setType(type);
        this.setDateStart(dateStart);
        this.sets = new ArrayList<>();
        id = UUID.randomUUID();
    }

    public Training(String name, Date dateStart, Date dateEnd, List<TrainingSet> sets, TrainingType type, UUID id) {
        this.setName(name);
        this.setDateStart(dateStart);
        this.setDateEnd(dateEnd);
        this.setSets(sets);
        this.setType(type);
        this.setId(id);
    }
    //Für Startseite Display
    public Training(String name, Date dateStart) {
        this.name = name;
        this.dateStart = dateStart;
    }

    public void setName(String name) {
        CheckTrue.ungueltigeZeichen(name);
        if(name.length() >= 2) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Name muss mindestens 2 Zeichen enthalten");
        }

    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void setType(TrainingType type) {
        this.type = type;
    }

    public void addSet(TrainingSet set) {
        if(set != null) {
            this.sets.add(set);
        }
    }

    public void setSets(List<TrainingSet> sets) {
        this.sets = sets;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public List<TrainingSet> getSets() {
        return sets;
    }

    public TrainingType getType() {
        return type;
    }

    public UUID getId() {
        return id;
    }
}
