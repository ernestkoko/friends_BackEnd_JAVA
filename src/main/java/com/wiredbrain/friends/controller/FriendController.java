package com.wiredbrain.friends.controller;

import com.wiredbrain.friends.model.Friend;
import com.wiredbrain.friends.service.FriendService;
import com.wiredbrain.friends.util.ErrorMessage;
import com.wiredbrain.friends.util.FieldErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class FriendController {

    @Autowired
    FriendService friendService;

    @PostMapping("/friend") // adding @Valid ensures the friend is valid
    Friend create(@Valid  @RequestBody Friend friend){
        return friendService.save(friend);
    }

    // this handle fields error messages
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    List<FieldErrorMessage> exceptionHandler(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        List<FieldErrorMessage> fieldErrorMessages = fieldErrors.stream()
                .map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        return fieldErrorMessages;
    }




    @GetMapping("/friend")
    Iterable<Friend> read(){
        return friendService.findAll();
    }
    //ResponseEntity enables you to handle errors
    @PutMapping("/friend")
    ResponseEntity<Friend> update(@RequestBody Friend friend){ //changed the return type to handle any error
        if (friendService.findById(friend.getId()).isPresent())
            return  new ResponseEntity(friend, HttpStatus.BAD_REQUEST);
         else
        return  new ResponseEntity(friendService.save(friend), HttpStatus.OK);
         //if the friend does exists it sends bad request and does not change the existing friend
    }

    @DeleteMapping("/friend/{id}")
    void delete(@PathVariable Integer id){
        friendService.deleteById(id);
    }
    @GetMapping("/friend/{id}")
    Optional<Friend> findById(@PathVariable Integer id){
        return friendService.findById(id);
    }

    @GetMapping("/friend/search")
    Iterable<Friend> findByQuery(
            @RequestParam(value = "first", required = false) String firstName,
            @RequestParam(value = "last", required = false) String lastName)
    {
        if (firstName != null && lastName != null) {
            return friendService.findByFirstNameAndLastName(firstName, lastName);
        }else if (firstName != null){
            return friendService.findByFirstName(firstName);
        }else if (lastName != null){
            return friendService.findByLastName(lastName);
        }
        return friendService.findAll();
    }

//    @GetMapping("/friend/search")
//    Iterable<Friend> findByFirstName(@RequestParam("firstName") String firstName){
//        return friendService.findByFirstName(firstName);
//    }

}
