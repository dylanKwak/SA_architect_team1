package com.sds.swa1.sdk.repository;

import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity(name="ProcessedEvent")
public class ProcessedEvent implements Serializable, Persistable<String> {
    @Id
    @Column(name="eventId")
    private String eventId;

    public ProcessedEvent() {}
    public ProcessedEvent(final String eventId) {
        this.eventId = eventId;
    }

    @Transient
    @Override
    public String getId() {
        return eventId;
    }

    @Transient
    @Override
    public boolean isNew() {return true;}
}
