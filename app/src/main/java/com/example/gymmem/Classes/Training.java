package com.example.gymmem.Classes;

import java.util.Date;
import java.util.UUID;

public class Training {
    private String name;
    private Date dateStart, dateEnd;
    TrainingType type;
    UUID id;

    public void setName(String name) {
        this.name = name;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

}
