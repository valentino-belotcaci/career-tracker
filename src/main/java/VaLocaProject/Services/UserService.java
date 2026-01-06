package VaLocaProject.Services;


import org.springframework.beans.factory.annotation.Autowired;

import VaLocaProject.Repositories.UserRepository;

import org.springframework.stereotype.Service;


@Service
public class UserService{

    @Autowired
    UserRepository userRepository;


    public String getUsername(){
        userRepository.fi
    }
}