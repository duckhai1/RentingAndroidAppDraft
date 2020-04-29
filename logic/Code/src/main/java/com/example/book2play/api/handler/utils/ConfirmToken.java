package com.example.book2play.api.handler.utils;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.User;

public class ConfirmToken {
    public static String getId(String token) throws FacebookOAuthException{
        FacebookClient facebookClient = new DefaultFacebookClient(token);

        User user = facebookClient.fetchObject("me",
                User.class,
                Parameter.with("fields", "id"));
        if (user == null){
            throw new FacebookOAuthException("Bad user data", "user is null");
        }

        return user.getId();
    }
}
