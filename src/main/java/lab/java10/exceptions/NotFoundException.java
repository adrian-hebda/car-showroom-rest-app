package lab.java10.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (code =HttpStatus.NOT_FOUND, reason = "Can't find searched entity")
public class NotFoundException extends RuntimeException{
}
