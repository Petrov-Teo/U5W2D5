package PetrovTodor.Gestione.Viaggi.e.Prenotazioni.exceptions;

import PetrovTodor.Validation_Images_Upload_E_Emails.payload.ErrorsPayloadRecord;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(BadRequestExeption.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)//Err 400
    public ErrorsPayloadRecord handleBadRequest(BadRequestExeption ex) {
        return new ErrorsPayloadRecord(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)//Err 404
    public ErrorsPayloadRecord handleNotFound(NotFoundException ex) {
        return new ErrorsPayloadRecord(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //Err 500
    public ErrorsPayloadRecord handleGenericErors(Exception ex) {
        ex.printStackTrace();// Serve per catturare tutti gli errori e stamparli nella console
        return new ErrorsPayloadRecord(ex.getMessage(), LocalDateTime.now());
    }

}
