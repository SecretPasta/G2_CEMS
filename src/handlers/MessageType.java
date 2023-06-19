package handlers;

/**
 * Represents the types of messages exchanged in the application.
 */
public enum MessageType {
    /**
     * Represents a string message.
     */
    STRING,

    /**
     * Represents an ArrayList of strings message.
     */
    ARRAY_LIST_STRING,

    /**
     * Represents an ArrayList of Question objects message.
     */
    ARRAY_LIST_QUESTION,

    /**
     * Represents an ArrayList of QuestionInExam objects message.
     */
    ARRAY_LIST_QUESTIONINEXAM,

    /**
     * Represents an ArrayList of Exam objects message.
     */
    ARRAY_LIST_EXAM,

    /**
     * Represents an ArrayList of HeadOfDepartment objects message.
     */
    ARRAY_LIST_HOD,

    /**
     * Represents an ArrayList of FinishedExam objects message.
     */
    ARRAY_LIST_FINISHED_EXAM,

    /**
     * Represents a map with string keys and ArrayList of strings values message.
     */
    MAP_STRING_ARRAYLIST_STRING,

    /**
     * Represents a map with string keys and string values message.
     */
    MAP_STRING_STRING,

    /**
     * Represents a MyFile object message.
     */
    MY_FILE
}

