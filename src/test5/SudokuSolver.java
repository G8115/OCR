/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test5;

import java.awt.Point;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

/**
 *
 * @author g8115
 */
public class SudokuSolver {

    private ArrayList<Sudoku> listOfGuessed = new ArrayList<>();
    //public Sudoku su=null;
    /*
     public static void main(String[] args) {
     // TODO code application logic here
     //test data, one can only hope that it is correct
     int[] s = {0, 0, 0, 2, 2, 0, 5, 0, 1,
     0, 0, 0, 6, 0, 0, 4, 0, 0,
     0, 0, 0, 0, 0, 8, 0, 0, 0,
     1, 6, 0, 0, 0, 0, 7, 0, 0,
     0, 4, 0, 0, 0, 9, 0, 1, 0,
     7, 5, 0, 1, 2, 0, 0, 4, 0,
     0, 0, 8, 0, 1, 0, 0, 5, 0,
     0, 0, 0, 9, 0, 7, 0, 0, 0,
     0, 7, 0, 5, 8, 0, 0, 9, 4};
     Sudoku sudoku = new Sudoku();
     sudoku.setAllSudokuCells(s);
     SudokuSolver SS = new SudokuSolver();
     SS.solve(sudoku).PrintSudoku();

     int ahnjio = 0;
     }
     */

    //main sudoku solving method
    public Sudoku solve(Sudoku s) {
        /*
         First first constraint
         then if not solved guess
         apply constraint
         see if solved
         if not guess
         if wrong go back one guess
         */
        //Constraint2(s);
        //int guessss = 0;
        boolean running = true;
        //int a=2;
        while (running) {
            System.out.println("========GUESS:" + listOfGuessed.size() + "========");
            Constraint1(s);
            int a = s.isSolved();
            System.out.println("=======SOLVED:" + a + "========");

            s.PrintSudoku();
            switch (s.isSolved()) {
                case 1://solved wrong
                    if (listOfGuessed.size() > 0) {
                        s = listOfGuessed.get(listOfGuessed.size() - 1);
                        listOfGuessed.remove(s);
                        //System.out.println(listOfGuessed.size());
                        //Constraint2(s);
                        //s.PrintSudoku();
                    } else {
                        running = false;
                    }
                    break;
                case 2://not solved
                    Guesser(s);
                    //s.PrintSudoku();
                    break;
                case 3://solved
                    running = false;
                    //su=new Sudoku(s);
                    break;
            }
        }
        return s;
    }

    public boolean Guesser(Sudoku s) {
        ArrayList<Integer> opportunities = new ArrayList<>();
        Sudoku tempSudoku;
        int minPossibilities = 9;
        Point minPossibilitiesPoint = new Point(100, 100);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (s.Sudoku[i][j].numOfPossibilities == 2) {
                    for (int k = 0; k < 9; k++) {
                        if (s.Sudoku[i][j].possibilities[k]) {
                            opportunities.add(k);
                        }
                    }
                    tempSudoku = new Sudoku(s);
                    s.Sudoku[i][j].setSudokuCellValue(opportunities.get(0) + 1);
                    tempSudoku.Sudoku[i][j].setSudokuCellValue(opportunities.get(1) + 1);
                    s.cellQueue.add(s.Sudoku[i][j]);
                    tempSudoku.cellQueue.add(tempSudoku.Sudoku[i][j]);
                    listOfGuessed.add(tempSudoku);
                    return true;
                } else if (s.Sudoku[i][j].numOfPossibilities > 2 && s.Sudoku[i][j].numOfPossibilities < minPossibilities) {
                    minPossibilities = s.Sudoku[i][j].numOfPossibilities;
                    minPossibilitiesPoint.x = j;
                    minPossibilitiesPoint.y = i;
                }
            }
        }
        if (minPossibilitiesPoint.x != 100) {
            int i = minPossibilitiesPoint.y;
            int j = minPossibilitiesPoint.x;

            for (int k = 0; k < 9; k++) {
                if (s.Sudoku[i][j].possibilities[k]) {
                    opportunities.add(k);
                }
            }

            int index = opportunities.size() - 1;
            for (int h = 0; h < index; h++) {
                tempSudoku = new Sudoku(s);
                tempSudoku.Sudoku[i][j].setSudokuCellValue(opportunities.get(h) + 1);
                tempSudoku.cellQueue.add(tempSudoku.Sudoku[i][j]);
                listOfGuessed.add(tempSudoku);
            }
            s.Sudoku[i][j].setSudokuCellValue(opportunities.get(index) + 1);
            s.cellQueue.add(s.Sudoku[i][j]);
        }
        return false;
    }

//No row, column a 3x3 grid can have the same number twice 
    public void Constraint1(Sudoku s) {
        SudokuCell tempCell;
        while (!s.cellQueue.isEmpty()) {
            tempCell = s.cellQueue.remove();
            for (SudokuCell su : tempCell.constraintTargets) {
                if (su.removeValue(tempCell.CellNumber)) {
                    s.cellQueue.add(su);
                }
            }
        }
    }

    //If only one cell in a row,column or a 3x3 grid has a specific value then that it is
    public int Constraint2(Sudoku s) {//might work and but not be used

        //init temporary container for the SudokuCells
        ArrayList<SudokuCell>[] a = new ArrayList[9];
        int returnValue = 0;
        //go through all the arraylists in checklist
        for (ArrayList<SudokuCell> t : s.checkList) {
            for (int i = 0; i < 9; i++) {
                a[i] = new ArrayList<>();
            }
            //go through the arraylist in checkList
            for (SudokuCell c : t) {
                // check to see if there is something to do
                if (c.numOfPossibilities > 1) //Go through the possible numbers of the cell    
                //and add the cell to the corresponding arraylist    
                {
                    for (int i = 0; i < 9; i++) {
                        if (c.possibilities[i]) {
                            a[i].add(c);
                        }
                    }
                }
            }
            //go through the array of arrylists
            for (int i = 0; i < 9; i++) {
                //check to see if the arraylist has only a single cell in it
                if (a[i].size() == 1) {
                    // because the cell was the only one with the spesific possible value
                    //the value is set as it's value
                    a[i].get(0).setSudokuCellValue(i + 1);
                    s.cellQueue.add(a[i].get(0));
                }
                a[i].clear();
            }
        }
        return returnValue;
    }

}
