package cn.chenxing.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Factory {


    private Integer id;
    private String name;
    private Integer number;

    public Factory (){
        this((Integer)null, (String)null, (Integer)null);
    }

    public Factory(Integer id,String name,Integer number) {
        this.id = new Integer(id);
        this.name = new String(name);
        this.number = new Integer(number);

    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }










}
