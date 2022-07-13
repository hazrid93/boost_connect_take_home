package com.example.demo.service.impl;

import com.example.demo.jpa.UserRepository;
import com.example.demo.model.BaseModel;
import com.example.demo.model.Owner;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private static Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Override
    public List<Owner> getAllUsers() {
        return (List<Owner>) userRepository.findAll();
    }

    @Override
    public void saveAll(List<? extends BaseModel> data) {
        log.info("staring savingAll");

        userRepository.saveAll((List<Owner>) data);

        log.info("End savingAll ");
    }

    @Override
    public List<? extends BaseModel> getAll(Pageable pageable) {
        log.info("staring getAll");

        List<Owner> allOwners = userRepository.findAll(pageable);

        log.info("finishing getAll");
        return allOwners;
    }

    @Override
    public List<? extends BaseModel> getAll() {
        log.info("staring getAll");

        List<Owner> allOwners = (List<Owner>) userRepository.findAll();

        log.info("finishing getAll");
        return allOwners;
    }

    @Override
    public long getAllUserCount() {
        return userRepository.countAll();
    }

    @Override
    public void updateUser(Owner fetchedOwne) {
        userRepository.save(fetchedOwne);
    }

}
