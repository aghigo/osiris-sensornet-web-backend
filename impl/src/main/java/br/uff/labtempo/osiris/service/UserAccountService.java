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

    public UserAccountResponse getById(long userId) {
        UserAccount userAccount = this.userAccountRepository.getById(userId);
        UserAccountResponse userAccountResponse = UserAccountMapper.toResponse(userAccount);
        return userAccountResponse;
    }

    public List<UserAccountResponse> getAll() {
        List<UserAccount> userAccountList = this.userAccountRepository.getAll();
        List<UserAccountResponse> userAccountResponseList = UserAccountMapper.toResponse(userAccountList);
        return userAccountResponseList;
    }

    public URI create(UserAccount userAccount) {
        URI uri = this.userAccountRepository.create(userAccount);
        return uri;
    }

    public void update(long userId, UserAccount userAccount) {
        this.userAccountRepository.update(userId, userAccount);
    }

    public void delete(long userId) {
        this.userAccountRepository.delete(userId);
    }
}
