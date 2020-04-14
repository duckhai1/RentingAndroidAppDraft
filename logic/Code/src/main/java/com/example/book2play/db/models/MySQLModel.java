package com.example.book2play.db.models;

import com.example.book2play.db.utils.DBManager;

import java.util.logging.Logger;

class MySQLModel {

    final static protected Logger LOG = Logger.getLogger("DB_MODEL");
    protected DBManager db;

    public MySQLModel(DBManager db) {
        this.db = db;
    }
}
