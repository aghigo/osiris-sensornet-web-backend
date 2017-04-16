package br.uff.labtempo.osiris.service;

import br.uff.labtempo.osiris.mapper.UserAccountMapper;
import br.uff.labtempo.osiris.model.domain.UserAccount;
import br.uff.labtempo.osiris.model.response.UserAccountResponse;
import br.uff.labtempo.osiris.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
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
        UserAccount userAccount = this.userAccountRepository.getById(userId);
        UserAccountResponse userAccountResponse = UserAccountMapper.toResponse(userAccount);
        return userAccountResponse;
    }

    /**
     * Get a list of all available UserAccounts in the Application
     * @return List of UserAccount
     */
    public List<UserAccountResponse> getAll() {
        List<UserAccount> userAccountList = this.userAccountRepository.getAll();
        List<UserAccountResponse> userAccountResponseList = UserAccountMapper.toResponse(userAccountList);
        return userAccountResponseList;
    }

    /**
     * Creates a new UserAccount into the Application
     * @param userAccount
     * @return URI with the new UserAccount location
     */
    public URI create(UserAccount userAccount) {
        URI uri = this.userAccountRepository.create(userAccount);
        return uri;
    }

    /**
     * Updates an existing UserAccount from the Application
     * @param userId
     * @param userAccount
     */
    public void update(long userId, UserAccount userAccount) {
        this.userAccountRepository.update(userId, userAccount);
    }

    /**
     * Removes an existing UserAccount from the Application
     * @param userId
     */
    public void delete(long userId) {
        this.userAccountRepository.delete(userId);
    }
}
