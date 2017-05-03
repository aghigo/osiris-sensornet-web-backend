package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.model.request.UserAccountRequest;
import br.uff.labtempo.osiris.model.response.UserAccountResponse;
import br.uff.labtempo.osiris.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class to provide UserAccount management REST endpoints
 * @see UserAccountRequest (UserAccount data provided by the client on POST/PUT requests)
 * @see br.uff.labtempo.osiris.model.domain.UserAccount (UserAccount application domain)
 * @see br.uff.labtempo.osiris.model.response.UserAccountResponse (UserAccount response data sent by the API to the Client)
 * @author andre.ghigo
 * @version 1.0
 * @since 1.8
 */
@RestController
@CrossOrigin
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
        try {
            List<UserAccountResponse> userAccountResponseList = this.userAccountService.getAll();
            return ResponseEntity.ok(userAccountResponseList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Create a new UserAccount on the application
     * @see br.uff.labtempo.osiris.model.domain.UserAccount
     * @return URI with new UserAccount location
     */
    @PostMapping
    public ResponseEntity<?> doPost(@RequestBody @Valid UserAccountRequest userAccountRequest) {
        try {
            return ResponseEntity.ok(this.userAccountService.create(userAccountRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Get a specific UserAccount based on its Id
     * @param userId
     * @return UserAccount with {userId}
     */
    @GetMapping(value = "/{userId}")
    public ResponseEntity<?> doGetById(@PathVariable long userId) {
        try {
            return ResponseEntity.ok(this.userAccountService.getById(userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Updates a existing UserAccount from the application
     * @param userId
     * @param userAccountRequest
     * @return
     */
    @PutMapping(value = "/{userId}")
    public ResponseEntity<?> doPut(@PathVariable long userId, @RequestBody @Valid UserAccountRequest userAccountRequest) {
        try {
            this.userAccountService.update(userId, userAccountRequest);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Removes an userAccount from the application
     * @param userId
     * @return
     */
    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<?> doDelete(@PathVariable long userId) {
        try {
            this.userAccountService.delete(userId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
