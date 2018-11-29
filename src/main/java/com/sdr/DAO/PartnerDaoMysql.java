package com.sdr.DAO;

import com.sdr.Entity.Campaign;
import com.sdr.Entity.Partner;
import com.sdr.Exception.NoActiveCampaignException;
import com.sdr.Exception.NoSuchPartnerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import java.sql.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;


@Repository
@Qualifier("mysql")
public class PartnerDaoMysql extends PartnerDaoAbstract implements PartnerDaoInterface {

    private Connection dbConnection;

    @Value("${mysql.driver}")
    private String driver;

    @Value("${mysql.host}")
    private String host;

    @Value("${mysql.port}")
    private String port;

    @Value("${mysql.database}")
    private String database;

    @Value("${mysql.user}")
    private String user;

    @Value("${mysql.password}")
    private String password;

    private String url;

    /**
     * Set our required configurations, build the url, and create the connection object for later use.
     *
     * @param env Autowired Spring Boot environment
     */
    @Autowired
    public PartnerDaoMysql(Environment env) {
        try {
            this.driver = env.getRequiredProperty("mysql.driver");
            this.host = env.getRequiredProperty("mysql.host");
            this.port = env.getProperty("mysql.port");
            this.database = env.getRequiredProperty("mysql.database");
            this.user = env.getRequiredProperty("mysql.user");
            this.password = env.getProperty("mysql.password");

            this.buildUrl();

            Class.forName(this.driver);
            this.dbConnection = DriverManager.getConnection(this.url, this.user, this.password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Build out the driver connection url based on configured values.  This totally ignores extra values, and does not
     * concat the username or password since we send those as distinct arguments to the driver manager.
     */
    private void buildUrl() {
        this.url = "jdbc:mysql://" + this.host;

        if (this.port != null) {
            this.url += ':' + this.port;
        }

        this.url += '/' + this.database;
    }


    /**
     * Create a Partner entity from a partnerId by searching the `partners` table
     * @param partnerId String
     * @return a Partner object containing the partnerId, api key, and a list of all campaigns (if any exist)
     * @throws NoSuchPartnerException if there is no row with the supplied partnerId
     * @throws SQLException if the query is malformed
     */
    @Override
    public Partner getPartner(String partnerId) throws NoSuchPartnerException, SQLException {
        String query = "SELECT api_key FROM partners WHERE partner_id = ? LIMIT 1";
        PreparedStatement statement;
        Partner p;

        try {
            statement = this.dbConnection.prepareStatement(query);
            statement.setString(1, partnerId);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                p = new Partner(partnerId, rs.getString("api_key"));
                Collection<Campaign> c = this.getCampaigns(p.getPartnerId());

                p.setCampaigns(c);

                return p;
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new NoSuchPartnerException(partnerId);
    }

    /**
     *
     * @param partnerId
     * @return
     * @throws NoSuchPartnerException
     * @throws NoActiveCampaignException
     */
    @Override
    public Campaign getActiveCampaign(String partnerId) throws NoSuchPartnerException, NoActiveCampaignException {
        Partner p;

        try {
            p = this.getPartner(partnerId);

            return this.traverseCampaignsForActive(p);
        } catch (NoSuchPartnerException nspe) {
            throw nspe;
        } catch (NoActiveCampaignException nace) {
            throw nace;
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new NoActiveCampaignException(partnerId);
    }

    /**
     * Fetch a list of all campaigns associated with the specified partnerId
     * @param partnerId the uuid string of the partner to search against
     * @return a collection of campaigns
     * @throws SQLException if the query is malformed
     */
    public Collection<Campaign> getCampaigns(String partnerId) throws SQLException {
        Collection<Campaign> c = new ArrayList<Campaign>();

        String query = "SELECT content, date_created, duration FROM campaigns WHERE partner_id = ? ORDER BY date_created ASC";
        PreparedStatement statement;

        try {
            statement = this.dbConnection.prepareStatement(query);
            statement.setString(1, partnerId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Calendar creationDate = Calendar.getInstance();

                creationDate.setTime(rs.getTimestamp("date_created"));

                Campaign g = new Campaign(partnerId, rs.getString("content"), rs.getInt("duration"), creationDate);

                c.add(g);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();

            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return c;
    }
}
