package ploton.brokers_rabbitmq_usersdb.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ploton.brokers_rabbitmq_usersdb.entities.UserEntity;
import ploton.brokers_rabbitmq_usersdb.repositories.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserEntity save(UserEntity entity) {
        return userRepository.save(entity);
    }

    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("User ID - " + id));
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new NoSuchElementException("User username - " + username));

    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity addNewSub(Long id, String subUsername) {
        UserEntity entity = findById(id);
        List<String> tempSubs = entity.getSubs();
        tempSubs.add(subUsername);
        entity.setSubs(tempSubs);
        return userRepository.save(entity);
    }

    @Override
    public Long deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return id;
        }
        return -1L;
    }
}
