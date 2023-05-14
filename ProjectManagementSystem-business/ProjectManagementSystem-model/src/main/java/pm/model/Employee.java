package pm.model;

import com.digital.pm.common.enums.EmployeeStatus;
import lombok.*;

@Builder
@Getter
@Setter

public class Employee {
    private int id;
    private String firsName;
    private String lastName;
    private String patronymic;
    private String post;
    private String account;
    private String email;
    private EmployeeStatus status;

    @Override
    public String toString() {
        return
                id + " " +
                        firsName + " " +
                        lastName + " " +
                        patronymic + " " +
                        post + " " +
                        account + " " +
                        email + " " +
                        status;
    }
}
