package Config;

import java.io.Serializable;

public class MyFile implements Serializable {

    private String Description = null;
    private String fileName = null;
    private int size = 0;
    public byte[] mybytearray;


    /**
     * Initializes the byte array with the specified size.
     *
     * @param size The size of the byte array to initialize.
     */
    public void initArray(int size) {
        mybytearray = new byte[size];
    }

    /**
     * Constructs a MyFile object with the specified file name.
     *
     * @param fileName The name of the file.
     */
    public MyFile(String fileName) {
        this.fileName = fileName;
    }


    /**
     * Returns the file name.
     *
     * @return The file name.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the file name.
     *
     * @param fileName The file name to set.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Returns the size of the file.
     *
     * @return The size of the file.
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the size of the file.
     *
     * @param size The size of the file to set.
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Returns the byte array representing the file content.
     *
     * @return The byte array representing the file content.
     */
    public byte[] getMybytearray() {
        return mybytearray;
    }

    /**
     * Returns the byte at the specified index in the byte array.
     *
     * @param i The index of the byte to retrieve.
     * @return The byte at the specified index.
     */
    public byte getMybytearray(int i) {
        return mybytearray[i];
    }

    /**
     * Sets the byte array representing the file content.
     *
     * @param mybytearray The byte array to set.
     */
    public void setMybytearray(byte[] mybytearray) {
        for (int i = 0; i < mybytearray.length; i++)
            this.mybytearray[i] = mybytearray[i];
    }

    /**
     * Returns the description of the file.
     *
     * @return The description of the file.
     */
    public String getDescription() {
        return Description;
    }

    /**
     * Sets the description of the file.
     *
     * @param description The description of the file to set.
     */
    public void setDescription(String description) {
        Description = description;
    }
}
