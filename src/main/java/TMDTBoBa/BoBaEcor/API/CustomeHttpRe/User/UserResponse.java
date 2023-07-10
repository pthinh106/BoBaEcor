package TMDTBoBa.BoBaEcor.API.CustomeHttpRe.User;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@RequiredArgsConstructor
@Getter
@Setter
@Data
public class UserResponse {

    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String birthDay;

    private String address;

    private Integer accountStatus;

    private String createdOn;

    private String updatedOn;

    private Set<String> listRole;

    public UserResponse(Long userId, String firstName, String lastName, String email, String phoneNumber, String birthDay, String address, Integer accountStatus, String createdOn, String updatedOn, Set<String> listRole) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.address = address;
        this.accountStatus = accountStatus;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.listRole = listRole;
    }

    public UserResponse(Long userId, String firstName, String lastName, String email, String phoneNumber, String birthDay, String address, Integer accountStatus, String createdOn, String updatedOn) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.address = address;
        this.accountStatus = accountStatus;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public String getFullName(){ return firstName + " " + lastName;}
}
