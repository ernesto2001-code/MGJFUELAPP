package com.example.mgj.model;

public class    Tank {
    String name;
    int capacity, current_level;
    public Tank(){}

    public Tank(String name, int capacity, int current_level) {
        this.name = name;
        this.capacity = capacity;
        this.current_level = current_level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrent_level() {
        return current_level;
    }

    public void setCurrent_level(int current_level) {
        this.current_level = current_level;
    }
}
