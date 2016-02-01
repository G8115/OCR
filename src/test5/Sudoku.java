/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test5;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Ari
 */
public class Sudoku {

    public SudokuCell[][] Sudoku = new SudokuCell[9][9];
    public ArrayList<ArrayList<SudokuCell>> checkList = new ArrayList<>();
    public Queue<SudokuCell> cellQueue = new LinkedList<>();

    public Sudoku() {
        for (int a = 0; a < 9; a++) {
            for (int b = 0; b < 9; b++) {
                Sudoku[a][b] = new SudokuCell();
            }
        }
        fillCheckList();
        setSudokuCellConstraintTargets();
    }

    //copy constructor
    public Sudoku(Sudoku s) {
        for (int a = 0; a < 9; a++) {
            for (int b = 0; b < 9; b++) {
                Sudoku[a][b] = new SudokuCell(s.Sudoku[a][b]);
            }
        }
        fillCheckList();
        setSudokuCellConstraintTargets();
    }

    //check to see if the sudoku is solved
    public int isSolved() {
        //returns 3 if solved, 2 if not and 1 if wrong
        int[] allNumbers;
        boolean notSolved = false;
        for (ArrayList<SudokuCell> l : checkList) {
            allNumbers = new int[10];
            for (SudokuCell c : l) {
                allNumbers[c.CellNumber]++;
            }
            for (int i = 1; i < 10; i++) {
                if (allNumbers[i] > 1) {
                    return 1;
                }
            }
            if (allNumbers[0] > 0) {
                notSolved = true;
            }
        }
        if (notSolved) {
            return 2;
        } else {
            return 3;
        }
    }

    //debugmethod 1
    private void debug1(ArrayList<SudokuCell> s) {
        System.out.println();
        for (SudokuCell c : s) {
            System.out.print(c);
        }
        System.out.println();
    }

    // method for manipulating a single cell
    public void setCellValue(int x, int y, int value) {
        //SUDOKU[y][x]
        if (value == 0) {
            Sudoku[y][x].resetSudokuCell();
            cellQueue.remove(Sudoku[y][x]);
        } else {
            Sudoku[y][x].setSudokuCellValue(value);
            cellQueue.add(Sudoku[y][x]);
        }
    }

    //method for setting the entire sudoku all at once
    public void setAllSudokuCells(int[] values) {
        //SUDOKU[y][x]
        cellQueue = new LinkedList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                Sudoku[i][j].resetSudokuCell();
                if (values[j + i * 9] != 0) {
                    Sudoku[i][j].setSudokuCellValue(values[j + i * 9]);
                    cellQueue.add(Sudoku[i][j]);
                }
            }
        }
    }

    //method for filling each sudokus constraintTargets array
    private void setSudokuCellConstraintTargets() {
        ArrayList<SudokuCell> temp = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (ArrayList a : checkList) {
                    if (a.contains(Sudoku[i][j])) {
                        //remove duplicates
                        temp.removeAll(a);
                        //add everything
                        temp.addAll(a);
                    }
                }
                temp.remove(Sudoku[i][j]);
                Sudoku[i][j].constraintTargets = temp;
                for (int x = 0; x < 9; x++) {
                    for (int y = 0; y < 9; y++) {
                        if (temp.contains(Sudoku[x][y])) {
                            //System.out.print("X");
                        } else {
                            //System.out.print("_");
                        }
                    }
                    //System.out.println();
                }
                //System.out.println();

                temp = new ArrayList<>();
            }
        }
    }

    //method for filling checkList with objects from Sudoku
    private void fillCheckList() {
        ArrayList<SudokuCell> temp1 = new ArrayList<>();
        ArrayList<SudokuCell> temp2 = new ArrayList<>();
        //add rows and columns seperately
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                temp1.add(Sudoku[i][j]);
                temp2.add(Sudoku[j][i]);
                //System.out.print(i + "," + j + "|");
            }
            //System.out.println();
            checkList.add(temp1);
            checkList.add(temp2);
            temp2 = new ArrayList<>();
            temp1 = new ArrayList<>();

        }
        //System.out.println();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //System.out.print(j + "," + i + "|");
            }
            //System.out.println();
        }
        //System.out.println();

        //add the 3x3 parts of a sudoku
        for (int i = 0; i < 8; i += 3) {
            for (int j = 0; j < 8; j += 3) {
                temp1 = new ArrayList<>();
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        //System.out.print((i + k) + "," + (j + l) + "|");
                        temp1.add(Sudoku[i + k][j + l]);
                    }
                    //System.out.println();
                }
                //System.out.println();
                checkList.add(temp1);
            }
        }
    }

    //used for debugging
    public void PrintSudoku() {
        //System.out.println();
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) {
                System.out.println();
            }
            for (int j = 0; j < 9; j++) {

                if (j % 3 == 0) {
                    System.out.print(" ");
                }
                System.out.print(Sudoku[i][j]);
            }
            System.out.println();
        }
        System.out.println();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(Sudoku[i][j].numOfPossibilities);
            }
            System.out.println();
        }

    }
}
