package com.appfire.taskmanagement.service.impl;

import com.appfire.taskmanagement.exception.UserNotFoundException;
import com.appfire.taskmanagement.exception.UsernameNotFoundException;
import com.appfire.taskmanagement.dto.UserDTO;
import com.appfire.taskmanagement.model.User;
import com.appfire.taskmanagement.repository.UserRepository;
import com.appfire.taskmanagement.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public String save(UserDTO userRequest) {
        User user = new User();
        user.setSalt(createSalt());
        user.setPassword(createPasswordHash(userRequest.password(), user.getSalt()));

        user.setUsername(userRequest.username());
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());

        if(isUsernameValid(userRequest.username())) {
            return repository.save(user).getId();
        }
        return "";
    }

    @Override
    public User save(User entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(User entity) {
        entity.setDeleted(true);
        updateById(entity.getId(),entity);
    }

    @Override
    public User findById(String id) {
        return repository.findById(id).orElseThrow(UserNotFoundException::new);
    }


    //<3
    //ima go v bazata
    //
    @Override
    public boolean isUsernameValid(String username) {
        return (!repository.existsByUsername(username)) && username.length()>3;
    }
    @Override
    public boolean checkPassword(User user, String password) {
        return user
                .getPassword()
                .equals(createPasswordHash(password, user.getSalt()));
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(UsernameNotFoundException::new);
    }

    @Override
    public void updateById(String id, User changedUser) {
        repository.update(id,changedUser.getUsername(),changedUser.getFirstName(),changedUser.getLastName());
    }


    private byte[] createSalt() {
        var random = new SecureRandom();
        var salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private String createPasswordHash(String password, byte[] salt) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(salt);
        return Arrays.toString(md.digest(
                password.getBytes(StandardCharsets.UTF_8))
        );
    }
}
