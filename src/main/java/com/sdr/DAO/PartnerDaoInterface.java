package com.sdr.DAO;

import com.sdr.Entity.Campaign;
import com.sdr.Entity.Partner;

import com.sdr.Exception.NoActiveCampaignException;
import com.sdr.Exception.NoSuchPartnerException;

public interface PartnerDaoInterface {
    Partner getPartner(String partnerId) throws NoSuchPartnerException;
    Campaign getActiveCampaign(String partnerId) throws NoSuchPartnerException, NoActiveCampaignException;
}
