package PetrovTodor.Gestione.Viaggi.e.Prenotazioni.services;

import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.entities.Dipendente;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.entities.Prenotazione;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.entities.Viaggio;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.exceptions.BadRequestException;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.exceptions.NotFoundException;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.payload.PrenotazioneDto;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.repositories.DipendenteRepository;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.repositories.PrenotazioneRepository;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.repositories.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PrenotazioneService {
    @Autowired
    PrenotazioneRepository prenotazioneRepository;
    @Autowired
    ViaggioRepository viaggioRepository;
    @Autowired
    DipendenteRepository dipendenteRepository;


    public Prenotazione savePrenotazione(PrenotazioneDto body) {
        Viaggio viaggio = this.viaggioRepository.findById(body.idViaggio())
                .orElseThrow(() -> new NotFoundException(body.idViaggio()));

        Dipendente dipendente = this.dipendenteRepository.findById(body.idDipendente())
                .orElseThrow(() -> new NotFoundException(body.idDipendente()));
        viaggio.setDipendente(dipendente);
        if (dipendente == null) {
            throw new BadRequestException("Il viaggio non è associato a nessun dipendente.");
        }
        long conflitti = prenotazioneRepository.countConflictingPrenotazioni(
                dipendente.getIdDipendente(),
                body.dataPrenotazione()
        );

        if (conflitti > 0) {
            throw new BadRequestException("Il dipendente è già impegnato in questa data.");
        }
        Prenotazione prenotazione = new Prenotazione(body.dataPrenotazione(), body.noteEoPreferenze(), viaggio, dipendente);

        return this.prenotazioneRepository.save(prenotazione);
    }

    public Page<Prenotazione> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.prenotazioneRepository.findAll(pageable);
    }

    public Prenotazione findById(UUID idPrenotazione) {
        return this.
                prenotazioneRepository.
                findById(idPrenotazione).
                orElseThrow(()
                        -> new NotFoundException(idPrenotazione));
    }

    public Prenotazione findAndUpdite(UUID idPrenotazione, PrenotazioneDto body) {
        Viaggio viaggio = this.viaggioRepository.findById(body.idViaggio())
                .orElseThrow(() -> new NotFoundException(body.idViaggio()));

        Dipendente dipendente = this.dipendenteRepository.findById(body.idDipendente())
                .orElseThrow(() -> new NotFoundException(body.idDipendente()));
        viaggio.setDipendente(dipendente);
        if (dipendente == null) {
            throw new BadRequestException("Il viaggio non è associato a nessun dipendente.");
        }
        long conflitti = prenotazioneRepository.countConflictingPrenotazioni(
                dipendente.getIdDipendente(),
                body.dataPrenotazione()
        );
        if (conflitti > 0) {
            throw new BadRequestException("Il dipendente è già impegnato in questa data.");
        }
        Prenotazione prenotazione = findById(idPrenotazione);
        prenotazione.setDataPrenotazione(body.dataPrenotazione());
        prenotazione.setViaggio(viaggio);
        prenotazione.setDipendente(dipendente);
        prenotazione.setNoteEoPreferenze(body.noteEoPreferenze());
        return this.prenotazioneRepository.save(prenotazione);
    }

    public void findAndDelete(UUID idPrenotazione) {
        Prenotazione prenotazione = findById(idPrenotazione);
        this.prenotazioneRepository.delete(prenotazione);
    }
}
