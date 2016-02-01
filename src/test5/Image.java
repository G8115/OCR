/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test5;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Ari
 */
public class Image {

    public int width, height;

    public boolean image[][];
    public boolean visited[][];

    public int tolerance = 100;

    public int name = 0;
    public Point coordinates;

    //constructor to create an empty image
    public Image(int w, int h) {
        width = w;
        height = h;

        image = new boolean[height][width];
        visited = new boolean[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                image[i][j] = false;
                visited[i][j] = false;
            }
        }
    }

    //constructor for creating image from a bufferedimage
    public Image(BufferedImage/*Bitmap*/ im) {
        width = im.getHeight();
        height = im.getWidth();

        image = new boolean[height][width];
        visited = new boolean[height][width];

        for (int i = 1; i < height; i++) {
            for (int j = 1; j < width; j++) {
                //getrgb(int x height,int y width)
                //System.out.println(height-i +" < "+height);
                Color c =/*im.getPixel(i, width - j)*/ new Color(im.getRGB(i, width - j));
                image[i][j] = isBlack(c);
                visited[i][j] = false;
            }
        }

    }

    //wrapper for color check
    public boolean isBlack(Color c) {
        return c.getRed() < tolerance && c.getBlue() < tolerance && c.getGreen() < tolerance;
    }

    public void nullVisited() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                visited[i][j] = false;
            }
        }
    }

    public ArrayList<ArrayList<Point>> getConnectedBlacks() {
        ArrayList<ArrayList<Point>> templist = new ArrayList<>();
        Queue<Point> q = new LinkedList<>();
        Point piste;
        ArrayList<Point> l1 = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (image[j][i] && !visited[j][i]) {

                    q.add(new Point(i, j));
                    while (!q.isEmpty()) {
                        piste = q.remove();
                        l1.add(new Point(piste));
                        q.addAll(surroundingBlackPoints(piste));
                    }
                    if (l1.size() == 1) {
                        image[l1.get(0).y][l1.get(0).x] = false;
                    } else {
                        templist.add(l1);
                    }
                    l1 = new ArrayList<>();
                }
            }
        }
        this.nullVisited();
        return templist;
    }

    public ArrayList<ArrayList<Point>> getConnectedWhites() {
        ArrayList<ArrayList<Point>> templist = new ArrayList<>();
        Queue<Point> q = new LinkedList<>();
        Point piste;
        ArrayList<Point> l1 = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (!image[j][i] && !visited[j][i]) {

                    q.add(new Point(i, j));
                    while (!q.isEmpty()) {
                        piste = q.remove();
                        l1.add(new Point(piste));
                        q.addAll(surroundingWhitePoints(piste));
                    }
                    if (l1.size() == 1) {
                    } else {
                        templist.add(l1);
                    }
                    l1 = new ArrayList<>();
                }
            }
        }
        this.nullVisited();
        return templist;
    }

    public ArrayList<Point> surroundingBlackPoints(Point t) {
        //image[height][width];
        ArrayList<Point> s = new ArrayList();
        s.clear();
        if ((t.x > 1) && (t.x < (width - 1)) && (t.y > 1) && (t.y < (height - 1))) {

            if (image[t.y][t.x - 1] && !visited[t.y][t.x - 1]) {
                s.add(new Point(t.x - 1, t.y));
                visited[t.y][t.x - 1] = true;
            }
            if (image[t.y][t.x + 1] && !visited[t.y][t.x + 1]) {
                s.add(new Point(t.x + 1, t.y));
                visited[t.y][t.x + 1] = true;
            }
            if (image[t.y - 1][t.x] && !visited[t.y - 1][t.x]) {
                s.add(new Point(t.x, t.y - 1));
                visited[t.y - 1][t.x] = true;
            }
            if (image[t.y + 1][t.x] && !visited[t.y + 1][t.x]) {
                s.add(new Point(t.x, t.y + 1));
                visited[t.y + 1][t.x] = true;
            }
            visited[t.y][t.x] = true;
        }
        return s;
    }

    public ArrayList<Point> surroundingWhitePoints(Point t) {
        //image[height][width];
        ArrayList<Point> s = new ArrayList();
        s.clear();
        if ((t.x > 1) && (t.x < (width - 1)) && (t.y > 1) && (t.y < (height - 1))) {

            if (!image[t.y][t.x - 1] && !visited[t.y][t.x - 1]) {
                visited[t.y][t.x - 1] = true;
                s.add(new Point(t.x - 1, t.y));
            }
            if (!image[t.y][t.x + 1] && !visited[t.y][t.x + 1]) {
                visited[t.y][t.x + 1] = true;
                s.add(new Point(t.x + 1, t.y));
            }
            if (!image[t.y - 1][t.x] && !visited[t.y - 1][t.x]) {
                visited[t.y - 1][t.x] = true;
                s.add(new Point(t.x, t.y - 1));
            }
            if (!image[t.y + 1][t.x] && !visited[t.y + 1][t.x]) {
                visited[t.y + 1][t.x] = true;
                s.add(new Point(t.x, t.y + 1));
            }
            visited[t.y][t.x] = true;
        }
        return s;
    }

    public ArrayList<Image> getSubImages() {
        ArrayList<ArrayList<Point>> templist = getConnectedBlacks();
        ArrayList<Image> subImages = new ArrayList<>();
        int xmax;
        int xmin;
        int ymax;
        int ymin;
        for (ArrayList<Point> l : templist) {
            xmax = 1;
            xmin = width - 1;
            ymax = 1;
            ymin = height - 1;
            //System.out.println(l.size());
            for (Point p : l) {
                if (p.x < xmin) {
                    xmin = p.x;
                } else if (p.x > xmax) {
                    xmax = p.x;
                }
                if (p.y < ymin) {
                    ymin = p.y;
                } else if (p.y > ymax) {
                    ymax = p.y;
                }
            }
            //if(xmin>0&&xmax<width&&ymin>0&&ymax<height)
            subImages.add(getSubImage(xmin - 4, ymin - 4, xmax + 8, ymax + 8));
        }
        return subImages;
    }

    public Image getSubImage(int xmin, int ymin, int xmax, int ymax) {
        //image[y][x]
        //System.out.println("xmin " + xmin + " xmax " + xmax + " ymin " + ymin + " ymax" + ymax);
        Image temp = new Image(xmax - xmin, ymax - ymin);
        temp.coordinates = new Point((xmax + xmin) / 2, (ymax + ymin) / 2);
        for (int i = 0; i < ymax - ymin; i++) {
            for (int j = 0; j < xmax - xmin; j++) {
                if ((i + ymin) < height && (i + ymin) >= 0 && (j + xmin) < width && (j + xmin) >= 0) {
                    temp.image[i][j] = this.image[i + ymin][j + xmin];
                }
            }
        }
        return temp;
    }

    public void debugImagee() {
        System.out.println("NAME: "+name);
        System.out.println("COORDINATES: "+coordinates);
        System.out.println("012345678901234567890123456789012345678901234567890123456789012345678901234567890");
        for (int j = 0; j < height; j++) {
            System.out.print(j);
            for (int i = 0; i < width; i++) {
                if (image[j][i]) {
                    System.out.print("X");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
