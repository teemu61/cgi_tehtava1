package com.example.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy="person", cascade = CascadeType.MERGE)
    private Set<Education> educations;

    private String sotu;
    private String language;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @ManyToMany
    private Set<Person> parentTo;

    @ManyToMany(mappedBy="parentTo")
    private Set<Person> parentFrom;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Education> getEducations() {
        return educations;
    }

    public void setEducations(Set<Education> educations) {
        this.educations = educations;
    }

    public String getSotu() {
        return sotu;
    }

    public void setSotu(String sotu) {
        this.sotu = sotu;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<Person> getParentTo() {
        return parentTo;
    }

    public void setParentTo(Set<Person> parentTo) {
        this.parentTo = parentTo;
    }

    public Set<Person> getParentFrom() {
        return parentFrom;
    }

    public void setParentFrom(Set<Person> parentFrom) {
        this.parentFrom = parentFrom;
    }

    public Person init() {
        parentTo = new HashSet<>();
        return this;
    }
}


