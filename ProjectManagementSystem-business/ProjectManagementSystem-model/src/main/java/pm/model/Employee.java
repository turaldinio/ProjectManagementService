package pm.model;

import com.digital.pm.common.enums.EmployeeStatus;
import lombok.Data;

@Data
public class Employee {
    private int id;
    private String firsName;
    private String lastName;
    private String patronymic;
    private String post;
    private String account;
    private String email;
    private EmployeeStatus status;

}
