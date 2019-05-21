package org.bupt.www.repository;

import org.bupt.www.pojo.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPhoneOrEmail(String phone, String email);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}
