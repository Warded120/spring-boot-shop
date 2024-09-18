package com.ivan.usercrud.service.userData;

import com.ivan.usercrud.entity.UserData;
import com.ivan.usercrud.repo.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDataServiceImpl implements UserDataService {

    UserDataRepository userDataRepository;

    @Autowired
    public UserDataServiceImpl(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    @Override
    public UserData save(UserData userData) {
        return userDataRepository.save(userData);
    }
}
