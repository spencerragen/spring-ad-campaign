package com.sdr.Service;

import com.sdr.DAO.PartnerDaoInterface;
import com.sdr.Entity.Campaign;
import com.sdr.Entity.Partner;
import com.sdr.Exception.NoActiveCampaignException;
import com.sdr.Exception.NoSuchPartnerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PartnerService {
    @Autowired
    @Qualifier("static")
    private PartnerDaoInterface partnerDao;

    public Partner getPartner(String partnerId) throws NoSuchPartnerException {
        return this.partnerDao.getPartner(partnerId);
    }

    public Campaign getActiveCampaign(String partnerId) throws NoSuchPartnerException, NoActiveCampaignException {
        return this.partnerDao.getActiveCampaign(partnerId);
    }
}
