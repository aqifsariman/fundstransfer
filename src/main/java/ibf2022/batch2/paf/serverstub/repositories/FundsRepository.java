package ibf2022.batch2.paf.serverstub.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.batch2.paf.serverstub.models.Account;

@Repository
public class FundsRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String FIND_ACCOUNT_SQL = """
            select * from bankaccount where full_name = ?
            """;

    private final String UPDATE_ACCOUNT_SQL = """
            update bankaccount set balance = ? where full_name = ?
            """;

    public Account findAccountInfo(String fullName) {
        return jdbcTemplate.queryForObject(FIND_ACCOUNT_SQL, BeanPropertyRowMapper.newInstance(Account.class),
                fullName);
    }

    public Boolean transactionStatus(float balance, String fullName) {
        return (jdbcTemplate.update(UPDATE_ACCOUNT_SQL, balance, fullName) > 0 ? true : false);
    }

}
