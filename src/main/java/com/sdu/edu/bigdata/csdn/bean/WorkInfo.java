package com.sdu.edu.bigdata.csdn.bean;

/**
 * WorkInfo
 *
 * @author Allen Jin
 * @date 2017/05/20
 */
public class WorkInfo {
    private String name;
    private int total;
    private int escape;
    private int finished;

    public WorkInfo() {
    }

    public WorkInfo(String name, int total, int escape, int finished) {
        this.name = name;
        this.total = total;
        this.escape = escape;
        this.finished = finished;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getEscape() {
        return escape;
    }

    public void setEscape(int escape) {
        this.escape = escape;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "WorkInfo{" +
                "name='" + name + '\'' +
                ", total=" + total +
                ", escape=" + escape +
                ", finished=" + finished +
                '}';
    }
}
