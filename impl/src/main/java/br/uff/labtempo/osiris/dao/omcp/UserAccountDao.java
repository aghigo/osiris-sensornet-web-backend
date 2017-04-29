package br.uff.labtempo.osiris.dao.omcp;

import br.uff.labtempo.osiris.connection.ApplicationDatabaseConnection;
import br.uff.labtempo.osiris.model.domain.UserAccount;
import br.uff.labtempo.osiris.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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

    @Autowired
    private ApplicationDatabaseConnection applicationDatabaseConnection;

    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void setUp() {
        this.jdbcTemplate = applicationDatabaseConnection.getJdbcTemplate();
    }

    @Override
    public UserAccount getById(long userId) {
        try {
            String sql = "SELECT * FROM USER_ACCOUNT u WHERE u.id = ?";
            UserAccount userAccount = this.jdbcTemplate.queryForObject(sql, new Object[] {userId}, new BeanPropertyRowMapper<>(UserAccount.class));
            return userAccount;
        } catch (Exception e) {
            return null;
        }
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
