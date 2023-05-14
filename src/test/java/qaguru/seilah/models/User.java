package qaguru.seilah.models;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class User {
    private String id;
    private String name;
    private String dob;
    private Address address;
    private String telephone;
    private ArrayList<String> pets;
    private String email;
}