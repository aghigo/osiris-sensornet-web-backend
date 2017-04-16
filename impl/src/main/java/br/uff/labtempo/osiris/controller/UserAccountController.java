package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.osiris.model.request.UserAccountRequest;
import br.uff.labtempo.osiris.service.UserAccountService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller class to provide UserAccount management REST endpoints
 * @author andre.ghigo
 * @version 1.0
 * @since 1.8
 */
@RestController
@RequestMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserAccountController {

    private UserAccountService userAccountService;

    @Autowired
    public UserAccountController(UserAccountService userAcccountService) {
        this.userAccountService = userAcccountService;
    }

    /**
     * Get a list of All available UserAccounts
     * @see br.uff.labtempo.osiris.model.domain.UserAccount
     * @return List of UserAccount
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        return null;
    }

    /**
     * Create a new UserAccount on the application
     * @see br.uff.labtempo.osiris.model.domain.UserAccount
     * @return URI with new UserAccount location
     */
    @PostMapping
    public ResponseEntity<?> doPost(@RequestBody @Valid UserAccountRequest userAccountRequest) {
        return null;
    }

    /**
     * Get a specific UserAccount based on its Id
     * @param userId
     * @return UserAccount with {userId}
     */
    @GetMapping(value = "/{userId}")
    public ResponseEntity<?> doGetById(@PathVariable long userId) {
        return null;
    }

    /**
     * Updates a existing UserAccount from the application
     * @param userId
     * @param userAccountRequest
     * @return
     */
    @PutMapping(value = "/{userId}")
    public ResponseEntity<?> doPut(@PathVariable long userId, @RequestBody @Valid UserAccountRequest userAccountRequest) {
        return null;
    }

    /**
     * Removes an userAccount from the application
     * @param userId
     * @return
     */
    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<?> doDelete(@PathVariable long userId) {
        return null;
    }
}
