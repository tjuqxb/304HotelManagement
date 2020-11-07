package com.cpsc304.HotelManagement.Model;

public class ReservationGuest extends Guest{
        String credit_card;
        String photo_identity;
        String email;
        String membership;

    public ReservationGuest(String name, String phone, Long id, String credit_card, String photoID, String email, String membership) {
        super(name, phone, id);
        this.credit_card = credit_card;
        this.photo_identity = photoID;
        this.email = email;
        this.membership = membership;
    }

    public ReservationGuest() {
    }

    public String getCredit_card() {
        return credit_card;
    }

    public void setCredit_card(String credit_card) {
        this.credit_card = credit_card;
    }

    public String getPhoto_identity() {
        return photo_identity;
    }

    public void setPhoto_identity(String photo_identity) {
        this.photo_identity = photo_identity;
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
