package com.sboard.repository;

import com.sboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//insert sql 메서드를 처리해줌
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByuid(String uid);
    boolean existsByNick(String nick);
    boolean existsByEmail(String email);
    boolean existsByHp(String hp);
}
