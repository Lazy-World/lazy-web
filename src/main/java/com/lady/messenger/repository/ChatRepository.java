package com.lady.messenger.repository;

import com.lady.messenger.entity.Chat;
import com.lady.messenger.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends CrudRepository<Chat, Long> {
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Chat c JOIN c.users u WHERE u IN :users GROUP BY c HAVING COUNT(u) = :numUsers")
    default boolean existsByUsers(@Param("users") List<User> users, @Param("numUsers") Long numUsers) {
        return findByUsers(users, numUsers) != null;
    }

    @Query("SELECT c FROM Chat c JOIN c.users u WHERE u IN :users GROUP BY c HAVING COUNT(u) = :numUsers")
    Chat findByUsers(@Param("users") List<User> users, @Param("numUsers") Long numUsers);
}



