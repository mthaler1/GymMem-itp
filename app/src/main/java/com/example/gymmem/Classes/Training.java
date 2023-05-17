package com.example.gymmem.Classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Training {
    private String name;
    private Date dateStart, dateEnd;
    private List<TrainingSet> sets;
    TrainingType type;
    UUID id;

    public Training(String name, TrainingType type, Date dateStart) {
        this.setName(name);
        this.setType(type);
        this.setDateStart(dateStart);
        this.sets = new ArrayList<>();
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
}
