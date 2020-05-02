package com.example.book2play.api.utils;

import com.example.book2play.api.TokenAuthenticator;
import com.example.book2play.db.PlayerModel;
import com.example.book2play.db.StaffModel;
import com.example.book2play.db.exceptions.MySQLException;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.User;

import java.util.logging.Logger;


public class FbTokenValidator implements TokenAuthenticator {

    private static Logger LOG = Logger.getLogger("AUTHENTICATOR");

    private PlayerModel playerModel;
    private StaffModel staffModel;

    public FbTokenValidator(PlayerModel playerModel, StaffModel staffModel) {
        this.playerModel = playerModel;
        this.staffModel = staffModel;
    }

    @Override
    public String validatePlayer(String token) {
        try {
            var playerId = getId(token);
            if (!playerModel.isPlayerExist(playerId)) {
                return null;
            }
            return playerId;
        } catch (FacebookOAuthException | MySQLException e) {
            LOG.warning("Could not validate token");
            return null;
        }
    }

    @Override
    public String validateStaff(String token, String cityId, String sportCenterId) {
        try {
            var staffId = getId(token);
            if (!staffModel.isStaffExist(staffId, cityId, sportCenterId)) {
                return null;
            }
            return staffId;
        } catch (MySQLException e) {
            LOG.warning("Could not validate token");
            return null;
        }
    }

    @Override
    public String getId(String token) {
        FacebookClient facebookClient = new DefaultFacebookClient(token);
        User user = facebookClient.fetchObject("me", User.class, Parameter.with("fields", "id"));
        if (user == null) {
            return null;
        }
        return user.getId();
    }
}
