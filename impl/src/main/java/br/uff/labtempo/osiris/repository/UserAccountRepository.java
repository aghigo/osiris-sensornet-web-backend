package br.uff.labtempo.osiris.repository;

import br.uff.labtempo.osiris.model.domain.UserAccount;

import java.net.URI;
import java.util.List;

/**
 * Interface that abstracts UserAccount DAO implementations
 * @author andre.ghigo
 * @version 1.0
 * @since 1.8
 */
public interface UserAccountRepository {
    UserAccount getById(long userId);
    List<UserAccount> getAll();
    URI create(UserAccount userAccount);
    void update(long userId, UserAccount userAccount);
    void delete(long userId);
}
