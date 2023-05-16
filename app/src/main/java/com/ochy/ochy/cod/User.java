package com.ochy.ochy.cod;


import java.util.HashMap;
import java.util.Map;

public class User {
    public String email, password,  surn, name, patronomyc, tel;

    public User() {
    }

    public User(String email, String password, String surn, String name, String patronomyc, String tel) {
        this.email = email;
        this.password = password;
        this.surn = surn;
        this.name = name;
        this.patronomyc = patronomyc;
        this.tel = tel;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("password", password);
        result.put("surn", surn);
        result.put("name", name);
        result.put("patronomyc", patronomyc);
        result.put("tel", tel);
        return result;
    }
}
