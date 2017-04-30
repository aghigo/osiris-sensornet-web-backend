package br.uff.labtempo.osiris.repository;

import br.uff.labtempo.osiris.model.domain.UserAccount;
import org.springframework.data.repository.CrudRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Interface that abstracts UserAccount DAO implementations
 * @author andre.ghigo
 * @version 1.0
 * @since 1.8
 */
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {
}
