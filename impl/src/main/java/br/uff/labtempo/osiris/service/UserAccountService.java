package br.uff.labtempo.osiris.service;

import br.uff.labtempo.osiris.mapper.UserAccountMapper;
import br.uff.labtempo.osiris.model.domain.UserAccount;
import br.uff.labtempo.osiris.model.request.UserAccountRequest;
import br.uff.labtempo.osiris.model.response.UserAccountResponse;
import br.uff.labtempo.osiris.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Service class with business rules to manage UserAccounts
 * @see UserAccount
 * @see br.uff.labtempo.osiris.model.request.UserAccountRequest
 * @see UserAccountResponse
 * @author andre.ghigo
 * @version 1.0
 */
@Service
public class UserAccountService {
    private UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    /**
     * Get a specific UserAccount by its unique Id
     * @param userId
     * @return UserAccountResponse
     */
    public UserAccountResponse getById(long userId) {
        UserAccount userAccount = this.userAccountRepository.findOne(userId);
        UserAccountResponse userAccountResponse = UserAccountMapper.toResponse(userAccount);
        return userAccountResponse;
    }

    /**
     * Get a list of all available UserAccounts in the Application
     * @return List of UserAccount
     */
    public List<UserAccountResponse> getAll() {
        List<UserAccount> userAccountList = (List<UserAccount>) this.userAccountRepository.findAll();
        List<UserAccountResponse> userAccountResponseList = UserAccountMapper.toResponse(userAccountList);
        return userAccountResponseList;
    }

    /**
     * Creates a new UserAccount into the Application
     * @param userAccountRequest
     * @return URI with the new UserAccount location
     */
    public URI create(UserAccountRequest userAccountRequest) throws URISyntaxException {
        UserAccount userAccount = UserAccountMapper.toUserAccount(userAccountRequest);
        userAccount.setEnabled(true);
        UserAccount u = this.userAccountRepository.save(userAccount);
        return new URI("/users/" + u.getId());
    }

    /**
     * Updates an existing UserAccount from the Application
     * @param userId
     * @param userAccountRequest
     */
    public void update(long userId, UserAccountRequest userAccountRequest) {
        UserAccount userAccount = UserAccountMapper.toUserAccount(userAccountRequest);
        this.userAccountRepository.save(userAccount);
    }

    /**
     * Removes an existing UserAccount from the Application
     * @param userId
     */
    public void delete(long userId) {
        this.userAccountRepository.delete(userId);
    }
}
