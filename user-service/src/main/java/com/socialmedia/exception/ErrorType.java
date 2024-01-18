package com.socialmedia.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ErrorType {
    REGISTER_PASSWORD_MISMATCH(1001,"Girilen parolalar uyusmadi",HttpStatus.BAD_REQUEST),
    REGISTER_EMAIL_ALREADY_EXISTS(1002,"Bu email adresi daha once kaydedilmis.",HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(2001,"Mail veya Sifre yanlis.",HttpStatus.NOT_FOUND),
    INVALID_TOKEN_FORMAT(3001,"Gecersiz token formati",HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(3002,"Gecersiz token",HttpStatus.BAD_REQUEST),
    TOKEN_NOT_CREATED(3003,"Token uretilemedi",HttpStatus.BAD_REQUEST),
    PARAMETER_NOT_VALID(5000,"Parametre hatasi",HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_ACTIVE(4000,"Hesabiniz aktif degil",HttpStatus.FORBIDDEN),
    ACTIVATION_CODE_MISMATCH(4001,"Hesabiniz aktif degil",HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR_SERVER(9999,"Server Hatasi ",HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_ACTIVE(2002,"Hesap Aktif Degil ",HttpStatus.BAD_REQUEST),
    USER_ALREADY_DELETED(2002,"Hesap zaten silinmis ",HttpStatus.BAD_REQUEST),
    USER_ALREADY_ACTIVE(2002,"Hesap zaten aktif ",HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    private HttpStatus httpStatus;
}
