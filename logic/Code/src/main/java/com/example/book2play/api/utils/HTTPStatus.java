package com.example.book2play.api.utils;

/**
 * Easy to access HTTP status code
 * https://en.wikipedia.org/wiki/List_of_HTTP_status_codes
 */
public class HTTPStatus {
    public final static int CONTINUE = 100;
    public final static int SWITCHING_PROTOCOLS = 101;
    public final static int PROCESSING = 102;
    public final static int EARLY_HINTS = 103;
    public final static int OK = 200;
    public final static int CREATED = 201;
    public final static int ACCEPTED = 202;
    public final static int NON_AUTHORITATIVE_INFORMATION = 203;
    public final static int NO_CONTENT = 204;
    public final static int RESET_CONTENT = 205;
    public final static int PARTIAL_CONTENT = 206;
    public final static int MULTI_STATUS = 207;
    public final static int ALREADY_REPORTED = 208;
    public final static int IM_USED = 226;
    public final static int MULTIPLE_CHOICES = 300;
    public final static int MOVED_PERMANENTLY = 301;
    public final static int FOUND = 302;
    public final static int SEE_OTHER = 303;
    public final static int NOT_MODIFIED = 304;
    public final static int USE_PROXY = 305;
    public final static int SWITCH_PROXY = 306;
    public final static int TEMPORARY_REDIRECT = 307;
    public final static int PERMANENT_REDIRECT = 308;
    public final static int BAD_REQUEST = 400;
    public final static int UNAUTHORIZED = 401;
    public final static int PAYMENT_REQUIRED = 402;
    public final static int FORBIDDEN = 403;
    public final static int NOT_FOUND = 404;
    public final static int METHOD_NOT_ALLOWED = 405;
    public final static int NOT_ACCEPTABLE = 406;
    public final static int PROXY_AUTHENTICATION_REQUIRED = 407;
    public final static int REQUEST_TIMEOUT = 408;
    public final static int CONFLICT = 409;
    public final static int GONE = 410;
    public final static int LENGTH_REQUIRED = 411;
    public final static int PRECONDITION_FAILED = 412;
    public final static int PAYLOAD_TOO_LARGE = 413;
    public final static int URI_TOO_LONG = 414;
    public final static int UNSUPPORTED_MEDIA_TYPE = 415;
    public final static int RANGE_NOT_SATISFIABLE = 416;
    public final static int EXPECTATION_FAILED = 417;
    public final static int IM_A_TEAPOT = 418;
    public final static int MISDIRECTED_REQUEST = 421;
    public final static int UNPROCESSABLE_ENTITY = 422;
    public final static int LOCKED = 423;
    public final static int FAILED_DEPENDENCY = 424;
    public final static int TOO_EARLY = 425;
    public final static int UPGRADE_REQUIRED = 426;
    public final static int PRECONDITION_REQUIRED = 428;
    public final static int TOO_MANY_REQUESTS = 429;
    public final static int REQUEST_HEADER_FIELDS_TOO_LARGE = 431;
    public final static int UNAVAILABLE_FOR_LEGAL_REASONS = 451;
    public final static int INTERNAL_SERVER_ERROR = 500;
    public final static int NOT_IMPLEMENTED = 501;
    public final static int BAD_GATEWAY = 502;
    public final static int SERVICE_UNAVAILABLE = 503;
    public final static int GATEWAY_TIMEOUT = 504;
    public final static int HTTP_VERSION_NOT_SUPPORTED = 505;
    public final static int VARIANT_ALSO_NEGOTIATES = 506;
    public final static int INSUFFICIENT_STORAGE = 507;
    public final static int LOOP_DETECTED = 508;
    public final static int NOT_EXTENDED = 510;
    public final static int NETWORK_AUTHENTICATION_REQUIRED = 511;

    private HTTPStatus() {
    }
}
