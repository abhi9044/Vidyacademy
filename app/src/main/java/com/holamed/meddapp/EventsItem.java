package com.holamed.meddapp;

import com.holamed.meddapp.adapter.EventLab;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Era on 8/13/2015.
 */
public class EventsItem {
   private String eventName;
    private String eventAddress;
    private String eventDescription;
    private String eventDate;
    private String eventPrice;
    private String eventId;
    private String type;
    private EventLab lab;
    private List<EventTestgroup> testgroup=new ArrayList<>();

    public void setEventLab(EventLab lab)
    {
        this.lab=lab;
    }

    public EventLab getEventLab()
    {
        return lab;

    }
    public String getEventType()
    {
        return type;
    }
    public void setEventType(String type)
    {
     this.type=type;
    }

    public void setEventTestgroup(List<EventTestgroup> testgroup)
    {
        this.testgroup.addAll(testgroup);
    }

    public List<EventTestgroup> getEventTestgroup()
    {
        return testgroup;

    }

    public String getEventId() {
        return eventId;
    }


    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventPrice() {
        return eventPrice;
    }

    public void setEventPrice(String eventPrice) {
        this.eventPrice = eventPrice;
    }
}
