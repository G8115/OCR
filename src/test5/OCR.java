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
 * @author Ari 3 extra white lines from the top 6 extra white lines from the
 * bottom
 */
public class OCR {

    Image im;
    ArrayList<ArrayList<Point>> connectedWhites;
    int errorCOunt = 0;

    public void detect(Image i) {
        im = i;
        //im.debugImagee();
        im.getConnectedBlacks();
        connectedWhites = im.getConnectedWhites();
        int a = connectedWhites.size();
        //System.out.println(a);
        switch (a) {
            case 1:
                //im.name = "123457";
                oneTest();
                break;
            case 2:
                if (sixOrNine()) {
                    im.name = 9;
                } else {
                    im.name = 6;
                }
                break;
            case 3:

                //if (testEight(200)) {
                i.name = 8;
                //} else {
                //    i.name = 0;
                //}

                break;
        }

    }

    public int[] getSudoku(ArrayList<Image> numbers, int height, int width) {
        for (Image i : numbers) {
            detect(i);
        }
        System.out.println(errorCOunt);

        int[] temp = new int[81];
        int heightModifier = height / 9;
        int widthModifier = width / 9;
        for (int i = 0; i < 81; i++) {
            temp[i] = 0;
        }
        for (Image i : numbers) {
            int x = i.coordinates.x / widthModifier;
            int y = i.coordinates.y / heightModifier;
            temp[y * 9 + x] = i.name;
        }
        return temp;
    }

    private boolean oneTest() {
        boolean is = true;
        //remove extra white lines from the bottom
        int height = im.height - 6;

        int firstStop = height - 8;
        int secondStop = height - height / 4;

        //Read first section of the image from bottom to top
        ArrayList<ArrayList<Integer>> temp = this.readSectionY(height, firstStop);
        int testInt = 0;
        //check to see if there is more than one line of black
        for (ArrayList<Integer> l : temp) {
            if (l.size() > 1) {
                testInt++;
            }
        }
        //if all slices had more than one line of black the image is bad and will be ignored
        if (testInt == temp.size()) {
            im.name = 0;
            return false;
        }
        //read the next section
        temp.addAll(this.readSectionY(firstStop, secondStop));
        //filter out all garbage data to a point
        int notTooFar = 5;
        ArrayList<Integer> indexList = new ArrayList<>();
        //list good indexes

        //test the listed length and its behaviour
        switch (behaviourTestOne(temp, 10)) {
            case 1:
                // test for 1 or 2
                //mayby read from side to side as two has max three lines and one only ever has max two lines
                if (oneOrTwo()) {
                    im.name = 2;
                } else {
                    im.name = 1;
                }
                break;
            case 2:
                // test for 4 or 7
                //mayby read from middle to 1/4 f the top as 4 goes one the two permanently and 7 goes one mayby two but returns to one shortly
                if (fourOrSeven()) {
                    im.name = 7;
                } else {
                    im.name = 4;
                }
                break;
            case 3:
                //test for 3 or 5
                //from top to bottom 3 goes from one line to two lines and 5 in theory should only visit two lines an immideatelly go back to one line
                //mayby use this in detectin 4 or 7
                if (threeOrFive()) {
                    im.name = 5;
                } else {
                    im.name = 3;
                }
                break;
        }
        return is;
    }

