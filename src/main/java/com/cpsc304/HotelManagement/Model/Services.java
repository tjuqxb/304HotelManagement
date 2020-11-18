package com.cpsc304.HotelManagement.Model;

public class Services {
    Integer service_id;
    String service_name;
    String description;
    Integer fee;

    public Services() {
    }

    public Services(Integer service_id, String service_name, String description, Integer fee) {
        this.service_id = service_id;
        this.service_name = service_name;
        this.description = description;
        this.fee = fee;
    }
    public int getServiceID() {
        return service_id;
    }

    public void setServiceID(Integer service_id) {
        this.service_id = service_id;
    }

    public String getServices() {
        return service_name;
    }

    public void setServices(String service_name) {
        this.service_name = service_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }
}
