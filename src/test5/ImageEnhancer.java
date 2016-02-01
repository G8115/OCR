/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test5;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Ari
 */
public class ImageEnhancer {

    Image im;
    ArrayList<ArrayList<Point>> blackList;

    public void ENHANCE(Image image) {
        im = image;
        //tahan valiin kuvamanipulaatio
        //alkuun ohuiden pysty- ja vaakaviivojen poisto
        newVerticalLineRemover(7);
        newHorizontalLineRemover(7);

        //seuraavaksi pitkien pysty- ja vaakaviivojen poisto
        removeLongVerticalLines(100);
        removeLongHorizontalLines(80);

        //seuraavaksi kallein operaatio eli connectedBlackTester
        //connectedBlackTester(10);
        drawLines();

        connectedBlackTester(900);

        connectedWhiteTester(20);
        
        edgeTester(5);
        
        connectedBlackTester(900);
        //removeBigBlackClusters(5000);
        testForSurround();
        //}
    }

    private void newHorizontalLineRemover(int lineWidth) {
        //image[height][width];
        int tempH = im.height - lineWidth * 2 - 1;
        int tempW = im.width;
        int tempadd = 0;
        try {
            for (int i = 1; i < tempW; i++) {
                for (int j = 1; j < tempH;) {
                    if (!im.image[j][i]) {
                        for (int index1 = 2; index1 < lineWidth - 1; index1++) {
                            if (!im.image[j + index1][i]) {
                                for (int i2 = 0; i2 < index1; i2++) {
                                    im.image[j + i2][i] = false;
                                }
                                tempadd = index1 - 1;
                            }
                            j += tempadd;
                            tempadd = 0;
                        }
                    }
                    j++;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("nyt meni mönkään");
        }
    }

    private void newVerticalLineRemover(int lineWidth) {
        //image[height][width];
        int tempH = im.height;
        int tempW = im.width - lineWidth * 2 - 1;
        int tempadd = 0;

        try {
            for (int i = 1; i < tempH; i++) {
                for (int j = 1; j < tempW;) {
                    if (!im.image[i][j]) {
                        for (int index1 = 2; index1 < lineWidth - 1; index1++) {
                            if (!im.image[i][j + index1]) {
                                for (int i2 = 0; i2 < index1; i2++) {
                                    im.image[i][j + i2] = false;
                                }
                                tempadd = index1 - 1;
                            }
                            j += tempadd;
                            tempadd = 0;
                        }
                    }
                    j++;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("nyt meni mönkään");
        }
    }

    private void removeLongVerticalLines(int length) {
        //image[height][width];
        int tempH = im.height;
        int tempW = im.width;
        int temp = 0;
        int error1 = 0;
        try {
            for (int i = 1; i < tempW; i++) {
                for (int j = 1; j < tempH; j++) {
                    error1 = j;
                    //System.out.println(tempH+" > "+j);
                    while (im.image[j + temp][i] && j + temp + 1 < tempH) {
                        temp++;
                    }
                    if (temp > length) {
                        for (int index = j; index < temp + j; index++) {
                            im.image[index][i] = false;
                        }
                    }
                    j += temp;
                    temp = 0;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(error1 + " + " + temp);
        }
    }

    private void removeLongHorizontalLines(int length) {
        //image[height][width];
        int tempH = im.height;
        int tempW = im.width;
        int temp = 0;
        int error1 = 0;
        try {
            for (int i = 1; i < tempH; i++) {
                for (int j = 1; j < tempW; j++) {
                    //error1 = j;
                    //System.out.println(tempH+" > "+j);
                    while (im.image[i][j + temp] && j + temp + 1 < tempW) {
                        temp++;
                    }
                    if (temp > length) {
                        for (int index = j; index < temp + j; index++) {
                            im.image[i][index] = false;
                        }
                    }
                    j += temp;
                    temp = 0;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(error1 + " + " + temp + " = " + (temp + error1));
        }
    }

    private void connectedBlackTester(int size) {
        blackList = im.getConnectedBlacks();
        for (ArrayList<Point> a : blackList) {
            if (a.size() < size) {
                for (Point t : a) {
                    im.image[t.y][t.x] = false;
                }
            }
        }
    }

    private void connectedWhiteTester(int size) {
        ArrayList<ArrayList<Point>> tempList = im.getConnectedWhites();
        for (ArrayList<Point> a : tempList) {
            if (a.size() < size) {
                for (Point t : a) {
                    im.image[t.y][t.x] = true;
                }
            }
        }
    }

    private void removeBigBlackClusters(int size) {
        for (ArrayList<Point> a : blackList) {
            if (a.size() < size) {
                for (Point t : a) {
                    im.image[t.y][t.x] = false;
                }
            }
        }
    }

    public void drawLines() {
        for (int i = 0; i < im.width; i++) {
            im.image[0][i] = true;
            im.image[im.height - 1][i] = false;
        }
        for (int i = 0; i < im.height; i++) {
            im.image[i][0] = true;
            im.image[i][im.width - 1] = false;
        }
    }

    private void testForSurround() {
        for (int i = 1; i < im.height - 1; i++) {
            for (int j = 1; j < im.width - 1; j++) {
                if ((im.image[i - 1][j] && im.image[i + 1][j]) || (im.image[i][j - 1] && im.image[i][j + 1])) {
                    im.image[i][j] = true;
                }
            }
        }
    }
    
    private void edgeTester(int range){
        for(ArrayList<Point> l:blackList){
            boolean kill=false;
            for(Point p:l){
                if(p.y<range){
                    kill=true;
                }
            }
            if(kill){
                for(Point p:l){
                    im.image[p.y][p.x]=false;
                }
            }
        }
    }
}
