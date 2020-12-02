package pl.deska.springbootsecurityimplementation.service;


import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.deska.springbootsecurityimplementation.model.AppUser;
import pl.deska.springbootsecurityimplementation.model.Role;
import pl.deska.springbootsecurityimplementation.model.VerificationToken;
import pl.deska.springbootsecurityimplementation.repo.AppUserRepo;
import pl.deska.springbootsecurityimplementation.repo.VerificationTokenRepo;


import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class UserService {

    private static final String MAJOR_ADMIN_EMAIL = "admin00@gmail.com";

    private AppUserRepo appUserRepo;
    private PasswordEncoder passwordEncoder;
    private MailSenderService mailSenderService;
    private VerificationTokenRepo verificationTokenRepo;

    @Autowired
    public UserService(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder, MailSenderService mailSenderService, VerificationTokenRepo verificationTokenRepo) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.mailSenderService = mailSenderService;
        this.verificationTokenRepo = verificationTokenRepo;
    }

    public boolean addUser(AppUser appUser, HttpServletRequest request) throws MessagingException {
        boolean result = false;
        if(userDidntExist(appUser) && userHasValidEmail(appUser.getUsername())) {
            saveUser(appUser);
            VerificationToken verificationToken = createVerificationToken(appUser);
            verificationTokenRepo.save(verificationToken);
            if (appUser.getRole() == Role.ADMIN) {
                sendConfirmationEmail(MAJOR_ADMIN_EMAIL, request,"Confirm Admin role for " + appUser.getUsername(), verificationToken,"/add-authority?token=");
            }
            sendConfirmationEmail(appUser.getUsername(), request,"Verification account", verificationToken, "/verify-token?token=");
            result = true;
        }
        return result;
    }

    private boolean userDidntExist(AppUser appUser) {
       return appUserRepo.findByUsername(appUser.getUsername()).isEmpty();
    }

    private boolean userHasValidEmail(String email) {
      return EmailValidator.getInstance().isValid(email);
    }

    private void saveUser(AppUser appUser) {
        encodeUserPassword(appUser);
        appUserRepo.save(appUser);
    }

    private void encodeUserPassword(AppUser appUser) {
        String password = appUser.getPassword();
        appUser.setPassword(passwordEncoder.encode(password));
    }

    private VerificationToken createVerificationToken(AppUser appUser) {
        String token = UUID.randomUUID().toString();
        return  new VerificationToken(token, appUser);
    }


    private void sendConfirmationEmail(String email, HttpServletRequest request, String title, VerificationToken verificationToken, String endpoint) throws MessagingException {
        String confirmationUrl = createConfirmationUrl(request, verificationToken.getValue(), endpoint);
        mailSenderService.sendEmail(email,
                title,
                confirmationUrl,
                false);
    }


    private String createConfirmationUrl(HttpServletRequest request, String token, String endPoint) {
        String url = "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath() +
                endPoint +
                token;
        return url;
    }




}
