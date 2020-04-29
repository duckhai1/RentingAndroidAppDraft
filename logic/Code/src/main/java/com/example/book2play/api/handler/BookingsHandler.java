package com.example.book2play.api.handler;

import com.example.book2play.api.handler.utils.ConfirmToken;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.AuthenticateModel;
import com.example.book2play.db.models.BookingModel;
import com.example.book2play.types.Booking;
import com.restfb.exception.FacebookOAuthException;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

public class BookingsHandler extends AbstractHandler {

    BookingModel model;
    AuthenticateModel authenticateModel;
    public BookingsHandler(BookingModel model, AuthenticateModel authenticateModel) {
        super();
        this.model = model;
        this.authenticateModel = authenticateModel;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if ("GET".equals(exchange.getRequestMethod())) {
                execGet(exchange);
            } else if ("POST".equals(exchange.getRequestMethod())) {
                execPost(exchange);
            } else if ("PUT".equals(exchange.getRequestMethod())) {
                execPut(exchange);
            } else if ("DELETE".equals(exchange.getRequestMethod())) {
                execDelete(exchange);
            } else {
                exchange.sendResponseHeaders(HTTPStatus.METHOD_NOT_ALLOWED, -1); // NOT ALLOWED
            }
        } catch (RuntimeException e) {
            LOG.severe("Unexpected exception " + e.getMessage());
            responseWithJsonException(exchange, HTTPStatus.INTERNAL_SERVER_ERROR, e);
        }
        exchange.close();
    }

    private void execGet(HttpExchange exchange) throws IOException {
        var params = splitQuery(exchange.getRequestURI().getRawQuery());
        var token = exchange.getRequestHeaders().get("Token");
        var date = params.get("date");
        var cityId = params.get("cityId");
        var sportCenterId = params.get("sportCenterId");
        var courtId = params.get("courtId");

        if ((token == null || token.size() != 1)
                || (date != null && date.size() != 1)
                || (cityId != null && cityId.size() != 1)
                || (sportCenterId != null && sportCenterId.size() != 1)
                || (courtId != null && courtId.size() != 1)
        ) {
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        Collection<Booking> bookings;
        try {
            var id = ConfirmToken.getId(token.get(0));
            if (cityId != null && sportCenterId != null && courtId != null && date != null) {
                if (authenticateModel.isStaff(
                        id,
                        cityId.get(0),
                        sportCenterId.get(0))
                ) {
                    bookings = model.getCourtBookings(
                            courtId.get(0),
                            cityId.get(0),
                            sportCenterId.get(0),
                            Date.valueOf(date.get(0))
                    );
                } else {
                    exchange.sendResponseHeaders(HTTPStatus.UNAUTHORIZED, -1);
                    return;
                }
            } else if (cityId != null && sportCenterId != null && courtId == null && date != null) {
               if (authenticateModel.isStaff(
                       id,
                       cityId.get(0),
                       sportCenterId.get(0))
               ) {
                   bookings = model.getSportCenterBookings(
                           sportCenterId.get(0),
                           cityId.get(0),
                           Date.valueOf(date.get(0))
                   );
               } else {
                   exchange.sendResponseHeaders(HTTPStatus.UNAUTHORIZED, -1);
                   return;
               }
            } else if (cityId != null && sportCenterId == null && courtId == null && date != null) {
                if (authenticateModel.isPlayer(id)) {
                    bookings = model.getPlayerBookingsInCity(
                            id,
                            cityId.get(0),
                            Date.valueOf(date.get(0))
                    );
                } else {
                    exchange.sendResponseHeaders(HTTPStatus.UNAUTHORIZED, -1);
                    return;
                }
            } else if (cityId == null && sportCenterId == null && courtId == null && date == null) {
                if (authenticateModel.isPlayer(id)) {
                   bookings = model.getPlayerBookings(id);
               } else {
                    exchange.sendResponseHeaders(HTTPStatus.UNAUTHORIZED, -1);
                    return;
                }
            } else {
                exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
                return;
            }
            responseWithJson(exchange, HTTPStatus.OK, bookings);
        } catch (MySQLException | IllegalArgumentException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseWithJsonException(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }

    private void execPost(HttpExchange exchange) throws IOException {
        var token = exchange.getRequestHeaders().get("Token");
        if (token == null || token.size() != 1){
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }
        try {
            var id = ConfirmToken.getId(token.get(0));
            var booking = GSON.fromJson(new InputStreamReader(exchange.getRequestBody()), Booking.class);
            if(authenticateModel.isPlayer(id)) {
                model.createBooking(
                        new Timestamp(System.currentTimeMillis()),
                        booking.getBookingDate(),
                        booking.getBookingStartTime(),
                        booking.getBookingEndTime(),
                        booking.getCityId(),
                        booking.getSportCenterId(),
                        booking.getCourtId(),
                        id
                );
            } else {
                exchange.sendResponseHeaders(HTTPStatus.UNAUTHORIZED, -1);
                return;
            }
            exchange.sendResponseHeaders(HTTPStatus.CREATED, -1);
        } catch (MySQLException | IllegalArgumentException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseWithJsonException(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }

    private void execPut(HttpExchange exchange) throws IOException {
        var params = splitQuery(exchange.getRequestURI().getRawQuery());
        var token = exchange.getRequestHeaders().get("Token");
        var bookingStatus = params.get("status");
        var bookingId = params.get("bookingId");
        var cityId = params.get("cityId");
        var sportCenterId = params.get("sportCenterId");

        if( (token == null || token.size() != 1)
            || (bookingId != null && bookingId.size() !=1)
            || (bookingStatus != null && bookingStatus.size() != 1)
            || (cityId != null && cityId.size() !=1)
            || (sportCenterId != null && sportCenterId.size() != 1)
        ){
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        try{
            var id = ConfirmToken.getId(token.get(0));
            if(bookingId != null && bookingStatus != null){
                if(authenticateModel.isStaff(
                        id,
                        cityId.get(0),
                        sportCenterId.get(0))
                ){
                    model.updateBookingStatus(
                            Boolean.parseBoolean(bookingStatus.get(0)),
                            bookingId.get(0),
                            cityId.get(0),
                            sportCenterId.get(0),
                            id
                    );
                } else {
                    exchange.sendResponseHeaders(HTTPStatus.UNAUTHORIZED, -1);
                    return;
                }
            } else{
                exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
                return;
            }
            exchange.sendResponseHeaders(HTTPStatus.ACCEPTED, -1);
        } catch (MySQLException | IllegalArgumentException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseWithJsonException(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }

    private void execDelete(HttpExchange exchange) throws IOException {
        var params = splitQuery(exchange.getRequestURI().getRawQuery());
        var token = exchange.getRequestHeaders().get("Token");
        var bookingId = params.get("bookingId");

        if ( (token == null || token.size() != 1)
            || (bookingId != null && bookingId.size() != 1)
        ){
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        try{
            var id = ConfirmToken.getId(token.get(0));

            if(bookingId != null){
                if(authenticateModel.isPlayer(id)) {
                    model.cancelBooking(bookingId.get(0), id);
                } else {
                    exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
                    return;
                }
            }
            else{
                exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
                return;
            }
            exchange.sendResponseHeaders(HTTPStatus.ACCEPTED, -1);
        } catch (MySQLException | IllegalArgumentException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseWithJsonException(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }
}
