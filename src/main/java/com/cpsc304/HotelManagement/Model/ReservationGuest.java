package com.cpsc304.HotelManagement.Model;

public class ReservationGuest extends Guest{
        String creditCard;
        String photoID;
        String email;
        String membership;

    public ReservationGuest(String name, String phone, Long id, String creditCard, String photoID, String email, String membership) {
        super(name, phone, id);
        this.creditCard = creditCard;
        this.photoID = photoID;
        this.email = email;
        this.membership = membership;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getPhotoID() {
        return photoID;
    }

    public void setPhotoID(String photoID) {
        this.photoID = photoID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }
}
