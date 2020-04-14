package com.example.book2play.db.models;

import com.example.book2play.db.AppDataSource;

import java.util.logging.Logger;

class MySQLModel {

    final static protected Logger LOG = Logger.getLogger("DB_MODEL");
    protected AppDataSource db;

    public MySQLModel(AppDataSource db) {
        this.db = db;
    }
}
