package lk.mazarin.demo.hexagonal.persistence.respository;

import io.vavr.API;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lk.mazarin.demo.hexagonal.persistence.entity.UserDBEntity;
import lk.mazarin.demo.hexagonal.registration.domain.user.service.spi.UserRepo;
import lk.mazarin.demo.hexagonal.registration.domain.user.types.*;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;
import static io.vavr.control.Option.none;
import static io.vavr.control.Option.of;

@Repository
public class UserDBRepository implements UserRepo {
    private JdbcTemplate jdbcTemplate;

    public UserDBRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Either<List<Failure>, PendingUserId> addUser(PendingUser user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        var result =
                Try
                        .ofSupplier(() -> of(
                                jdbcTemplate.update(con -> {
                                    PreparedStatement ps = con.prepareStatement("INSERT INTO verification(email,password,code) VALUES(?,?,?)");
                                    ps.setString(1, user.getEmail().getRawEmail());
                                    ps.setString(2, user.getPassword().getRawPassword());
                                    ps.setString(3, user.getVerificationCode().getCode());
                                    return ps;
                                }, keyHolder) > 0 ? user.getVerificationCode() : new Failure(Failure.Type.RegistrationErrorEmailTaken, "")))
                        .recover(throwable ->
                                API.Match(throwable).of(
                                        Case($(instanceOf(DataAccessException.class)), Option.of(new Failure(Failure.Type.RegistrationErrorEmailTaken, throwable.getMessage()))),
                                        Case($(instanceOf(DataIntegrityViolationException.class)), Option.of(new Failure(Failure.Type.RegistrationErrorEmailTaken, throwable.getMessage()))),
                                        Case($(instanceOf(BadSqlGrammarException.class)), Option.of(new Failure(Failure.Type.PersistenceError, throwable.getMessage()))),
                                        Case($(), Option.of(new Failure(Failure.Type.PersistenceError, throwable.getMessage())))
                                )
                        ).getOrElse(none());
        return result.isEmpty() ?
                Either.left(List.of((Failure) result.get())) : Either.right(new PendingUserId(keyHolder.getKey()));
//        return result.isEmpty() ?
//                Either.left(List.of((Failure) result.get())) : Either.right(user);
    }

    @Override
    public Either<Failure, ActiveUser> setEmailAsVerified(VerificationCode code) {
        return null;
    }

    @Override
    public Option<User> findUser(UserId userId) {
        return findUserById(userId)
                .map(UserDBEntity::toDomainModel);
    }

    private Option<UserDBEntity> findUserById(UserId userId) {
        return Try
                .ofSupplier(() -> of(jdbcTemplate.queryForObject("SELECT u.id as userId, u.email, u.password, u.state as userState FROM app_user u WHERE u.id=?", new BeanPropertyRowMapper<>(UserDBEntity.class), userId.getId())))
                .getOrElse(none());
    }

    @Override
    public Option<User> findPendingUser(PendingUserId id){
        return findPendingUserById(id)
                .map(UserDBEntity::toDomainModel);
    }

    private Option<UserDBEntity> findPendingUserById(PendingUserId id){

        return Try
                .ofSupplier(() -> of(jdbcTemplate.queryForObject("SELECT id as userId,email ,password,code FROM verification WHERE id=?", new BeanPropertyRowMapper<>(UserDBEntity.class),id.getId())))
                .getOrElse(none());
    }
}
