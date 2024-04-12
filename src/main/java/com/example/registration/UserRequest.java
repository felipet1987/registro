package com.example.registration;

import lombok.Data;
import java.util.List;

@Data
public class UserRequest {

    private String name;
    private String email;
    private String password;
    private List<Phone> phones;


}
@Data
class Phone {
    private String number;
    private String citycode;
    private String contrycode;
}