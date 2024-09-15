package PetrovTodor.Gestione.Viaggi.e.Prenotazioni.repositories;

import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {

    @Query("SELECT COUNT(p) FROM Prenotazione p WHERE p.viaggio.dipendente.idDipendente = :dipendenteId AND p.dataPrenotazione = :dataPrenotazione")
    long countConflictingPrenotazioni(@Param("dipendenteId") UUID dipendenteId, @Param("dataPrenotazione") LocalDate dataPrenotazione);
}
