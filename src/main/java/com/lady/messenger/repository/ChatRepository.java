package com.lady.messenger.repository;

import com.lady.messenger.entity.Chat;
import com.lady.messenger.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends CrudRepository<Chat, Long> {
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Chat c JOIN c.users u WHERE u IN :users GROUP BY c HAVING COUNT(u) = :numUsers AND COUNT(DISTINCT u) = :numDistinctUsers")
    default boolean existsByUsers(@Param("users") List<User> users, @Param("numUsers") Long numUsers, @Param("numDistinctUsers") Long numDistinctUsers) {
        return findByUsers(users, numUsers, numDistinctUsers) != null;
    }

    @Query("SELECT c FROM Chat c JOIN c.users u WHERE u IN :users GROUP BY c HAVING COUNT(u) = :numUsers AND COUNT(DISTINCT u) = :numDistinctUsers")
    Chat findByUsers(@Param("users") List<User> users, @Param("numUsers") Long numUsers, @Param("numDistinctUsers") Long numDistinctUsers);
}




