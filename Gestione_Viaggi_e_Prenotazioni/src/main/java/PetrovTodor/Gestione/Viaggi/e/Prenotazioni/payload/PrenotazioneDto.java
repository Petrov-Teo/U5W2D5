package PetrovTodor.Gestione.Viaggi.e.Prenotazioni.payload;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record PrenotazioneDto(
        @NotNull(message = "L'ID del viaggio è obbligatorio!")
        UUID idViaggio,
        @NotNull(message = "Il campo Data è obbligatorio!")
        LocalDate dataPrenotazione,
        String noteEoPreferenze) {
}
