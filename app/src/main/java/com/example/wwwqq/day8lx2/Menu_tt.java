package com.example.wwwqq.day8lx2;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "Menu_tt")

public class Menu_tt
{
    @Column(name = "id" ,isId = true,autoGen = true)
    private int id;
    @Column(name = "name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
