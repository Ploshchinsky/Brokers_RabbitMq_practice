package ploton.brokers_rabbitmq_usersdb.services;

import ploton.brokers_rabbitmq_usersdb.entities.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity save(UserEntity entity);

    UserEntity findById(Long id);

    UserEntity findByUsername(String username);
    List<UserEntity> findAll();

    UserEntity addNewSub(Long id, String subUsername);

    Long deleteById(Long id);

}
