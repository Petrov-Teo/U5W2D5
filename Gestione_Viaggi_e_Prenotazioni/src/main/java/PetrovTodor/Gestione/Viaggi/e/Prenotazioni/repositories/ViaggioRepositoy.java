package PetrovTodor.Gestione.Viaggi.e.Prenotazioni.repositories;

import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.UUID;

public interface ViaggioRepositoy extends JpaRepository<Viaggio, UUID> {


    @Query("SELECT COUNT(v) FROM Viaggio v WHERE v.dipendenteId = :dipendenteId AND :data BETWEEN v.dataInizio AND v.dataFine")
    long countViaggiImpegnativi(@Param("dipendenteId") UUID dipendenteId, @Param("data") LocalDate data);

   
    default boolean isDipendenteLibero(UUID dipendenteId, LocalDate data) {
        return countViaggiImpegnativi(dipendenteId, data) == 0;
    }
}
