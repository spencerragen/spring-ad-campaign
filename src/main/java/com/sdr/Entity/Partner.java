package com.sdr.Entity;

import java.util.Collection;

public class Partner {
    private String partnerId;
    private String apiKey;

    private Collection<Campaign> campaigns;

    public Partner(String partnerId) {
        this.partnerId = partnerId;
    }

    public Partner(String partnerId, String apiKey) {
        this.partnerId = partnerId;
        this.apiKey = apiKey;
    }

    public Partner(String partnerId, String apiKey, Collection<Campaign> campaigns) {
        this.partnerId = partnerId;
        this.apiKey = apiKey;
        this.campaigns = campaigns;
    }

    public Partner(String partnerId, Collection<Campaign> campaigns) {
        this.partnerId = partnerId;
        this.campaigns = campaigns;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Collection<Campaign> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(Collection<Campaign> campaigns) {
        this.campaigns = campaigns;
    }

    public void addCampaign(Campaign campaign) {
        this.campaigns.add(campaign);
    }
}
