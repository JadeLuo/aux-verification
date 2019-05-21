package org.bupt.www.service;

import lombok.extern.slf4j.Slf4j;
import org.bupt.www.pojo.po.User;
import org.bupt.www.repository.UserRepository;
import org.bupt.www.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User login(String phone, String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        try {
            password = MD5Util.generate(password);
        } catch (NoSuchAlgorithmException e) {
            log.error("action: login, username: {}, email: {}, password: {} 加密失败", phone, email, password);
            e.printStackTrace();
            return null;
        }
        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
            return userOptional.get();
        } else {
            return null;
        }
    }

    public User register(String phone, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            return null;
        }
        User user = new User();
        user.setPhone(phone);
        user.setEmail(email);
        try {
            password = MD5Util.generate(password);
        } catch (NoSuchAlgorithmException e) {
            log.error("action: register, username: {}, email: {}, password: {} 加密失败", phone, email, password);
            e.printStackTrace();
            return null;
        }
        user.setPassword(password);
        return userRepository.save(user);
    }

}
