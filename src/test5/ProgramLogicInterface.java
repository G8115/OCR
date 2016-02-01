/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test5;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Ari Helps manage the use of all the components
 */
public class ProgramLogicInterface {

    public SudokuSolver solver;
    public Sudoku sudoku;
    public Image im;
    public ImageEnhancer enhancer;
    public OCR ocr;

    public ProgramLogicInterface() {
        solver = new SudokuSolver();
        sudoku = new Sudoku();
        im = null;
        enhancer = new ImageEnhancer();
        ocr = new OCR();
    }

    public void solveFromImage(BufferedImage i) {
        im = new Image(i);
        enhancer.ENHANCE(im);
        //writefile(im,"johanon");
        sudoku.setAllSudokuCells(ocr.getSudoku(im.getSubImages(), im.height, im.width));

        sudoku=solver.solve(sudoku);
        sudoku.PrintSudoku();//debug
    }
    public void solveFromList(int[] list) {
        sudoku.setAllSudokuCells(list);
        sudoku=solver.solve(sudoku);
        sudoku.PrintSudoku();//debug
       // solver.su.PrintSudoku();
    }
    public void readToSudoku(BufferedImage i){
        im = new Image(i);
        enhancer.ENHANCE(im);
        sudoku.setAllSudokuCells(ocr.getSudoku(im.getSubImages(), im.height, im.width));
    }
    private void writefile(Image img, String name) {
        BufferedImage temp = new BufferedImage(img.width , img.height ,BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < img.height; i++) {
            for (int j = 0; j < img.width; j++) {
                if (img.image[i][j]) {
                    temp.setRGB(j, i, Color.BLACK.getRGB());
                } else {
                    temp.setRGB(j, i, Color.WHITE.getRGB());
                }
            }
        }

        File outpt = new File(name+".jpg");
        try {
            ImageIO.write(temp, "jpg", outpt);
        } catch (IOException ex) {
        }
    }
}
