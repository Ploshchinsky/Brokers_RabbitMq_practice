package ploton.brokers_rabbitmq_usersdb.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ploton.brokers_rabbitmq_usersdb.entities.UserEntity;
import ploton.brokers_rabbitmq_usersdb.services.UserService;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody UserEntity entity) {
        return new ResponseEntity<>(userService.save(entity), HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> addNewSub(@PathVariable("id") Long id,
                                       @RequestBody Map<String, Object> object) {
        return new ResponseEntity<>(userService.addNewSub(id, (String) object.get("subUsername")), HttpStatus.ACCEPTED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable("username") String username) {
        return new ResponseEntity<>(userService.findByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.deleteById(id), HttpStatus.NO_CONTENT);
    }

}
