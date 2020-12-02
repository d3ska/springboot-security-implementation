package pl.deska.springbootsecurityimplementation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.deska.springbootsecurityimplementation.model.AppUser;
import pl.deska.springbootsecurityimplementation.model.VerificationToken;
import pl.deska.springbootsecurityimplementation.repo.AppUserRepo;
import pl.deska.springbootsecurityimplementation.repo.VerificationTokenRepo;

import java.util.Optional;


@Service
public class Verifier {

    private AppUserRepo appUserRepo;
    private VerificationTokenRepo verificationTokenRepo;

    @Autowired
    public Verifier(AppUserRepo appUserRepo, VerificationTokenRepo verificationTokenRepo) {
        this.appUserRepo = appUserRepo;
        this.verificationTokenRepo = verificationTokenRepo;
    }


    public void verifyToken(String token) {
        AppUser appUser = verificationTokenRepo.findByValue(token).get().getAppUser();
        appUser.setEnabled(true);
        appUserRepo.save(appUser);
    }


    public void verifyAdminRole(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepo.findByValue(token);
        verificationToken.get().getAppUser().setConfirmedByAdmin(true);
        appUserRepo.save(verificationToken.get().getAppUser());
    }


}
