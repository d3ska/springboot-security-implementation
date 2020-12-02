package pl.deska.springbootsecurityimplementation.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.deska.springbootsecurityimplementation.model.VerificationToken;

import java.util.Optional;


@Repository
public interface VerificationTokenRepo extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByValue(String token);
}
