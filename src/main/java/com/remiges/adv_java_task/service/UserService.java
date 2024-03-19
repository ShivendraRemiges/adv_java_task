package com.remiges.adv_java_task.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.remiges.adv_java_task.jwt_detail.*;

@Service
public class UserService {

    private UserService(){
        store.add(new User(UUID.randomUUID().toString(),"Shivendra kumar","shivendra.kumar@remiges.tech"));
        store.add(new User(UUID.randomUUID().toString(),"Deepak kumar","deepak.kumar@remiges.tech"));
        store.add(new User(UUID.randomUUID().toString(),"Rajesh Chaudhary","rajesh.chodhary@remiges.tech"));

    }

    private List<User> store = new ArrayList<>() ;

    public List<User> getUsers(){
        return store;
    }
}
