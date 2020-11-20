package com.brunomnsilva.domain;

import com.brunomnsilva.dao.Dao;

import java.util.Collection;

/**
 * @author brunomnsilva
 */
public abstract class BookDao implements Dao<Book, String> {

    /* Aditional operations besides CRUD (inherited): */

    public abstract Collection<Book> getAllFromAuthorSearch(String queryString);

    public abstract Collection<Book> getAllFromYearRange(int yearStart, int yearEnd);

}
