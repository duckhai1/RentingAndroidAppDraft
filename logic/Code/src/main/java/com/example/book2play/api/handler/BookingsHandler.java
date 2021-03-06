package com.example.book2play.api.handler;

import com.example.book2play.api.TokenAuthenticator;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.db.BookingModel;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.types.Booking;
import com.google.gson.JsonParseException;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

public class BookingsHandler extends AbstractHandler {

    BookingModel model;

    public BookingsHandler(BookingModel model, TokenAuthenticator authModel) {
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
        var token = exchange.getRequestHeaders().get("Token");
        var params = splitQuery(exchange.getRequestURI().getRawQuery());
        var tokenType = exchange.getRequestHeaders().get("TokenType");

        var date = params.get("date");
        var cityId = params.get("cityId");
        var sportCenterId = params.get("sportCenterId");
        var courtId = params.get("courtId");

        if ((token == null || token.size() != 1)
                || (tokenType == null || tokenType.size() != 1)
                || (date != null && date.size() != 1)
                || (cityId != null && cityId.size() != 1)
                || (sportCenterId != null && sportCenterId.size() != 1)
                || (courtId != null && courtId.size() != 1)) {
            LOG.warning("Request was unsuccessful, invalid query");
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        try {
            Collection<Booking> bookings;
            if (cityId != null && sportCenterId != null && courtId != null && date != null) {
                if (auth.validateStaff(token.get(0),tokenType.get(0),  cityId.get(0), sportCenterId.get(0)) == null) {
                    exchange.sendResponseHeaders(HTTPStatus.UNAUTHORIZED, -1);
                    return;
                }
                bookings = model.getCourtBookings(courtId.get(0), cityId.get(0), sportCenterId.get(0), Date.valueOf(date.get(0)));
            } else if (cityId != null && sportCenterId != null && courtId == null && date != null) {
                if (auth.validateStaff(token.get(0),tokenType.get(0), cityId.get(0), sportCenterId.get(0)) == null) {
                    exchange.sendResponseHeaders(HTTPStatus.UNAUTHORIZED, -1);
                    return;
                }
                bookings = model.getSportCenterBookings(sportCenterId.get(0), cityId.get(0), Date.valueOf(date.get(0)));
            } else if (cityId != null && sportCenterId == null && courtId == null && date != null) {
                var id = auth.validatePlayer(token.get(0), tokenType.get(0));

                if (id == null) {
                    exchange.sendResponseHeaders(HTTPStatus.UNAUTHORIZED, -1);
                    return;
                }
                bookings = model.getPlayerBookingsInCity(id, cityId.get(0), Date.valueOf(date.get(0)));
            } else if (cityId == null && sportCenterId == null && courtId == null && date == null) {
                var id = auth.validatePlayer(token.get(0), tokenType.get(0));
                if (id == null) {
                    exchange.sendResponseHeaders(HTTPStatus.UNAUTHORIZED, -1);
                    return;
                }
                bookings = model.getPlayerBookings(id);
            } else {
                LOG.info("Request was unsuccessful, invalid query");
                exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
                return;
            }
            responseWithJson(exchange, HTTPStatus.OK, bookings);
        } catch (MySQLException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }

    private void execPost(HttpExchange exchange) throws IOException {


        var token = exchange.getRequestHeaders().get("Token");
        var tokenType = exchange.getRequestHeaders().get("TokenType");
        if ((token == null || token.size() != 1)
                || (tokenType == null || tokenType.size() != 1)
        ){
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        var id = auth.validatePlayer(token.get(0), tokenType.get(0));
        if (id == null) {
            exchange.sendResponseHeaders(HTTPStatus.UNAUTHORIZED, -1);
            return;
        }

        Booking booking;
        try {
            booking = GSON.fromJson(new InputStreamReader(exchange.getRequestBody()), Booking.class);
        } catch (JsonParseException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
            return;
        }

        if (booking.getBookingDate() == null
                || booking.getBookingStartTime() == null
                || booking.getBookingEndTime() == null
                || booking.getCityId() == null
                || booking.getSportCenterId() == null
                || booking.getCourtId() == null
        ) {
            LOG.warning("Request was unsuccessful, invalid query");
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        try {
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
            exchange.sendResponseHeaders(HTTPStatus.CREATED, -1);
        } catch (MySQLException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }

    private void execPut(HttpExchange exchange) throws IOException {
        var token = exchange.getRequestHeaders().get("Token");
        var tokenType = exchange.getRequestHeaders().get("TokenType");
        var params = splitQuery(exchange.getRequestURI().getRawQuery());
        var bookingStatus = params.get("status");
        var bookingId = params.get("bookingId");
        var cityId = params.get("cityId");
        var sportCenterId = params.get("sportCenterId");

        if ((token == null || token.size() != 1)
                || (tokenType == null || tokenType.size() != 1)
                || (bookingId != null && bookingId.size() != 1)
                || (bookingStatus != null && bookingStatus.size() != 1)
                || (cityId != null && cityId.size() != 1)
                || (sportCenterId != null && sportCenterId.size() != 1)) {
            LOG.info("Request was unsuccessful, invalid query");
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        if (cityId == null || sportCenterId == null || bookingId == null || bookingStatus == null) {
            LOG.info("Request was unsuccessful, invalid query");
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        var id = auth.validateStaff(token.get(0), tokenType.get(0), cityId.get(0), sportCenterId.get(0));
        if (id == null) {
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        try {
            model.updateBookingStatus(
                    Boolean.parseBoolean(bookingStatus.get(0)),
                    bookingId.get(0),
                    cityId.get(0),
                    sportCenterId.get(0),
                    id
            );
            exchange.sendResponseHeaders(HTTPStatus.ACCEPTED, -1);
        } catch (MySQLException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }

    private void execDelete(HttpExchange exchange) throws IOException {
        var token = exchange.getRequestHeaders().get("Token");
        var tokenType = exchange.getRequestHeaders().get("TokenType");
        var params = splitQuery(exchange.getRequestURI().getRawQuery());
        var bookingId = params.get("bookingId");

        if ((token == null || token.size() != 1)
                || (tokenType == null || tokenType.size() != 1)
                || (bookingId != null && bookingId.size() != 1)
        ) {
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }


        var id = auth.validatePlayer(token.get(0), tokenType.get(0));
        if (id == null) {
            exchange.sendResponseHeaders(HTTPStatus.UNAUTHORIZED, -1);
            return;
        }

        if (bookingId == null) {
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        try {
            model.cancelBooking(bookingId.get(0), id);
            exchange.sendResponseHeaders(HTTPStatus.ACCEPTED, -1);
        } catch (MySQLException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
        }
    }
}
