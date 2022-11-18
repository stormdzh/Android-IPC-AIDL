// BookManager.aidl
package com.lypeer.ipcclient;
import com.lypeer.ipcclient.Book;

interface BookManager {

    List<Book> getBooks();
    void addBook(inout Book book);
}
