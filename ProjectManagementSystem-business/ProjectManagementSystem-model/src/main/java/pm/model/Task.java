package pm.model;

import com.digital.pm.common.enums.TaskStatus;

import java.util.Date;

public class Task {
    private int id;
    private String name;
    private String description;

    private Employee executor;

    private int laborCosts;

    private Date deadline;
    private TaskStatus status;

    private String author;

    private Date dateOfCreation;
    private Date update;

}
