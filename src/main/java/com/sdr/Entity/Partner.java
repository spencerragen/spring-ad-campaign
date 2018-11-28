package com.sdr.Entity;

import java.util.Collection;

public class Partner {
    private String partnerId;
    private Collection<Campaign> campaigns;

    public Partner(String partnerId) {
        this.partnerId = partnerId;
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
