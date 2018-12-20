package it.titusthefox04.persistence.test;

import it.titusthefox04.auth.persistence.UserRepository;
import it.titusthefox04.auth.persistence.model.AuthenticationType;
import it.titusthefox04.auth.persistence.model.PasswordHistory;
import it.titusthefox04.auth.persistence.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author titusthefox04
 */
@RunWith(SpringRunner.class)
@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED, connection = EmbeddedDatabaseConnection.NONE)
@TestPropertySource(locations = "classpath:application-test-h2.properties")
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class UserRepositoryJpaTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void createUser() {
        User user = new User();
        user.setUsername("User" + System.currentTimeMillis());
        user.setSalt("salt");
        user.setHash("---------");
        user.setAuthType(AuthenticationType.DB);
        user.setFirstName("TestUser1");
        user.setLastName("TestUser2");
        user.setEmail("test@user.it");
        user.setPhone("000");
        user.setTimezone(null);
        user.setEnabled(true);
        user.setCreator(null);
        user.setModifier(null);
        user.setFirstLogin(false);
        user.setTotpSecret(null);
        user.setTotpRequired(false);
        user.setPasswordExpires(false);
        user.setHuman(true);
        user.setPasswordChangedOn(null);

        userRepository.save(user);
        //entityManager.persist(user);
        userRepository.count();
        userRepository.delete(user);
    }


    private void flush() {
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    public void passwordHistory() throws InterruptedException {
        User admin = getUser("admin");
        assertThat(admin.getPasswordHistory().size()).isEqualTo(1);
        admin.getPasswordHistory().remove(0);

        admin.getPasswordHistory().add(new PasswordHistory("AAAA", "dfwhifughssdifeuh"));
        Thread.sleep(1100);
        admin.getPasswordHistory().add(new PasswordHistory("BBBB", "dfwhifsaughdifeuh"));
        Thread.sleep(1100);
        admin.getPasswordHistory().add(new PasswordHistory("CCCC", "dfwhifusagshifeuh"));
        Thread.sleep(1100);
        flush();
        User admin2 = getUser("admin");
        assertThat(admin2.getPasswordHistory().size()).isEqualTo(3);
        admin2.getPasswordHistory().remove(0);
        flush();
        User admin3 = getUser("admin");
        assertThat(admin3.getPasswordHistory().size()).isEqualTo(2);
        assertThat(admin3.getPasswordHistory().get(0).getSalt()).isEqualTo("BBBB");
        assertThat(admin3.getPasswordHistory().get(1).getSalt()).isEqualTo("CCCC");
        admin3.getPasswordHistory().remove(0);
        admin3.getPasswordHistory().add(new PasswordHistory("DDDD", "fdjidoijf"));
        flush();
        User admin4 = getUser("admin");
        assertThat(admin4.getPasswordHistory().size()).isEqualTo(2);
        assertThat(admin4.getPasswordHistory().get(0).getSalt()).isEqualTo("CCCC");
        assertThat(admin4.getPasswordHistory().get(1).getSalt()).isEqualTo("DDDD");
        flush();
    }

    private User getUser(String userId) {
        Optional<User> _admin = userRepository.getByUsername(userId);
        assertThat(_admin.isPresent()).isTrue();
        return _admin.get();
    }

}
