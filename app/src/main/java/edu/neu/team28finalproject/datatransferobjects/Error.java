package edu.neu.team28finalproject.datatransferobjects;

/**
 * POJO class that extracts errors from a JSON object.
 */
public class Error {
    private String error;

    /**
     * Creates an instance of this class.
     */
    public Error() {
        super();
    }

    /**
     * Creates an instance of this class with the required parameters.
     *
     * @param error error message
     */
    public Error(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
