package com.sdr.DAO;

import com.sdr.Entity.Campaign;
import com.sdr.Entity.Partner;

import com.sdr.Exception.*;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Qualifier("static")
public class PartnerDaoStatic implements PartnerDaoInterface {
    private static Map<String, Partner> partners;

    static {
        partners = new HashMap<String, Partner>() {
            {
                ArrayList<Campaign> c;
                Calendar nextBlock = Calendar.getInstance();
                nextBlock.add(Calendar.SECOND, 1);

                c = new ArrayList<Campaign>();
                nextBlock.add(Calendar.HOUR, 24);
                c.add(new Campaign("ed6bc819-6e58-43c8-8337-0d9bc58c3f04", "Test Ad Campaign", 86400));
                c.add(new Campaign("ed6bc819-6e58-43c8-8337-0d9bc58c3f04", "Test Ad Campaign 2", 86400, nextBlock));
                put("ed6bc819-6e58-43c8-8337-0d9bc58c3f04", new Partner("ed6bc819-6e58-43c8-8337-0d9bc58c3f04", c));

                c = new ArrayList<Campaign>();
                nextBlock.add(Calendar.HOUR, 24);
                c.add(new Campaign("cca4b4ce-e846-4cce-9728-ea1278ff0df2", "Not Actually an Ad Campaign", 172800));
                c.add(new Campaign("cca4b4ce-e846-4cce-9728-ea1278ff0df2", "Not Actually an Ad Campaign 2", 172800, nextBlock));
                put("cca4b4ce-e846-4cce-9728-ea1278ff0df2", new Partner("cca4b4ce-e846-4cce-9728-ea1278ff0df2", c));

                c = new ArrayList<Campaign>();
                nextBlock.add(Calendar.HOUR, 24);
                c.add(new Campaign("838be700-7dc6-4998-acd5-732b2c9173bb", "10 EASY STEPS TO PERFECT COLD FUSION", 259200));
                c.add(new Campaign("838be700-7dc6-4998-acd5-732b2c9173bb", "10 EASY STEPS TO PERFECT COLD FUSION 2", 259200, nextBlock));
                put("838be700-7dc6-4998-acd5-732b2c9173bb", new Partner("838be700-7dc6-4998-acd5-732b2c9173bb", c));

                c = new ArrayList<Campaign>();
                nextBlock.add(Calendar.HOUR, 24);
                c.add(new Campaign("d5dc9d34-5b16-4bb5-aae9-4fb99adf4479", "i like cats", 345600));
                c.add(new Campaign("d5dc9d34-5b16-4bb5-aae9-4fb99adf4479", "i like cats 2", 345600, nextBlock));
                put("d5dc9d34-5b16-4bb5-aae9-4fb99adf4479", new Partner("d5dc9d34-5b16-4bb5-aae9-4fb99adf4479", c));

                c = new ArrayList<Campaign>();
                nextBlock.add(Calendar.HOUR, 24);
                c.add(new Campaign("290e6d0e-29e0-4c0b-8722-b98b3d3042a7", "How to make physics work differently on <i>your</i> stove top!", 432000));
                c.add(new Campaign("290e6d0e-29e0-4c0b-8722-b98b3d3042a7", "How to make physics work differently on <i>your</i> stove top! 2", 432000, nextBlock));
                put("290e6d0e-29e0-4c0b-8722-b98b3d3042a7", new Partner("290e6d0e-29e0-4c0b-8722-b98b3d3042a7", c));
            }
        };
    }

    @Override
    public Partner getPartner(String partnerId) throws NoSuchPartnerException {
        if (this.partners.containsKey(partnerId)) {
            return this.partners.get(partnerId);
        }

        throw new NoSuchPartnerException(partnerId);
    }

    @Override
    public Campaign getActiveCampaign(String partnerId) throws NoSuchPartnerException, NoActiveCampaignException {
        Partner p = this.getPartner(partnerId);

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

        System.out.println(partnerId + ": no campaign");

        throw new NoActiveCampaignException(partnerId);
    }
}
