package PetrovTodor.Gestione.Viaggi.e.Prenotazioni.repositories;

import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {
}
