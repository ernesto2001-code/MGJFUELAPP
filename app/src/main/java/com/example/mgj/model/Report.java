package com.example.mgj.model;

import com.google.firebase.Timestamp;

public class Report {
    String chargefromby, id, license_plate, liters_filled, operator_name, unidad, created_by, company, folio;
    Timestamp timestamp;

    public Report() {}

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Report(String chargefromby, String id, String license_plate, String liters_filled,
                  String operator_name, Timestamp timestamp, String unidad, String created_by, String company, String folio) {
        this.chargefromby = chargefromby;
        this.id = id;
        this.license_plate = license_plate;
        this.liters_filled = liters_filled;
        this.operator_name = operator_name;
        this.timestamp = timestamp;
        this.unidad = unidad;
        this.created_by = created_by;
        this.company = company;
        this.folio = folio;
    }

    public String getChargefromby() {
        return chargefromby;
    }

    public void setChargefromby(String chargefromby) {
        this.chargefromby = chargefromby;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }

    public String getLiters_filled() {
        return liters_filled;
    }

    public void setLiters_filled(String liters_filled) {
        this.liters_filled = liters_filled;
    }

    public String getOperator_name() {
        return operator_name;
    }

    public void setOperator_name(String operator_name) {
        this.operator_name = operator_name;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}