package com.example.algorithm;

import java.util.Vector;

import com.example.classes.Position;

class PSpot {
    public double i,j,p;
    public Vector<PSpot> neighbors = new Vector<PSpot>();
    PSpot previous;
    PSpot(double i, double j){
        this.i = i;
        this.j = j;
        this.p = 1.0;
    }

    public void addNeighbors(Vector<PSpot> ablePSpot){
        for (var k = 0; k < ablePSpot.toArray().length ; k++) {
            if (this.i + 1 == ablePSpot.get(k).i && this.j == ablePSpot.get(k).j) {
                this.neighbors.add(ablePSpot.get(k));
            } else if (this.i == ablePSpot.get(k).i && this.j + 1 == ablePSpot.get(k).j) {
                this.neighbors.add(ablePSpot.get(k));
            } else if (this.i - 1 == ablePSpot.get(k).i && this.j == ablePSpot.get(k).j) {
                this.neighbors.add(ablePSpot.get(k));
            } else if (this.i == ablePSpot.get(k).i && this.j - 1 == ablePSpot.get(k).j) {
                this.neighbors.add(ablePSpot.get(k));
            }
        }
    }


}