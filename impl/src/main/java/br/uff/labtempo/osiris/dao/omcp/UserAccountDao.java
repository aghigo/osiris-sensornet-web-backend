package br.uff.labtempo.osiris.dao.omcp;

import br.uff.labtempo.osiris.model.domain.UserAccount;
import br.uff.labtempo.osiris.repository.UserAccountRepository;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

/**
 * DAO class for UserAccount CRUD
 * @see br.uff.labtempo.osiris.model.domain.UserAccount
 * @author andre.ghigo
 * @version 1.0
 * @since 1.8
 */
@Component("userAccountDao")
public class UserAccountDao implements UserAccountRepository {
    @Override
    public UserAccount getById(long userId) {
        return null;
    }

    @Override
    public List<UserAccount> getAll() {
        return null;
    }

    @Override
    public URI create(UserAccount userAccount) {
        return null;
    }

    @Override
    public void update(long userId, UserAccount userAccount) {

    }

    @Override
    public void delete(long userId) {

    }
}
