package br.com.costa.agenda.model;

import java.io.Serializable;

/**
 * Created by ErickJohnFidelisCost on 14/03/2017.
 */

public class Student implements Serializable{

    private Long id;
    private String name;
    private String address;
    private String email;
    private String number;
    private String site;
    private Double note;
    private String pathPhoto;

    public String getPathPhoto(){
        return pathPhoto;
    }

    public void setPathPhoto(String pathPhoto){
        this.pathPhoto = pathPhoto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Double getNote() {
        return note;
    }

    public void setNote(Double note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
