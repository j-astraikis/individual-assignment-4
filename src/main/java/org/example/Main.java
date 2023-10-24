package org.example;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        mainMenu();
    }

    /**
     * displays main menu
     */
    static void mainMenu() {
        System.out.println("Notes\n");
        System.out.println("1. New note");
        System.out.println("2. Edit note");
        System.out.println("3. Delete note");
        System.out.println("4. View notes");
        System.out.println("5. Quit\n");

        Scanner in = new Scanner(System.in);
        System.out.print("Enter: ");
        int commandNum = in.nextInt();

        switch(commandNum) {
            case 1:
                newNoteMenu();
                break;
            case 2:
                editNoteMenu();
                break;
            case 3:
                deleteNoteMenu();
                break;
            case 4:
                viewNotes();
                break;
            case 5:
                System.exit(0);
            default:
                System.out.println("Enter a number between 1-5");
                mainMenu();
        }

        in.close();
    }

    /**
     * displays new note menu
     * @throws IOException if unable to write note to file
     */
    static void newNoteMenu() {
        System.out.println("\nNew note\n");

        // get title and body
        Scanner in = new Scanner(System.in);
        System.out.print("Title: ");
        String noteTitle = in.nextLine();
        System.out.print("Body: ");
        String noteBody = in.nextLine();

        String notePath = "./notes/" + noteTitle + ".txt";
        File note = new File(notePath);

        if (note.exists()) {
            System.out.print("\nNote with this title already exists. Override existing note? [y/n]: ");
            String overrideStr = in.nextLine();

            while (!overrideStr.equals("y") && !overrideStr.equals("n")) {
                System.out.print("Override existing note? [y/n]: ");
                overrideStr = in.nextLine();
            }

            if (overrideStr.equals("y")) {
                note.delete();
                // write note to file
                try {
                    note.createNewFile();
                    FileWriter writer = new FileWriter(notePath);
                    writer.write(noteBody);
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Note successfully saved.");
            }

            System.out.println("");
            mainMenu();
            return;
        }

        // write note to file
        try {
            note.createNewFile();
            FileWriter writer = new FileWriter(notePath);
            writer.write(noteBody);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Note successfully saved.");
        System.out.println("");
        mainMenu();
    }

    /**
     * displays edit note menu
     * @throws FileNotFoundException if note can't be read
     * @throws IOException if note can't be written to
     */
    static void editNoteMenu() {
        System.out.println("\nEdit note\n");

        // get note title
        Scanner in = new Scanner(System.in);
        System.out.print("Note title: ");
        String noteTitle = in.nextLine();

        String notePath = "./notes/" + noteTitle + ".txt";
        File note = new File(notePath);

        if(!note.exists()) {
            System.out.print("No note with this name. Create note? [y/n]: ");
            String createNoteStr = in.nextLine();

            while (!createNoteStr.equals("y") && !createNoteStr.equals("n")) {
                System.out.print("Create note? [y/n]: ");
                createNoteStr = in.nextLine();
            }

            if (createNoteStr.equals("y")) {
                newNoteMenu();
            } else {
                System.out.println("");
                mainMenu();
            }

            return;
        }

        // print note body
        try {
            Scanner noteScanner = new Scanner(note);
            System.out.println("\nNote body: " + noteScanner.nextLine());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // get new note body
        System.out.print("New note body: ");
        String newNoteBody = in.nextLine();

        // write note to file
        try {
            note.delete();
            note.createNewFile();
            FileWriter writer = new FileWriter(notePath);
            writer.write(newNoteBody);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Note successfully saved.");
        System.out.println("");
        mainMenu();
    }

    /**
     * displays delete note menu
     */
    static void deleteNoteMenu() {
        System.out.println("\nDelete note\n");

        // get note title
        Scanner in = new Scanner(System.in);
        System.out.print("Note title: ");
        String noteTitle = in.nextLine();

        String notePath = "./notes/" + noteTitle + ".txt";
        File note = new File(notePath);

        if (note.exists()) {
            note.delete();
            System.out.println("Note successfully deleted");
        } else {
            System.out.println("No note with this name.");
        }

        System.out.println("");
        mainMenu();
    }

    /**
     * displays all notes
     * @throws FileNotFoundException if note cannot be read
     */
    static void viewNotes() {
        System.out.println("\nView notes");
        File notesDir = new File("./notes/");
        File[] notes = notesDir.listFiles();

        // print notes
        for (File note: notes) {
            System.out.println("\n----------");
            System.out.println(note.getName() + "\n");

            try {
                Scanner noteScanner = new Scanner(note);
                System.out.println(noteScanner.nextLine());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            System.out.println("----------");
        }

        System.out.println("");
        mainMenu();
    }
}