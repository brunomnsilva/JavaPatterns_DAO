package com.brunomnsilva.domain;

/**
 * @author brunomnsilva
 */
public abstract class BookDaoFactory {

    public enum Type {
        MEMORY,
        SERIALIZATION,
        TEXT
    }

    /**
     * Returns the appropriate BookDao implementation based on <code>type</code> parameter.
     *
     * Currently accepted types are:
     * - "memory"
     * - "serialization"
     * - "text"
     * @param type type of dao implementation to create
     * @return a concrete dao instance.
     */
    public static BookDao create(String type) {
        switch (type) {
            case "memory": return new BookDaoInMemory();
            case "serialization": return new BookDaoSerialization();
            case "text": return new BookDaoTextFiles();
            default: throw new UnsupportedOperationException("Invalid type received.");
        }
    }

    /**
     * Returns the appropriate BookDao implementation based on <code>type</code> parameter.
     *
     * @param type type of dao implementation to create
     * @return a concrete dao instance.
     */
    public static BookDao create(Type type) {
        switch(type) {
            case MEMORY: return new BookDaoInMemory();
            case SERIALIZATION: return new BookDaoSerialization();
            case TEXT: return new BookDaoTextFiles();
            default: throw new UnsupportedOperationException("Invalid type received.");
        }
    }
}
