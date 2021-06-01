package cn.chenxing.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.awt.font.NumericShaper;

public class Cybercafe implements Comparable<Cybercafe>{

    private  Integer factoryId;
    private  String factoryName;
    private  Integer id;
    private  String name;
    private  Integer terminalNums;
    private  Integer disklessNums;
    private  Integer Nums27;
    private  Integer Nums37;


    public Cybercafe (){
        this((Integer)null, (String)null, (Integer)null, (String)null, (Integer) null, (Integer)null, (Integer)null, (Integer)null);
    }

    public Cybercafe(Integer factoryId,String factoryName,Integer id,String name,Integer terminalNums,Integer disklessNums,Integer Nums27,Integer Nums37) {
        this.factoryId = new Integer(factoryId);
        this.factoryName = new String(factoryName);
        this.id = new Integer(id);
        this.name = new String(name);
        this.terminalNums = new Integer(terminalNums);
        this.disklessNums = new Integer(disklessNums);
        this.Nums27 = new Integer(Nums27);
        this.Nums37 = new Integer(Nums37);
    }


    public Integer getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(Integer factoryId) {
        this.factoryId = factoryId;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
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

    public Integer getTerminalNums() {
        return terminalNums;
    }

    public void setTerminalNums(Integer terminalNums) {
        this.terminalNums = terminalNums;
    }

    public Integer getDisklessNums() {
        return disklessNums;
    }

    public void setDisklessNums(Integer disklessNums) {
        this.disklessNums = disklessNums;
    }

    public Integer getNums27() {
        return Nums27;
    }

    public void setNums27(Integer nums27) {
        Nums27 = nums27;
    }

    public Integer getNums37() {
        return Nums37;
    }

    public void setNums37(Integer nums37) {
        Nums37 = nums37;
    }


    @Override
    public int compareTo(Cybercafe o) {
        if(this.disklessNums>o.disklessNums){
            return -1;
        }else if (this.disklessNums<o.disklessNums){
            return 1;
        }
        return 0;
    }
}
