import java.util.Arrays;
public final class Matrix {
    private double[][] matrix;
    private int termsFilled;
    /* Table of Content: Methods (edited 10/26/2023 23:20)
     *  Lines 25-34: constructors
     *  Lines 41-59: set/get a specified term
     *  Lines 65-88: Add a row
     *  Lines 94-125: Add a column
     *  Lines 131-147: Add two matrices
     *  Lines 152-158: Multiply matrix by scalar value
     *  Lines 162-170: Transpose matrix rows and columns
     *  Lines : Multiply matrices
     *  Lines TBD: toString
     *  Lines TBD: set one matrix equal to another
     *  Lines TBD: private method boundsCheck
     */
    /**
     * Constructors for Matrix objects
     * @apiNote all rows must have equal lengths
     * @param i number of rows
     * @param j number of columns
     * @see Matrix(double[][] input) for inputing a prebuilt 2d array of doubles
     */
    public Matrix(int i,int j) {
        if (!(i==0||j==0)) {        
            this.matrix = new double[i][j];
            this.termsFilled = 0;
        }
    }
    public Matrix(double[][] input) {
        this.matrix = Arrays.copyOf(input, input.length);
        this.termsFilled = this.matrix.length;
    }
    /**
     * Sets the value at a certain term of the matrix to the new value
     * @param i row number (starts at 0)
     * @param j column number (starts at 0)
     * @param value new value (double)
     */
    public void setTerm(int i, int j, double value) {
        if (boundsCheck(i,j)) {
            this.matrix[i][j] = value;
        }
    }
    /**
     * Gets the value of a term in the matrix
     * @param i row number (starts at 0)
     * @param j column number (starts at 0)
     * @return returns the value of the term
     */
    public double getTerm(int i, int j) {
        if (boundsCheck(i,j)) {    
            return this.matrix[i][j];
        } else {        
            System.out.println("getTerm: Input out of bounds, output set to 0");
            return 0;
        }
    }
    /**
     * Adds a row to the bottom of the matrix
     * @param newRow an array of doubles to add to the matrix
     * @see addRow() for adding an empty row with the length of the previous
     */
    public void addRow(double[] newRow) {
        double[][] tempArray = new double[this.matrix.length+1][1];
        for (int i = 0; i < this.matrix.length; i++) {
            tempArray[i] = Arrays.copyOf(this.matrix[i], this.matrix[i].length);
        }
        tempArray[this.matrix.length] = Arrays.copyOf(newRow, newRow.length);
        this.matrix = new double[tempArray.length][tempArray[0].length];
        for (int i = 0; i < tempArray.length; i++) {
            this.matrix[i] = Arrays.copyOf(tempArray[i], tempArray[i].length);
        }
        this.termsFilled += this.matrix[this.matrix.length-1].length;
    }
    public void addRow() {
        double[][] tempArray = new double[this.matrix.length+1][1];
        for (int i = 0; i < this.matrix.length; i++) {
            tempArray[i] = Arrays.copyOf(this.matrix[i], this.matrix[i].length);
        }
        tempArray[this.matrix.length] = Arrays.copyOf(new double[this.matrix[this.matrix.length-1].length], this.matrix[this.matrix.length-1].length);
        this.matrix = new double[tempArray.length][tempArray[0].length];
        for (int i = 0; i < tempArray.length; i++) {
            this.matrix[i] = Arrays.copyOf(tempArray[i], tempArray[i].length);
        }
        this.termsFilled += this.matrix[this.matrix.length-1].length;
    }
    /**
     * Adds a column to the end of the matrix
     * @param newColumn an array of doubles, the index in the array is the row it is placed on the end of
     * @see addColumn() to add an empty column
     */
    public void addColumn(double[] newColumn) {
        if (newColumn.length == this.matrix.length) {
            for (int i = 0; i < this.matrix.length; i++) {
                double[] tempRow = new double[this.matrix[i].length+1];
                for (int n = 0; n < this.matrix[i].length; n++) {
                    tempRow[n] = this.matrix[i][n];
                }
                tempRow[tempRow.length-1] = newColumn[i];
                this.matrix[i] = new double[tempRow.length];
                for (int n = 0; n < this.matrix[i].length; n++) {
                    this.matrix[i][n] = tempRow[n];
                }
                this.termsFilled += this.matrix.length;
            }
        } else {
            System.out.println("addColumn: Too many arguements in input array");
        }
    }
    public void addColumn() {
        for (int i = 0; i < this.matrix.length; i++) {
            double[] tempRow = new double[this.matrix[i].length+1];
            for (int n = 0; n < this.matrix[i].length; n++) {
                tempRow[n] = this.matrix[i][n];
            }
            tempRow[tempRow.length-1] = 0;
            this.matrix[i] = new double[tempRow.length];
            for (int n = 0; n < this.matrix[i].length; n++) {
                this.matrix[i][n] = tempRow[n];
            }
        }
        this.termsFilled += this.matrix.length;
    }
    /**
     * Adds two matrix objects together with matrix addition, result is set as instance matrix
     * @param newMatrix a matrix object
     * @return returns resulting matrix
     */
    public void add(Matrix newMatrix) {
        int resultRows, resultColumns;
        if (newMatrix.matrix.length > this.matrix.length) {resultRows = newMatrix.matrix.length;} 
        else {resultRows = this.matrix.length;}
        if (newMatrix.matrix[0].length > this.matrix[0].length) {resultColumns = newMatrix.matrix[0].length;}
        else {resultColumns = this.matrix[0].length;}
        Matrix result = new Matrix(resultRows, resultColumns);
        for (int i = 0; i < this.matrix.length; i++) {
            result.matrix[i] = Arrays.copyOf(this.matrix[i], resultColumns);
        }
        for (int i = 0; i < newMatrix.matrix.length; i++) {
            for (int n = 0; n < newMatrix.matrix[i].length; n++) {
                result.matrix[i][n] += newMatrix.matrix[i][n];
            }
        }
        this.setTo(result);
    }
    /**
     * Multiplies each term in the matrix by a constant, modifies instance matrix
     * @param constant any double that you want to multiply the matrix by
     */
    public void constantMultiply(double constant) {
        for (double[] line : this.matrix) {
            for (double value : line) {
                value = value * constant;
            }
        }
    }
    /**
     * Transposes the matrix, switches the rows and columns
     */
    public void transpose() {
        Matrix result = new Matrix(this.matrix[0].length, this.matrix.length);
        for (int i = 0; i < this.matrix.length; i++) {
            for (int n = 0; n < this.matrix[0].length; n++) {
                result.matrix[n][i] = this.matrix[i][n];
            }
        }
        this.setTo(result);
    }
    /**
     * Multiplies the instance matrix by the input 2d array, modifies instance matrix
     * @apiNote this.matrix.length (rows) must equal newArray[n].length (columns)
     * @param newArray a 2d array
     * @see multiply(Matrix newMatrix) to pass through objects
     */
    public void multiply(double[][] newArray) {
        if (this.matrix[0].length == newArray.length) {
            //TO DO: multiply matrices
        } else {
            System.out.println("multiply: Instance matrix column count must be equal to second matrix's rows count ");
        }
    }
    public void multiply(Matrix newMatrix) {
        this.multiply(newMatrix.matrix);
    }
    /**
     * Creates a string of a matrix object
     * @return returns a mutiline string of the values formated like a table
     */
    public String toString() {
        String result = "toString: No terms in matrix\n";
        if (this.termsFilled > 0) {
            result = "{";
            for (int i = 0; i < this.matrix.length;i++) {
                result += "{";
                for (int n = 0; n < this.matrix[i].length-1; n++) {
                     result += this.matrix[i][n]+", ";
                }
                result += this.matrix[i][this.matrix[i].length-1]+"}";
                if (i<this.matrix.length-1) {result+="\n";}
            }
            result += "}\n";
        }
        return result;
    }
    /**
     * sets the instance matrix equal to the 2d parameter array
     * @param newArray a m2d array
     * @see setTo(Matrix newMatrix) to input a matrix object
     */
    public void setTo(double[][] newArray) {
        if (newArray != null) {    
            this.matrix = new double [newArray.length][newArray[0].length];
            for (int i = 0; i < newArray.length; i++) {
                this.matrix[i] = Arrays.copyOf(newArray[i], newArray[i].length);
            }
        } else {
            this.matrix = null;
            this.termsFilled = 0;
        }
    }
    public void setTo(Matrix newMatrix) {
        this.setTo(newMatrix.matrix);
    }
    /**
     * Private method used to check bounds
     * @param i row
     * @param j column
     * @return boolean true if both i and j are in bounds
     */
    private boolean boundsCheck(int i, int j) {
        if ((i < this.matrix.length && j < this.matrix.length) && (i >= 0 && j >=0)) {
            return true;
        } else {
            System.out.println("boundsCheck: Invalid bound or bounds");
            return false;
        }
    }   
}