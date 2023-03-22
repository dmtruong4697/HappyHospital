package com.example.algorithm;

import java.util.Vector;

import com.example.classes.Position;

import java.util.Random;
class Spot {
    public double i,j;
    public static double ptmp = 1.0;
    public static double p;
    public Vector<Spot> neighbors = new Vector<Spot>();
    Spot previous;
    Spot(double i, double j){
        this.i = i;
        this.j = j;
        this.p = ptmp;
    }

    public void addNeighbors(Vector<Spot> ableSpot){
        for (var k = 0; k < ableSpot.toArray().length ; k++) {
            if (this.i + 1 == ableSpot.get(k).i && this.j == ableSpot.get(k).j) {
                this.neighbors.add(ableSpot.get(k));
            } else if (this.i == ableSpot.get(k).i && this.j + 1 == ableSpot.get(k).j) {
                this.neighbors.add(ableSpot.get(k));
            } else if (this.i - 1 == ableSpot.get(k).i && this.j == ableSpot.get(k).j) {
                this.neighbors.add(ableSpot.get(k));
            } else if (this.i == ableSpot.get(k).i && this.j - 1 == ableSpot.get(k).j) {
                this.neighbors.add(ableSpot.get(k));
            }
        }
    }

    public boolean equal(Spot spot) {
        if (this.i == spot.i && this.j == spot.j) return true;
        return false;
    }
}
public class ACO {
    public static double width, height;
    public Spot start;
    public Spot end;
    public static Vector<Spot> ableSpot;
    public static int count = 0 ;
    //public Vector<Vector<Spot>> grid;
    public static Spot[][] grid;
    public Vector<Spot> path = new Vector<Spot>();

    public int[] per;
    public int[] per1;
    public ACO(double width, double height, Position startPos, Position endPos, Position[] ablePos){

        this.start = new Spot(startPos.x , startPos.y );
        this.end = new Spot(endPos.x , endPos.y );
        //this.grid = new Vector<Vector<Spot>>();


        for (var i = 0 ; i< width; i++) {
            for (var j = 0;j < height; j++) {
                ACO.grid[i][j].previous = null;
            }
        }
        count++;

    }
    public static void init(double width, double height,Position[] ablePos) {
        ACO.width = width;
        ACO.height = height;
        ACO.grid = new Spot[52][28];
        for (var i = 0 ; i< width; i++) {
            for (var j = 0;j < height; j++) {
                ACO.grid[i][j] = new Spot(i,j);
            }
        }

        ACO.ableSpot = new Vector<Spot>();
        for (var i = 0; i < ablePos.length; i++) {
            ACO.ableSpot.add( ACO.grid[(int) ablePos[i].x ][(int) ablePos[i].y ]);
        }

        for (var i = 0; i < width; i++) {
            for (var j = 0; j < height; j++) {
                ACO.grid[i][j].addNeighbors(ACO.ableSpot);
            }
        }
    }

    private double heuristic2(Spot spot1, Spot spot2) {
        return Math.sqrt(  Math.pow(spot1.i - spot2.i, 2) + Math.pow(spot1.j - spot2.j, 2) );
    }

    private boolean isInclude(Spot spot,Vector<Spot> spots) {
        for (var i = 0; i < spots.toArray().length; i++) {
            if (spot.i == spots.get(i).i && spot.j == spots.get(i).j) return true;
        }
        return false;
    }

    public void update(){
        for (var i = 0 ; i< width; i++) {
            for (var j = 0; j < height; j++) {
                ACO.grid[i][j].p = (ACO.grid[i][j].p)*(this.heuristic2(this.start, this.end)/this.heuristic2(ACO.grid[i][j], this.end));
            }
        }
    }
    public int lotteryWheel(Spot c){
        var win = 0;
        var t = 0.0;
        for (var i = 0; i < c.neighbors.toArray().length; i++){
            t = t + c.neighbors.get(i).p;
        }

        for (var i = 0; i < 100; i++){
            per[i] = i;
        }
        var x = 0;
        for (var i = 0; i < c.neighbors.toArray().length; i++){
            var j = 0;
            while (j<(c.neighbors.get(i).p/t)*100){
                per1[x+j] = i;
                j++;
            }
        }

        Random rand = new Random();
        var winn = rand.nextInt(99);
        win = per1[winn];
        return win;
    }
    public Position[] cal() {

        var closeSet = new Vector<Spot>();
        var current = this.start;
        while (!current.equal(this.end)) {

            //Random rand = new Random();
            //var winner = rand.nextInt(current.neighbors.toArray().length);

            var winner = lotteryWheel(current);

            var tmp = current;
            current = current.neighbors.get(winner);
            current.p += 0.5;
            current.ptmp = current.p;
            current.previous = tmp;
            if (current.equal(this.end)) {

                var cur = ACO.grid[(int) this.end.i][(int) this.end.j];
                this.path.add(cur);
                while (cur.previous != null) {
                    this.path.add(cur.previous);
                    cur = cur.previous;
                }

                var result = new Position[this.path.size()];
                for (var k = this.path.size() -1; k>=0 ; k--) {
                    result[this.path.size() - 1 - k] = new Position(this.path.get(k).i, this.path.get(k).j) ;
                }
                this.update();
                return result;
            }
        }
        System.out.println("Path not found!");
        //result.add( new Position(-1,-1));
        return null;
    }
}





































