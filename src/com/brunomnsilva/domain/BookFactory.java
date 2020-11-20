package com.brunomnsilva.domain;

/**
 * @author brunomnsilva
 */
public abstract class BookFactory {

    /**
     *
     * @param isbn
     * @param author
     * @param title
     * @param year
     * @return
     */
    public static Book createFromStrings(String isbn, String author, String title, String year) {
        /* We should perform value validations here, e.g., strings not null or empty */
        int yearValue = 0;
        try {
            yearValue = Integer.valueOf(year);
        } catch (NumberFormatException e) {
            yearValue = 0;
        }
        return new Book(isbn, author, title, yearValue);
    }

    /**
     *
     * @param contents
     * @return
     */
    public static Book createFromString(String contents) {
        /* We should perform value validations here, e.g., string not null or empty */
        String[] fields = contents.split(";");
        try {
            return new Book(fields[0],fields[1],fields[2],Integer.valueOf(fields[3]));
        } catch (NumberFormatException e) {
            return new Book(fields[0],fields[1],fields[2],0);
        }
    }
}
