package com.testing.testing.with.crud.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "userData")
public class UserModel {

    @Id
    private String id;
    private String fullName;
    private String userName;
    private String mobileNumber;
    private String email;
    private String organization;
    private String address;

    public UserModel(String id, String fullName, String userName, String mobileNumber, String email, String organization, String address) {
        this.id = id;
        this.fullName = fullName;
        this.userName = userName;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.organization = organization;
        this.address = address;
    }
}
