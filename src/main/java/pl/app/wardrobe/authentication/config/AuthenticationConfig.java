package pl.app.wardrobe.authentication.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@ApplicationScoped
@FormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/authentication/form/login.xhtml",
                errorPage = "/authentication/form/login_error.xhtml"
        )
)
//@BasicAuthenticationMechanismDefinition(realmName = "wardrobe-app")
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/Wardrobe",
        callerQuery = "select password from users where login = ?",
        groupsQuery = "select role from users__roles where id = (select id from users where login = ?)",
        hashAlgorithm = Pbkdf2PasswordHash.class
)
public class AuthenticationConfig {

}
