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

    public void setName(String name) {
        CheckTrue.ungueltigeZeichen(name);
        this.name = name;
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