    private int behaviourTestOne(ArrayList<ArrayList<Integer>> list, int growthLimit) {
        //retun 0 if garbage        
        //return 1 if 1 or 2
        //return 2 if 4 or 7
        //return 3 if 3 or 5
        int longLineHeight = 0;
        int shortLineHeight = 0;
        int twoLines = 0;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).size() == 1) {
                if (i < growthLimit) {
                    if (list.get(i).get(0) > ((im.width * 5) / 8)) {
                        longLineHeight++;
                    }
                }

                if (list.get(i).get(0) < (im.width * 5 / 8)) {
                    shortLineHeight++;
                }
            } else if (i > growthLimit && list.get(i).size() == 2) {
                twoLines++;
            }
        }
        //System.out.println("=====INFO=====");
        //System.out.println("Long: "+longLineHeight+". Short: "+shortLineHeight+" twoLines: "+twoLines);
        //System.out.println("List Size "+list.size());
        //System.out.println("==============");
        if (shortLineHeight > (list.size() * 2) / 3) {
            return 2;
        }
        if (longLineHeight > 0 && twoLines < 3) {
            return 1;
        }
        return 3;
    }

    private ArrayList<ArrayList<Integer>> readSectionY(int yStart, int yEnd) {
        ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();

        if (yStart > im.height || yEnd < 0) {
            System.out.println("KORKEUS: " + im.height);
            this.errorCOunt++;
        }

        if (yStart < im.height && yEnd > 0) {
            for (int y = yStart; y > yEnd; y--) {
                temp.add(readLine(y));
            }
        }
        return temp;
    }

    private ArrayList<Integer> readLine(int y) {
        int lineWidth = 0;
        ArrayList<Integer> temp = new ArrayList<Integer>();

        for (int x = 0; x < im.width; x++) {
            if (im.image[y][x]) {
                lineWidth++;
            } else if (lineWidth > 0) {
                temp.add(lineWidth);
                lineWidth = 0;
            }
        }
        return temp;
    }

    private boolean testEight(int tolerance) {
        //bad test
        int size1 = connectedWhites.get(1).size();
        int size2 = connectedWhites.get(2).size();
        if (size1 < tolerance || size2 < tolerance) {
            return false;
        }
        return true;
    }

    private boolean sixOrNine() {
        //return true if 9
        //else return 6
        int x = 0;
        int y = 0;
        int size = connectedWhites.get(1).size();
        for (Point p : connectedWhites.get(1)) {
            x += p.x;
            y += p.y;
        }
        x = x / size;
        y = y / size;
        if (y < (im.height / 2) && x > (im.width / 4) && x < ((im.width * 3) / 4)) {
            return true;
        }
        return false;
    }

    private boolean oneOrTwo() {
        // test for 1 or 2
        //mayby read from side to side as two has max three lines and one only ever has max two lines risky
        //mayby read from bottom up and as soon as linelength drops to 1/2 of longest track position?
        //return true if two
        //else return false
        int maxWidth = 0;
        int width = 0;
        int start = 0;
        for (int y = im.height - 1; y > im.height / 2; y--) {
            width = 0;
            start = 0;
            for (int x = 0; x < im.width; x++) {
                if (im.image[y][x]) {
                    if (start == 0) {
                        start = x;
                    }
                    width++;
                }
            }
            if (width > maxWidth) {
                maxWidth = width;
            } else if (width < maxWidth / 2) {
                if (start < im.width / 3) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    private boolean fourOrSeven() {
        // test for 4 or 7
        //mayby read from middle to 1/4 f the top as 4 goes one the two permanently and 7 goes one mayby two but returns to one shortly
        //return true if 7
        //else return false
        ArrayList<ArrayList<Integer>> temp = this.readSectionY(im.height / 2, im.height / 4);
        int[] lines = new int[2];
        lines[0] = 0;
        lines[1] = 0;
        for (ArrayList l : temp) {
            if (l.size() < 3) {
                lines[l.size() - 1]++;
            }
        }
        if (lines[1] < temp.size() / 4) {
            return true;
        }
        return false;
    }

    private boolean threeOrFive() {
        //test for 3 or 5
        //from top to bottom 3 goes from one line to two lines and 5 in theory should only visit two lines an immideatelly go back to one line
        //return true if five
        //else return false
        ArrayList<ArrayList<Integer>> temp = this.readSectionY(im.height / 4, 3);
        int[] lines = new int[2];
        lines[0] = 0;
        lines[1] = 0;
        for (ArrayList l : temp) {
            if (l.size() < 3) {
                lines[l.size() - 1]++;
            }
        }
        if (lines[1] < temp.size() / 5) {
            return true;
        }
        return false;
    }
}
