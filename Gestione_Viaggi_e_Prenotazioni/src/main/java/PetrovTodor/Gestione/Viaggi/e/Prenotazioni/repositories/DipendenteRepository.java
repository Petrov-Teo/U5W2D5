package PetrovTodor.Gestione.Viaggi.e.Prenotazioni.repositories;

import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DipendenteRepository extends JpaRepository<Dipendente, UUID> {

    Optional<Dipendente>
    findByEmail(String email);

    boolean existsByEmail
            (String email);

}
