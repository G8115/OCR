/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test5;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Ari
 */
public class test5 {

    BufferedImage bufferedImage = null;

    public static void main(String[] args) {
        test5 t=new test5();
    }
    public test5(){
        ProgramLogicInterface l = new ProgramLogicInterface();
        
       /* try {
            bufferedImage = ImageIO.read(new File("C:\\Users\\Ari\\Desktop\\Project\\OCRTest1\\build\\classes\\test1\\resource\\sudoku_1.JPG"));
        } catch (IOException ex) {

        }
        //crop the image as soon as possible
        bufferedImage = bufferedImage.getSubimage(800, 100, bufferedImage.getHeight(), bufferedImage.getHeight() - 100);
        l.solveFromImage(bufferedImage);
               */
        /*
        int[]temp= {0, 7, 0, 0, 0, 0, 0, 1, 5,
                    0, 3, 0, 2, 5, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 6, 0,
                    0, 0, 0, 0, 0, 5, 0, 0, 4,
                    0, 0, 2, 0, 0, 0, 8, 0, 0,
                    5, 0, 0, 6, 0, 0, 0, 0, 3,
                    0, 0, 0, 1, 4, 0, 7, 0, 0,
                    0, 6, 0, 7, 2, 3, 0, 0, 0,
                    0, 0, 4, 0, 0, 6, 0, 0, 0};
        l.solveFromList(temp);
        */
    }
}
