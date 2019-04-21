package com.btp_iitj.cnfmanag.Domain_Classes;

public class Conference {
    String name, date, venue, description;

    public Conference() {
    }

    public Conference(String name, String date, String venue, String description) {

        this.name = name;
        this.date = date;
        this.venue = venue;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getVenue() {
        return venue;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
