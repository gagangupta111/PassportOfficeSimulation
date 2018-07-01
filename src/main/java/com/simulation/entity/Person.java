package com.simulation.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int queueStatus;
    private String verificationStatus;
    private String initialAddition;
    private String queueAddition;
    private Long totalTime;

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

    public int getQueueStatus() {
        return queueStatus;
    }

    public void setQueueStatus(int queueStatus) {
        this.queueStatus = queueStatus;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getInitialAddition() {
        return initialAddition;
    }

    public void setInitialAddition(String initialAddition) {
        this.initialAddition = initialAddition;
    }

    public String getQueueAddition() {
        return queueAddition;
    }

    public void setQueueAddition(String queueAddition) {
        this.queueAddition = queueAddition;
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(name, person.name) &&
                Objects.equals(initialAddition, person.initialAddition);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, initialAddition);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", queueStatus=" + queueStatus +
                ", verificationStatus='" + verificationStatus + '\'' +
                ", initialAddition=" + initialAddition +
                ", queueAddition=" + queueAddition +
                '}';
    }

    public static String getCurrentTime(){

        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(dt);

    }

    public static Date getCurrentTime(String source){

        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getCause());
        }

    }

}
