package com.example.book2play.db.models;

import com.example.book2play.db.AppDataSource;

import java.util.logging.Logger;

abstract class AbstractModel {

    final static protected Logger LOG = Logger.getLogger("DB_MODEL");
    protected AppDataSource db;

    public AbstractModel(AppDataSource db) {
        this.db = db;
    }
}
