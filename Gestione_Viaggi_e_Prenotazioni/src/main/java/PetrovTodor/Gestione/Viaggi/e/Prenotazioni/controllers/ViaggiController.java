package PetrovTodor.Gestione.Viaggi.e.Prenotazioni.controllers;

import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.entities.Viaggio;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.exceptions.BadRequestException;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.payload.ViaggioDto;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.payload.responsPayload.ViaggioResponseDto;
import PetrovTodor.Gestione.Viaggi.e.Prenotazioni.services.ViaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

/*
1. GET http://localhost:3001/viggi
2. POST http://localhost:3001/viggi (+ body)
3. GET  http://localhost:3001/viggi/{autoreId}
4. PUT http://localhost:3001/viggi/{autoreId}
5. DELETE http://localhost:3001/viggi/{autoreId}

*/
@RestController
@RequestMapping("/viaggi")
public class ViaggiController {

    @Autowired
    ViaggioService viaggioService;

    //1. GET http://localhost:3001/viggi
    @GetMapping
    public Page<Viaggio> findAll(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "idViaggio") String sortBy) {
        return viaggioService.findAll(page, size, sortBy);
    }

    //2. POST http://localhost:3001/viaggi (+ body)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ViaggioResponseDto saveViaggio(@RequestBody @Validated ViaggioDto body, BindingResult validationResult) throws org.apache.coyote.BadRequestException {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Controlla i seguenti errori: " + messages);
        } else {
            return new ViaggioResponseDto(this.viaggioService.saveViaggio(body).getIdViaggio());
        }
    }

    //3. GET  http://localhost:3001/viaggi/{autoreId}
    @GetMapping("/dipendenti/{idViaggio}")
    public Viaggio findById(@PathVariable UUID idViaggio) {
        return viaggioService.findById(idViaggio);
    }

    //4. PUT http://localhost:3001/viaggi/{autoreId}
    @PutMapping("/viaggi/{idViaggio}")
    public Viaggio findByIdAndUpdite(@PathVariable UUID idViaggio, @RequestParam ViaggioDto body) throws org.apache.coyote.BadRequestException {

        return this.viaggioService.findAndUpdite(idViaggio, body);
    }

    // 5. DELETE http://localhost:3001/viaggi/{autoreId}
    @DeleteMapping("/viaggi/{idViaggio}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDipendente(UUID idDipendente) {
        viaggioService.findAndDelete(idDipendente);
    }
}



