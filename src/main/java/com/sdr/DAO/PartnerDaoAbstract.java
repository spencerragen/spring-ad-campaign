package com.sdr.DAO;

import com.sdr.Entity.Campaign;
import com.sdr.Entity.Partner;
import com.sdr.Exception.NoActiveCampaignException;

import java.util.Calendar;

abstract class PartnerDaoAbstract implements PartnerDaoInterface {
    protected Campaign traverseCampaignsForActive(Partner p) throws NoActiveCampaignException {
        for (Campaign c : p.getCampaigns()) {
            Calendar now = Calendar.getInstance();
            Calendar campaignStart = c.getCreationDate();
            Calendar campaignEnd = Calendar.getInstance();

            campaignEnd.setTime(campaignStart.getTime());
            campaignEnd.add(Calendar.SECOND, c.getDuration());

            if (now.before(campaignEnd) && now.after(campaignStart)) {
                return c;
            }
        }

        throw new NoActiveCampaignException(p.getPartnerId());
    }
}
