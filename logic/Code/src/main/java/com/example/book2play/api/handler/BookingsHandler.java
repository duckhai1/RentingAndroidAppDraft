package com.example.book2play.api.handler;

import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.db.models.BookingModel;
import com.example.book2play.types.Booking;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.Collection;

public class BookingsHandler extends AbstractHandler {

    BookingModel model;

    public BookingsHandler(BookingModel model) {
        super();
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
                exchange.sendResponseHeaders(405, -1); // NOT ALLOWED
            }
        } catch (RuntimeException e) {
            LOG.severe("Unexpected exception " + e.getMessage());
            responseWithJsonException(exchange, HTTPStatus.INTERNAL_SERVER_ERROR, e);
        }
        exchange.close();
    }

    private void execGet(HttpExchange exchange) throws IOException {
        var params = splitQuery(exchange.getRequestURI().getRawQuery());
        var date = params.get("date");
        var cityId = params.get("cityId");
        var sportCenterId = params.get("sportCenterId");
        var playerId = params.get("playerId");
        var courtId = params.get("courtId");

        if ((date == null || date.size() != 1)
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
            if (playerId == null && cityId != null && sportCenterId != null && courtId != null) {
                bookings = model.getCourtBookings(
                        courtId.get(0),
                        cityId.get(0),
                        sportCenterId.get(0),
                        Date.valueOf(date.get(0))
                );
            } else if (playerId == null && cityId != null && sportCenterId != null) {
                bookings = model.getSportCenterBookings(
                        sportCenterId.get(0),
                        cityId.get(0),
                        Date.valueOf(date.get(0))
                );
            } else if (playerId != null && cityId != null && sportCenterId == null) {
                bookings = model.getPlayerBookings(
                        playerId.get(0),
                        cityId.get(0),
                        Date.valueOf(date.get(0))
                );
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
        try {
            var booking = GSON.fromJson(new InputStreamReader(exchange.getRequestBody()), Booking.class);
            model.createBooking(
                    booking.getBookingId(),
                    booking.getCreatedAt(),
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
            responseWithJsonException(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }

    private void execPut(HttpExchange exchange) {
    }

    private void execDelete(HttpExchange exchange) {
    }
}
