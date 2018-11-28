package com.sdr.Controller;

import com.sdr.Entity.Campaign;

import com.sdr.Exception.NoActiveCampaignException;
import com.sdr.Exception.NoSuchPartnerException;
import com.sdr.Service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class Router {

    @Autowired
    private PartnerService partnerService;

    @GetMapping(path = "/list/{partnerId}")
    public ResponseEntity listCampaigns(@PathVariable("partnerId") String partnerId) {
        try {
            Collection<Campaign> campaigns = partnerService.getPartner(partnerId).getCampaigns();

            return new ResponseEntity<>(campaigns, HttpStatus.OK);
        } catch (NoSuchPartnerException err) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(path = "/campaign/{partnerId}")
    public ResponseEntity getCampaign(@PathVariable("partnerId") String partnerId) {
        try {
            return new ResponseEntity<>(this.partnerService.getActiveCampaign(partnerId), HttpStatus.OK);
        } catch (NoSuchPartnerException err) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoActiveCampaignException err) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
