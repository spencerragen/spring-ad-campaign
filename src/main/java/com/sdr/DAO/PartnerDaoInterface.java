package com.sdr.DAO;

import com.sdr.Entity.Campaign;
import com.sdr.Entity.Partner;

import com.sdr.Exception.NoActiveCampaignException;
import com.sdr.Exception.NoSuchPartnerException;

import java.sql.SQLException;
import java.util.Calendar;

public interface PartnerDaoInterface {
    Partner getPartner(String partnerId) throws NoSuchPartnerException, SQLException;
    Campaign getActiveCampaign(String partnerId) throws NoSuchPartnerException, NoActiveCampaignException;
}
