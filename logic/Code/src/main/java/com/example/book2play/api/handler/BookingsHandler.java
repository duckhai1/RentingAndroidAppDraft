package com.example.book2play.api.handler;

import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.db.Authenticator;
import com.example.book2play.db.BookingModel;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.Booking;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

public class BookingsHandler extends AbstractHandler {

    BookingModel model;

    public BookingsHandler(BookingModel model, Authenticator authModel) {
        super(authModel);
        this.model = model;
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
            responseErrorAsJson(exchange, HTTPStatus.INTERNAL_SERVER_ERROR, e);
        }
        exchange.close();
    }

    private void execGet(HttpExchange exchange) throws IOException {
        var params = splitQuery(exchange.getRequestURI().getRawQuery());
        //var token = exchange.getRequestHeaders().get("Token").get(0);
        var date = params.get("date");
        var cityId = params.get("cityId");
        var sportCenterId = params.get("sportCenterId");
        var playerId = params.get("playerId");
        var courtId = params.get("courtId");

        if ((date != null && date.size() != 1)
                || (cityId != null && cityId.size() != 1)
                || (sportCenterId != null && sportCenterId.size() != 1)
                || (courtId != null && courtId.size() != 1)
                || (playerId != null && playerId.size() != 1)
        ) {
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        Collection<Booking> bookings;
        try {
            if (playerId == null && cityId != null && sportCenterId != null && courtId != null && date != null) {
                bookings = model.getCourtBookings(
                        courtId.get(0),
                        cityId.get(0),
                        sportCenterId.get(0),
                        Date.valueOf(date.get(0))
                );
            } else if (playerId == null && cityId != null && sportCenterId != null && courtId == null && date != null) {
                bookings = model.getSportCenterBookings(
                        sportCenterId.get(0),
                        cityId.get(0),
                        Date.valueOf(date.get(0))
                );

            } else if (playerId != null && cityId != null && sportCenterId == null && courtId == null && date != null) {
                bookings = model.getPlayerBookingsInCity(
                        playerId.get(0),
                        cityId.get(0),
                        Date.valueOf(date.get(0))
                );
            } else if (playerId != null && cityId == null && sportCenterId == null && courtId == null && date == null) {
                bookings = model.getPlayerBookings(playerId.get(0));
            } else {
                exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
                return;
            }
            responseWithJson(exchange, HTTPStatus.OK, bookings);
        } catch (MySQLException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        } catch (IllegalArgumentException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        }

    }

    private void execPost(HttpExchange exchange) throws IOException {
        try {
            var booking = GSON.fromJson(new InputStreamReader(exchange.getRequestBody()), Booking.class);
            model.createBooking(
                    new Timestamp(System.currentTimeMillis()),
                    booking.getBookingDate(),
                    booking.getBookingStartTime(),
                    booking.getBookingEndTime(),
                    booking.getCityId(),
                    booking.getSportCenterId(),
                    booking.getCourtId(),
                    booking.getPlayerId()
            );
            exchange.sendResponseHeaders(HTTPStatus.CREATED, -1);
        } catch (MySQLException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        } catch (IllegalArgumentException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }

    private void execPut(HttpExchange exchange) throws IOException {
        var params = splitQuery(exchange.getRequestURI().getRawQuery());
        var bookingStatus = params.get("status");
        var bookingId = params.get("bookingId");
        var cityId = params.get("cityId");
        var sportCenterId = params.get("sportCenterId");
        var staffId = params.get("staffId");

        if ((bookingId != null && bookingId.size() != 1)
                || (staffId != null && staffId.size() != 1)
                || (bookingStatus != null && bookingStatus.size() != 1)
                || (cityId != null && cityId.size() != 1)
                || (sportCenterId != null && sportCenterId.size() != 1)
        ) {
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        try {
            if (bookingId != null && staffId != null && bookingStatus != null) {
                model.updateBookingStatus(
                        Boolean.parseBoolean(bookingStatus.get(0)),
                        bookingId.get(0),
                        cityId.get(0),
                        sportCenterId.get(0),
                        staffId.get(0)
                );
            } else {
                exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
                return;
            }
            exchange.sendResponseHeaders(HTTPStatus.ACCEPTED, -1);
        } catch (MySQLException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        } catch (IllegalArgumentException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }

    private void execDelete(HttpExchange exchange) throws IOException {
        var params = splitQuery(exchange.getRequestURI().getRawQuery());
        var bookingId = params.get("bookingId");
        var playerId = params.get("playerId");

        if ((bookingId != null && bookingId.size() != 1)
                || (playerId != null && playerId.size() != 1)
        ) {
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        try {
            if (playerId != null && bookingId != null) {
                model.cancelBooking(bookingId.get(0), playerId.get(0));
            } else {
                exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
                return;
            }
            exchange.sendResponseHeaders(HTTPStatus.ACCEPTED, -1);
        } catch (MySQLException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        } catch (IllegalArgumentException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }
}