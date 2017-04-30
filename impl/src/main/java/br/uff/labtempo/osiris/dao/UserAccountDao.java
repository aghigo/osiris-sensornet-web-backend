package br.uff.labtempo.osiris.dao;

import br.uff.labtempo.osiris.model.domain.UserAccount;
import org.springframework.data.repository.CrudRepository;

/**
 * DAO class for UserAccount CRUD
 * @see br.uff.labtempo.osiris.model.domain.UserAccount
 * @author andre.ghigo
 * @version 1.0
 * @since 1.8
 */
public interface UserAccountDao extends CrudRepository<UserAccount, Long> {

}
