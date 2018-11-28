package com.sdr.Exception;

public class ActiveCampaignConflictException extends Exception {
    public ActiveCampaignConflictException(String errorMessage) {
        super(errorMessage);
    }

    public ActiveCampaignConflictException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
