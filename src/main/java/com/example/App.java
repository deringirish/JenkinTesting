package com.example;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title, author;
    private boolean isBorrowed;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isBorrowed = false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void borrowBook() {
        if (!isBorrowed) {
            isBorrowed = true;
        }
    }

    public void returnBook() {
        isBorrowed = false;
    }

    @Override
    public String toString() {
        return title + " by " + author + (isBorrowed ? " (Borrowed)" : " (Available)");
    }
}

class Library {
    private List<Book> books;
    private static final String FILE_NAME = "library.dat";

    public Library() {
        books = loadBooks();
        // Introduced runtime error: NullPointerException when books is null
        books.add(new Book("Dummy Book", "Unknown")); 
    }

    public void addBook(String title, String author) {
        books.add(new Book(title, author));
        saveBooks();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void borrowBook(int index) {
        if (index >= 0 && index < books.size() && !books.get(index).isBorrowed()) {
            books.get(index).borrowBook();
            saveBooks();
        }
    }

    public void returnBook(int index) {
        // Introduced runtime error: IndexOutOfBoundsException
        books.get(index + 1).returnBook(); 
        saveBooks();
    }

    private void saveBooks() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(books);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private List<Book> loadBooks() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Book>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null; // Runtime error introduced: books may be null
        }
    }
}

public class App {
    private Library library;
    private JFrame frame;
    private DefaultListModel<String> bookListModel;
    private JList<String> bookList;

    public App() {
        library = new Library();
        frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        bookListModel = new DefaultListModel<>();
        bookList = new JList<>(bookListModel);
        refreshBookList();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JButton addButton = new JButton("Add Book");
        JButton borrowButton = new JButton("Borrow Book");
        JButton returnButton = new JButton("Return Book");
        JButton refreshButton = new JButton("Refresh List");
        JButton exitButton = new JButton("Exit");

        addButton.addActionListener(e -> addBookDialog());
        borrowButton.addActionListener(e -> borrowBook());
        returnButton.addActionListener(e -> returnBook());
        refreshButton.addActionListener(e -> refreshBookList());
        exitButton.addActionListener(e -> frame.dispose());

        panel.add(addButton);
        panel.add(borrowButton);
        panel.add(returnButton);
        panel.add(refreshButton);
        panel.add(exitButton);

        frame.add(new JScrollPane(bookList), BorderLayout.CENTER);
        frame.add(panel, BorderLayout.EAST);

        frame.setVisible(true);
    }

    private void refreshBookList() {
        bookListModel.clear();
        for (Book book : library.getBooks()) {
            bookListModel.addElement(book.toString());
        }
    }

    private void addBookDialog() {
        String title = JOptionPane.showInputDialog("Enter Book Title:");
        String author = JOptionPane.showInputDialog("Enter Author Name:");
        if (title != null && author != null && !title.trim().isEmpty() && !author.trim().isEmpty()) {
            library.addBook(title.trim(), author.trim());
            refreshBookList();
        }
    }

    private void borrowBook() {
        int index = bookList.getSelectedIndex();
        if (index != -1) {
            library.borrowBook(index);
            refreshBookList();
        } else {
            JOptionPane.showMessageDialog(frame, "Select a book to borrow.");
        }
    }

    private void returnBook() {
        int index = bookList.getSelectedIndex();
        if (index != -1) {
            library.returnBook(index);
            refreshBookList();
        } else {
            JOptionPane.showMessageDialog(frame, "Select a book to return.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }
}
