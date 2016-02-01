/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test5;

import java.util.ArrayList;

/**
 *
 * @author g8115
 */
public class SudokuCell {

    public boolean[] possibilities;
    public int numOfPossibilities;
    public int CellNumber;
    public ArrayList<SudokuCell> constraintTargets;

    //public constructor
    public SudokuCell() {
        possibilities = new boolean[9];
        resetSudokuCell();
    }
    public SudokuCell(SudokuCell c){
        possibilities = new boolean[9];
        for(int i=0;i<9;i++){
            this.possibilities[i]=c.possibilities[i];
        }
        this.CellNumber=c.CellNumber;
        this.numOfPossibilities=c.numOfPossibilities;
    }

    //method for resetting the cell
    public void resetSudokuCell() {
        for (int i = 0; i < 9; i++) {
            possibilities[i] = true;
        }
        numOfPossibilities = 9;
        CellNumber = 0;
    }

    //Used when initializing a new sudoku
    public void setSudokuCellValue(int sudokuCellValue) {
        for (int i = 0; i < 9; i++) {
            possibilities[i] = false;
        }
        possibilities[sudokuCellValue - 1] = true;
        numOfPossibilities = 1;
        CellNumber = sudokuCellValue;
    }

    //Used when applying constraint to cell
    public boolean removeValue(int notThis) {
        //check to see if there is any work to be done here
        if (numOfPossibilities != 1 && possibilities[notThis - 1]) {
            numOfPossibilities--;
            possibilities[notThis - 1] = false;
            //If there is only one possibility left return true
            if (numOfPossibilities == 1) {
                for (int i = 0; i < 9; i++) {
                    if (possibilities[i]) {
                        CellNumber = i + 1;
                    }
                }
                return true;
            }
        }
        return false;
    }

    //toString override
    @Override
    public String toString() {
        if (CellNumber != 0) {
            return Integer.toString(CellNumber);
        } else {
            return "_";
        }
    }

}
