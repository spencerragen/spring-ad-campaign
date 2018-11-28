package com.sdr.Exception;

public class NoActiveCampaignException extends Exception {
    public NoActiveCampaignException(String partnerId) {
        super(partnerId + ": no active campaign");
    }

    public NoActiveCampaignException(String partnerId, Throwable err) {
        super(partnerId + ": no active campaign", err);
    }
}
