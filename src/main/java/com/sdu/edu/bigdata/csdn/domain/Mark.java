package com.sdu.edu.bigdata.csdn.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mark
 *
 * @author Allen Jin
 * @date 2017/05/19
 */
@Entity
@Table(name = "marks")
public class Mark {
    @Id
    @GeneratedValue
    private Long id;

    private String user;
    private int sn;
    private String key1;
    private String key2;
    private String key3;

    public Mark(String user, int sn, String key1, String key2, String key3) {
        this.user = user;
        this.sn = sn;
        this.key1 = key1;
        this.key2 = key2;
        this.key3 = key3;
    }

    public Mark() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    public String getKey3() {
        return key3;
    }

    public void setKey3(String key3) {
        this.key3 = key3;
    }

    @Override
    public String toString() {
        return "Mark{" +
                "user='" + user + '\'' +
                ", sn=" + sn +
                ", key1='" + key1 + '\'' +
                ", key2='" + key2 + '\'' +
                ", key3='" + key3 + '\'' +
                '}';
    }
}
