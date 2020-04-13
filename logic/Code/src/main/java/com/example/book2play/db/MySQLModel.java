package com.example.book2play.db;

import java.util.logging.Logger;

public class MySQLModel {

    final static protected Logger LOG = Logger.getLogger("DB_MODEL");
    protected MySQLServer db;

    public MySQLModel(MySQLServer db) {
        this.db = db;
    }
}
