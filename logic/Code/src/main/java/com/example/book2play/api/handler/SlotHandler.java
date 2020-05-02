package com.example.book2play.api.handler;

import com.example.book2play.api.TokenAuthenticator;
import com.example.book2play.api.utils.HTTPStatus;
import com.example.book2play.db.BookingModel;
import com.example.book2play.db.SportCenterModel;
import com.example.book2play.db.exceptions.MySQLException;
import com.example.book2play.logic.SlotService;
import com.example.book2play.types.Booking;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.sql.Date;
import java.util.LinkedList;

public class SlotHandler extends AbstractHandler {
    static String OPEN_TIME = "07:00:00";
    static String CLOSE_TIME = "21:00:00";
    static int MIN_DURATION_IN_MINUTES = 45;

    BookingModel bookingModel;
    SportCenterModel sportCenterModel;

    public SlotHandler(BookingModel bookingModel, SportCenterModel sportCenterModel, TokenAuthenticator authModel) {
        super(authModel);
        this.bookingModel = bookingModel;
        this.sportCenterModel = sportCenterModel;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if ("GET".equals(exchange.getRequestMethod())) {
                execGet(exchange);
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
        var courtId = params.get("courtId");
        var sportCenterId = params.get("sportCenterId");
        var cityId = params.get("cityId");
        var date = params.get("date");

        if ((token == null || token.size() != 1)
                || (courtId != null && courtId.size() != 1)
                || (sportCenterId != null && sportCenterId.size() != 1)
                || (cityId != null && cityId.size() != 1)
                || (date != null && date.size() != 1)) {
            LOG.warning("Request was unsuccessful, invalid query");
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        if (auth.validatePlayer(token.get(0)) == null) {
            exchange.sendResponseHeaders(HTTPStatus.UNAUTHORIZED, -1);
            return;
        }

        if (courtId == null || sportCenterId == null || cityId == null || date == null) {
            LOG.warning("Request was unsuccessful, invalid query");
            exchange.sendResponseHeaders(HTTPStatus.BAD_REQUEST, -1);
            return;
        }

        LinkedList<Booking> bookings;
        try {
            bookings = new LinkedList<>(bookingModel.getCourtBookings(
                    courtId.get(0),
                    cityId.get(0),
                    sportCenterId.get(0),
                    Date.valueOf(date.get(0))
            ));
        } catch (MySQLException e) {
            LOG.warning("Request was unsuccessful " + e.getMessage());
            responseErrorAsJson(exchange, HTTPStatus.BAD_REQUEST, e);
            return;
        }

        var slotService = new SlotService(OPEN_TIME, CLOSE_TIME, MIN_DURATION_IN_MINUTES);
        var slots = slotService.getAvailableSlots(bookings,
                cityId.get(0),
                sportCenterId.get(0),
                courtId.get(0)
        );
        responseWithJson(exchange, HTTPStatus.OK, slots);
    }
}