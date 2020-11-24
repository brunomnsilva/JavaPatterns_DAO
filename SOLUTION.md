Solutions of exercises
===

1. We can implement it as follows:

    ```java
    //...
    case "range":
        input = ask(keyboard, new String[]{"Start year", "End year"});
        try {
            Collection<Book> rangeResults = dao.getAllFromYearRange(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
            rangeResults.forEach(b -> System.out.println(b));
        } catch(NumberFormatException e) {
            System.out.println("[Invalid format for year]");
        }
        break;
    //...
    ```

2. Implementation of `BooDaoVolatileMap` can be as follows:

    ```java
    public class BookDaoVolatileMap implements BookDao {
    
        /* key -> ISBN | value -> Book*/
        private Map<String, Book> books;
    
        public BookDaoVolatileMap() {
            this.books = new HashMap<>();
        }
    
        @Override
        public Book get(String key) {
            return books.get(key);
        }
    
        @Override
        public Collection<Book> getAll() {
            /* return a shallow-copy list */
            return new ArrayList<>(books.values());
        }
    
        @Override
        public void save(Book instance) throws DaoException  {
            if(books.containsKey(instance.getIsbn())) throw new DaoException("A book already exists with ISBN " + instance.getIsbn());
    
            books.put(instance.getIsbn(), instance);
        }
    
        @Override
        public void update(Book instance) throws DaoException {
            if(!books.containsKey(instance.getIsbn())) throw new DaoException("No book exists with ISBN " + instance.getIsbn());
    
            /* update logic here: remove old and insert new */
            books.remove(instance.getIsbn());
            books.put(instance.getIsbn(), instance);
        }
    
        @Override
        public Book delete(String key) {
            /* Do not iterate collection and remove simultaneously */
            return books.remove(key);
        }
    
        @Override
        public int count() {
            return books.size();
        }
    
        @Override
        public Collection<Book> getAllFromAuthorSearch(String queryString) {
            List<Book> result = new ArrayList<>();
            for (Book book : books.values()) {
                if (book.getAuthor().toLowerCase().contains(queryString.toLowerCase())) {
                    result.add(book);
                }
            }
            return result;
        }
    
        @Override
        public Collection<Book> getAllFromYearRange(int yearStart, int yearEnd) {
            List<Book> result = new ArrayList<>();
            for (Book book : books.values()) {
                if (book.getYear() >= yearStart && book.getYear() <= yearEnd) {
                    result.add(book);
                }
            }
            return result;
        }
    }
    ``` 

3. The *simple factory*. Two creation methods are available; one uses the name of the *concrete dao*, the other an *enum type*. Only one is really needed.

    ```java
    public abstract class BookDaoFactory { /* abstract is not required by the pattern */
    
        public enum Type {
            MEMORYLIST,
            MEMORYMAP,
            SERIALIZATION,
            TEXT
        }
    
        /**
         * Returns the appropriate BookDao implementation based on <code>type</code> parameter.
         *
         * Currently accepted types are:
         * - "memorylist"
         * - "memorymap" 
         * - "serialization"
         * - "text"
         * @param type type of dao implementation to create
         * @return a concrete dao instance.
         */
        public static BookDao create(String type) {
            switch (type) {
                case "memorylist": return new BookDaoVolatileList();
                case "memorymap": return new BookDaoVolatileMap();
                case "serialization": return new BookDaoSerialization();
                case "text": return new BookDaoTextFiles();
                default: throw new UnsupportedOperationException("Invalid type: " + type);
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
                case MEMORYLIST: return new BookDaoVolatileList();
                case MEMORYMAP: return new BookDaoVolatileMap();
                case SERIALIZATION: return new BookDaoSerialization();
                case TEXT: return new BookDaoTextFiles();
                default: throw new UnsupportedOperationException("Invalid type: " + type.toString());
            }
        }
    }
    ```
    
    The `main` method requires only changes to the instantiation of the *concrete dao*:
    
    ```java
    BookDao dao = BookDaoFactory.create("serialization");
    //or: BookDao dao = BookDaoFactory.create(BookDaoFactory.Type.SERIALIZATION);
    
    //...
    ```
   
4. Implementation can be as follows:

    ```java
    import com.brunomnsilva.dao.DaoException;
    import com.google.gson.Gson;
    import com.google.gson.reflect.TypeToken;
    
    import java.io.*;
    import java.lang.reflect.Type;
    import java.util.ArrayList;
    
    public class BookDaoJSON extends BookDaoVolatileList {
    
        private static final String STORAGE_FILENAME = "storage/books.json";
    
        public BookDaoJSON() {
            super();
            readStorage();
        }
    
        private void saveStorage() {
            try {
                Gson gson = new Gson();
                Type bookListType = new TypeToken<ArrayList<Book>>(){}.getType();
                FileWriter fr = new FileWriter(STORAGE_FILENAME);
    
                String json = gson.toJson(this.books, bookListType);
    
                fr.write(json);
    
                fr.close();
    
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    
        private void readStorage() {
            try {
                Gson gson = new Gson();
                Type bookListType = new TypeToken<ArrayList<Book>>(){}.getType();
                FileReader fr = new FileReader(STORAGE_FILENAME);
    
                this.books = gson.fromJson(fr, bookListType);
    
                fr.close();
            } catch (IOException  e) {
                System.err.println(e.getMessage());
            }
        }
    
        @Override
        public void save(Book instance) throws DaoException {
            super.save(instance);
    
            saveStorage();
        }
    
        @Override
        public void update(Book instance) throws DaoException {
            super.update(instance);
    
            saveStorage();
        }
    
        @Override
        public Book delete(String key) {
            Book deleted = super.delete(key);
            saveStorage();
            return deleted;
        }
    }
    ```