package com.sdr.Exception;

public class NoSuchPartnerException extends Exception {
    public NoSuchPartnerException(String partnerId) {
        super(partnerId + ": no such partner");
    }

    public NoSuchPartnerException(String partnerId, Throwable err) {
        super(partnerId + ": no such partner", err);
    }
}
