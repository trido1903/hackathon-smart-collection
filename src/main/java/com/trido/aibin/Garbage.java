package com.trido.aibin;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Garbage {
    private int id;
    private int recycle;
    private int landfill;
    private int compost;
    private int ewaste;
    private String description;
    private String status;

    public Garbage() {

    }

    public Garbage(int id, int recycle, int landfill, int compost, int ewaste, String description, String status) {
        this.id = id;
        this.recycle = recycle;
        this.landfill = landfill;
        this.compost = compost;
        this.ewaste = ewaste;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecycle() {
        return recycle;
    }

    public void setRecycle(int recycle) {
        this.recycle = recycle;
    }

    public int getLandfill() {
        return landfill;
    }

    public void setLandfill(int landfill) {
        this.landfill = landfill;
    }

    public int getCompost() {
        return compost;
    }

    public void setCompost(int compost) {
        this.compost = compost;
    }

    public int getEwaste() {
        return ewaste;
    }

    public void setEwaste(int ewaste) {
        this.ewaste = ewaste;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String isFull() {
        return status;
    }

    public void setFull(String full) {
        status = full;
    }

    @Override
    public String toString() {
        return "Garbage{" +
                "id=" + id +
                ", recycle=" + recycle +
                ", landfill=" + landfill +
                ", compost=" + compost +
                ", ewaste=" + ewaste +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}