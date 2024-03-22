import javax.print.DocFlavor;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main{

    public static void main(String[] args) {
        String boardFile = args[0];
        String moveFile = args[1];
        Board(boardFile, moveFile);
    }
    public  static ArrayList<ArrayList<String>> Board(String boardFile, String moveFile) {

        String[] boardLines = readFile(boardFile);

        ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();

        for (String line : boardLines) {
            String[] splittedRow = line.split(" ");
            table.add(new ArrayList<String>(Arrays.asList(splittedRow)));
        }
        Play(table, moveFile);
        return table;
    }

    public  static ArrayList<ArrayList<String>> InitialBoard() {
        String[] boardLines = readFile("board.txt");

        ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();

        for (String line : boardLines) {
            String[] splittedRow = line.split(" ");
            table.add(new ArrayList<String>(Arrays.asList(splittedRow)));
        }

        return table;
    }

    public static String[] Moves(String moveFile){
        String[] moveLines = readFile(moveFile);
        String[] splittedMoves = new String[0];
        for (String line : moveLines) {
            splittedMoves = line.split(" ");
        }
        return splittedMoves;
    }

    public static int RowIndex(){
        String[] boardLines = readFile("board.txt");

        int i = 0;
        int playerRowIndex = 0;

        for (String line : boardLines) {
            if (line.contains("*")) {
                playerRowIndex = i;
            }
            i++;
        }

        return playerRowIndex;

    }

    public static int RowSize(){
        String[] boardLines = readFile("board.txt");

        int rowSize = boardLines.length;

        return rowSize;
    }

    public static int ColumnIndex(){
        String[] boardLines = readFile("board.txt");

        int playerColumnIndex = 0;

        for (String line : boardLines) {
            if (line.contains("*")) {
                playerColumnIndex = line.indexOf("*") / 2;
            }
        }

        return playerColumnIndex;
    }

    public static int ColumnSize(){
        String[] boardLines = readFile("board.txt");

        int columnSize = (boardLines[0].length() / 2) + 1;

        return columnSize;
    }

    public static String[] readFile(String path){
        try{
            int i = 0;
            int lenght = Files.readAllLines(Paths.get(path)).size();
            String[] results = new String[lenght];
            for (String line : Files.readAllLines(Paths.get(path))){
                results[i++] = line;
            }
            return  results;

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
    }

    public  static void Play(ArrayList<ArrayList<String>> table, String moveFile){
        int point = 0 ;
        int rowIndex = RowIndex();
        int columnIndex = ColumnIndex();
        boolean isFinished = false;
        String movement = "";
        ArrayList<ArrayList<String>> firstBoard = new ArrayList<ArrayList<String>>();
        firstBoard = table;
        for(int i = 0; i < Moves(moveFile).length; i++){
            int previousRowIndex = rowIndex;
            int previousColumnIndex = columnIndex;

            switch (Moves(moveFile)[i]){
                case "L":
                    movement += "L ";
                    columnIndex = (columnIndex - 1 + ColumnSize()) % ColumnSize();
                    if (!table.get(rowIndex).get(columnIndex).equals("W") & !table.get(rowIndex).get(columnIndex).equals("H")){
                        point += Point(table.get(rowIndex).get(columnIndex));
                        if(table.get(rowIndex).get(columnIndex).equals("R") || table.get(rowIndex).get(columnIndex).equals("Y") || table.get(rowIndex).get(columnIndex).equals("B")){
                            table.get(rowIndex).set(columnIndex+1, "X");
                        }else {
                            table.get(rowIndex).set(columnIndex+1, table.get(rowIndex).get(columnIndex));
                        }
                        table.get(rowIndex).set(columnIndex, "*");
                    } else if (table.get(rowIndex).get(columnIndex).equals("W")) {
                        columnIndex = (columnIndex + 2 + ColumnSize()) % ColumnSize();
                        point += Point(table.get(rowIndex).get(columnIndex));
                        if(table.get(rowIndex).get(columnIndex).equals("R") || table.get(rowIndex).get(columnIndex).equals("Y") || table.get(rowIndex).get(columnIndex).equals("B")){
                            table.get(rowIndex).set(columnIndex-1, "X");
                        }else {
                            table.get(rowIndex).set(columnIndex-1, table.get(rowIndex).get(columnIndex));
                        }
                        table.get(rowIndex).set(columnIndex, "*");
                    } else if (table.get(rowIndex).get(columnIndex).equals("H")) {
                        table.get(rowIndex).set(previousColumnIndex, " ");
                        isFinished = true;
                    }

                    break;
                case "R":
                    movement += "R ";
                    columnIndex = (columnIndex + 1 + ColumnSize()) % ColumnSize();
                    //System.out.println(table.get(rowIndex).get(columnIndex));
                    if (!table.get(rowIndex).get(columnIndex).equals("W") & !table.get(rowIndex).get(columnIndex).equals("H")){
                        point += Point(table.get(rowIndex).get(columnIndex));
                        if(table.get(rowIndex).get(columnIndex).equals("R") || table.get(rowIndex).get(columnIndex).equals("Y") || table.get(rowIndex).get(columnIndex).equals("B")){
                            table.get(rowIndex).set(columnIndex-1, "X");
                        }else {
                            table.get(rowIndex).set(columnIndex-1, table.get(rowIndex).get(columnIndex));
                        }
                        table.get(rowIndex).set(columnIndex, "*");
                    } else if (table.get(rowIndex).get(columnIndex).equals("W")) {
                        columnIndex = (columnIndex - 2 + ColumnSize()) % ColumnSize();
                        point += Point(table.get(rowIndex).get(columnIndex));
                        if(table.get(rowIndex).get(columnIndex).equals("R") || table.get(rowIndex).get(columnIndex).equals("Y") || table.get(rowIndex).get(columnIndex).equals("B")){
                            table.get(rowIndex).set(columnIndex+1, "X");
                        }else {
                            table.get(rowIndex).set(columnIndex+1, table.get(rowIndex).get(columnIndex));
                        }
                        table.get(rowIndex).set(columnIndex, "*");
                    } else if (table.get(rowIndex).get(columnIndex).equals("H")) {
                        table.get(rowIndex).set(previousColumnIndex, " ");
                        isFinished = true;
                    }

                    break;
                case "U":
                    movement += "U ";
                    rowIndex = (rowIndex - 1 + RowSize()) % RowSize();
                    if (!table.get(rowIndex).get(columnIndex).equals("W") & !table.get(rowIndex).get(columnIndex).equals("H")){
                        point += Point(table.get(rowIndex).get(columnIndex));
                        if(table.get(rowIndex).get(columnIndex).equals("R") || table.get(rowIndex).get(columnIndex).equals("Y") || table.get(rowIndex).get(columnIndex).equals("B")){
                            table.get(rowIndex+1).set(columnIndex, "X");
                        }else {
                            table.get(rowIndex+1).set(columnIndex, table.get(rowIndex).get(columnIndex));
                        }
                        table.get(rowIndex).set(columnIndex, "*");
                    } else if (table.get(rowIndex).get(columnIndex).equals("W")) {
                        rowIndex = (rowIndex + 2 + RowSize()) % RowSize();;
                        point += Point(table.get(rowIndex).get(columnIndex));
                        if(table.get(rowIndex).get(columnIndex).equals("R") || table.get(rowIndex).get(columnIndex).equals("Y") || table.get(rowIndex).get(columnIndex).equals("B")){
                            table.get(rowIndex-1).set(columnIndex, "X");
                        }else {
                            table.get(rowIndex-1).set(columnIndex, table.get(rowIndex).get(columnIndex));
                        }
                        table.get(rowIndex).set(columnIndex, "*");
                    } else if (table.get(rowIndex).get(columnIndex).equals("H")) {
                        table.get(previousRowIndex).set(columnIndex, " ");
                        isFinished = true;
                    }

                    break;
                case "D":
                    movement += "D ";
                    rowIndex = (rowIndex + 1 + RowSize()) % RowSize();
                    if (!table.get(rowIndex).get(columnIndex).equals("W") & !table.get(rowIndex).get(columnIndex).equals("H")){
                        point += Point(table.get(rowIndex).get(columnIndex));
                        if(table.get(rowIndex).get(columnIndex).equals("R") || table.get(rowIndex).get(columnIndex).equals("Y") || table.get(rowIndex).get(columnIndex).equals("B")){
                            table.get(rowIndex-1).set(columnIndex, "X");
                        }else {
                            table.get(rowIndex-1).set(columnIndex, table.get(rowIndex).get(columnIndex));
                        }
                        table.get(rowIndex).set(columnIndex, "*");
                    } else if (table.get(rowIndex).get(columnIndex).equals("W")) {
                        rowIndex = (rowIndex - 2 + RowSize()) % RowSize();
                        point += Point(table.get(rowIndex).get(columnIndex));
                        if(table.get(rowIndex).get(columnIndex).equals("R") || table.get(rowIndex).get(columnIndex).equals("Y") || table.get(rowIndex).get(columnIndex).equals("B")){
                            table.get(rowIndex+1).set(columnIndex, "X");
                        }else {
                            table.get(rowIndex+1).set(columnIndex, table.get(rowIndex).get(columnIndex));
                        }                        table.get(rowIndex).set(columnIndex, "*");
                    } else if (table.get(rowIndex).get(columnIndex).equals("H")) {
                        table.get(previousRowIndex).set(columnIndex, " ");
                        isFinished = true;
                    }

                    break;
            }
            
            if (isFinished){
                WriteToFile(InitialBoard(),table, movement, point);
                break;
            }

        }

        WriteToFile(InitialBoard(),table, movement, point);

    }

    public static int Point(String color){
        int point = 0;
        switch (color){
            case "R":
                point = 10;
                break;
            case "Y":
                point = 5;
                break;
            case "B":
                point = -5;
                break;
        }
        return point;
    }

    public static void WriteToFile(ArrayList<ArrayList<String>> firstBoard ,ArrayList<ArrayList<String>> table, String movement, int point){
        String output = "Game Board:\n";

        for(int i = 0; i < RowSize(); i++){
            for(int j = 0; j < ColumnSize(); j++){
                output += firstBoard.get(i).get(j) + " ";
            }
            output += "\n";
        }
        output += "\n";

        output += "Your movement is:\n" + movement + "\n\nYour output is:\n";


        for(int i = 0; i < RowSize(); i++){
            for(int j = 0; j < ColumnSize(); j++){
                output += table.get(i).get(j) + " ";
            }
            output += "\n";
        }
        output += "\nGame Over!\nScore: "+ Integer.toString(point);


        try {
            File myFile = new File("output.txt");
            FileWriter writer = new FileWriter("output.txt");
            writer.write(output);
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}