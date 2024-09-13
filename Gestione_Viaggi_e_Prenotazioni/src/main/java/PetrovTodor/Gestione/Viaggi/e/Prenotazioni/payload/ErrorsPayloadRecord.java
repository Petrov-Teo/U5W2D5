package PetrovTodor.Gestione.Viaggi.e.Prenotazioni.payload;

import java.time.LocalDateTime;

public record ErrorsPayloadRecord(String message, LocalDateTime timeStamp) {
}
