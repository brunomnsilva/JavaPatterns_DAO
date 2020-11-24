Java Patterns | Data Access Object
===

The **data access object** is an arquitectural design pattern. It proposes a layered arquitecture,
promoting decoupling of classes containing *business logic* from *data access* and corresponding persistence mechanisms:

![](dao_layers.png)



## Exercises

1. Implement the "range" command in the `CLI` method:

    ```java
    ///...
    case "range":
        /* TODO: implement this command */
        System.out.println("[Not implemented]"); 
        break;
    //...
    ```

    - The commands retrives the information from the *dao* using the method:
    `getAllFromYearRange(int yearStart, int yearEnd)`

2. Provide a *concrete BookDAO* implementation based on volatile storage, using an underlying collection of type `Map<String, Book>`.
    - Name it `BookDaoVolatileMap`;
    - The *map key* should correspond to the book's ISBN;
    - This will be similar to `BookDaoVolatileList`, but a little easier to implement.
    - Change the `main` method to use this *concrete dao*. Everything should work as previously.

3. Apply the **Simple Factory** pattern to allow requesting the different available storage strategies.
    - Pattern *participants*:
        - Factory: `BookDaoFactory`
        - Product: `BookDao`
        - Concrete Products: `BookDaoSerialization`, `BookDaoVolatileTextFiles`, `BookDaoVolatileList` and `BookDaoVolatileMap`
        
    - Change the `main` method to use the factory.

4. Provide a *concrete BookDao* implementation that persists data in *JSON* format.
    - Name it `BookDaoJSON`;
    - Download *GSON jar* library (put it in `/lib`) from https://search.maven.org/artifact/com.google.code.gson/gson/2.8.6/jar
    - Similar implementation to `BookDaoSerialization`, but the `saveStorage` and `readStorage` private methods use the *GSON* library;
    - Use the following tutorial:
        - https://futurestud.io/tutorials/gson-mapping-of-arrays-and-lists-of-objects

Solutions can be found [here](SOLUTION.md).