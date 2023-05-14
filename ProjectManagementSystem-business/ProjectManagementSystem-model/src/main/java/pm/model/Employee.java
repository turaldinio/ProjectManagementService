package pm.model;

import com.digital.pm.common.enums.EmployeeStatus;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
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
