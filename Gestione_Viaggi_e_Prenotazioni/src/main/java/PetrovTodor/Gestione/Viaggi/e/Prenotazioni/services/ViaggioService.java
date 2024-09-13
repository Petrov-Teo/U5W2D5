package PetrovTodor.Gestione.Viaggi.e.Prenotazioni.services;

import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.entities.Dipendente;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.entities.Viaggio;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.exceptions.BadRequestException;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.exceptions.NotFoundException;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.payload.ViaggioDto;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.repositories.DipendenteRepository;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.repositories.ViaggioRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ViaggioService {
    @Autowired
    ViaggioRepositoy viaggioRepositoy;

    @Autowired
    DipendenteRepository dipendenteRepository;


    public Viaggio saveViaggio(ViaggioDto body) {
        UUID dipendenteId = body.idDipendente();

        if (this.viaggioRepositoy.countViaggiImpegnativi(dipendenteId, body.data()) > 0) {
            throw new BadRequestException("Il dipendente non è libero per questo viaggio, perché già impegnato in un altro viaggio!");
        }
        Dipendente dipendente = this.dipendenteRepository.findById(dipendenteId)
                .orElseThrow(() -> new NotFoundException(dipendenteId));


        return new Viaggio(body.destinazione(), body.data(), dipendente);
    }

    public Page<Viaggio> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.viaggioRepositoy.findAll(pageable);
    }

    public Viaggio findById(UUID idViaggio) {
        return this.viaggioRepositoy.findById(idViaggio).orElseThrow(() -> new NotFoundException(idViaggio));
    }

    public void findAndDelete(UUID idViaggio) {
        Viaggio found = this.findById(idViaggio);
        this.viaggioRepositoy.delete(found);
    }

    public Viaggio findAndUpdate(UUID idViaggio, ViaggioDto body) throws BadRequestException {
        UUID dipendenteId = body.idDipendente();

        if (this.viaggioRepositoy.countViaggiImpegnativi(dipendenteId, body.data()) > 0) {
            throw new BadRequestException("Il dipendente non è libero per questo viaggio, perché già impegnato in un altro viaggio!");
        }
        Dipendente dipendente = this.dipendenteRepository.findById(dipendenteId)
                .orElseThrow(() -> new NotFoundException(dipendenteId));
        Viaggio found = this.findById(idViaggio);
        found.setData(body.data());
        found.setDipendente(dipendente);
        found.setDestinazione(body.destinazione());
        return this.viaggioRepositoy.save(found);
    }
}

