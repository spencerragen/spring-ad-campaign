package com.sdr.Controller;

import com.sdr.Entity.Campaign;
import com.sdr.Entity.Partner;

import com.sdr.Exception.NoActiveCampaignException;
import com.sdr.Exception.NoSuchPartnerException;

import com.sdr.Service.PartnerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collection;

@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class Router {

    @Autowired
    private PartnerService partnerService;

    @GetMapping(path = "/sanity/{partnerId}")
    public ResponseEntity sanityCheck(HttpEntity<byte[]> requestEntity, @PathVariable("partnerId") String partnerId) {
        String apiKey = requestEntity.getHeaders().getFirst("apikey");

        try {
            Partner p = partnerService.getPartner(partnerId);

            if (apiKey == null || !apiKey.equals(p.getApiKey())) {
                p.setApiKey("");
            }

            return new ResponseEntity<>(p, HttpStatus.OK);
        } catch (NoSuchPartnerException nsp) {
            nsp.printStackTrace();

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (SQLException sqle) {
            // if we want to ignore HTTP status code standards we can change the 500 here to something like 412
            sqle.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/list/{partnerId}")
    public ResponseEntity listCampaigns(@PathVariable("partnerId") String partnerId) {
        try {
            Collection<Campaign> campaigns = partnerService.getPartner(partnerId).getCampaigns();

            return new ResponseEntity<>(campaigns, HttpStatus.OK);
        } catch (SQLException sqle) {
            sqle.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoSuchPartnerException err) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/campaign/{partnerId}")
    public ResponseEntity getCampaign(@PathVariable("partnerId") String partnerId) {
        try {
            return new ResponseEntity<>(this.partnerService.getActiveCampaign(partnerId), HttpStatus.OK);
        } catch (NoSuchPartnerException err) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NoActiveCampaignException err) {
            err.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
