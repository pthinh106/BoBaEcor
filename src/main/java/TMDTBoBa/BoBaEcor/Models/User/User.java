package TMDTBoBa.BoBaEcor.Models.User;

import TMDTBoBa.BoBaEcor.Models.Store.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.*;

@Entity
@Setter
@Getter
@Table(name = "table_users")

public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "first_name", columnDefinition = "varchar(255) NOT NULL")
    private String firstName;

    @Column(name = "last_name", columnDefinition = "varchar(255) NOT NULL")
    private String lastName;

    @Column(name = "email", columnDefinition = "varchar(255)")
    private String email;

    @Column(name = "phone", columnDefinition = "varchar(11)")
    private String phoneNumber;

    @Column(name = "birth_date")
    private Date birthDay;

    @Column(name = "address", columnDefinition = "varchar(255)")
    private String address;

    @Column(name = "password", columnDefinition = "varchar(255)")
    @JsonIgnore
    private String password;

    @Column(name = "verification_code", columnDefinition = "varchar(255)")
    @JsonIgnore
    private String verificationCode;

    @Column(name = "forget_token", columnDefinition = "varchar(255)")
    @JsonIgnore
    private String forgetToken;

    @Column(name = "access_token", columnDefinition = "varchar(255)")
    @JsonIgnore
    private String accessToken;

    @Column(name = "refresh_token", columnDefinition = "varchar(255)")
    @JsonIgnore
    private String refreshToken;

    @Column(name = "account_status", columnDefinition = "tinyint(1) default 0")
    private Integer accountStatus = 0;

    @Column(name = "is_employee", columnDefinition = "tinyint(1) default 0")
    private Integer isEmployee = 0;

    @Column(name = "total_price", columnDefinition = "int(11) default 0")
    private Integer totalPrice = 0;

    @Column(name = "ranked", columnDefinition = "int(11) default 0")
    private Integer ranked = 0;

    @Column(name = "created_on", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp()
    private Timestamp createdOn;

    @Column(name = "updated_on", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @UpdateTimestamp()
    private Timestamp updatedOn;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "position_id")
    private Position position;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<UserHasRole> listRole = new HashSet<>();
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Order>  listOrder = new HashSet<>();

    public User() {
    }

//    public User(Integer userId, String firstName, String lastName, String email, String phoneNumber, Date birthDay, String address, String password, String verificationCode, String forgetToken, String accessToken, String refreshToken, Integer accountStatus, Integer isEmployee, Integer totalPrice, Integer rank, Timestamp createdOn, Timestamp updatedOn, Position position, Set<UserHasRole> listRole, Set<Order> listOrder) {
//        this.userId = userId;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.phoneNumber = phoneNumber;
//        this.birthDay = birthDay;
//        this.address = address;
//        this.password = password;
//        this.verificationCode = verificationCode;
//        this.forgetToken = forgetToken;
//        this.accessToken = accessToken;
//        this.refreshToken = refreshToken;
//        this.accountStatus = accountStatus;
//        this.isEmployee = isEmployee;
//        this.totalPrice = totalPrice;
//        this.rank = rank;
//        this.createdOn = createdOn;
//        this.updatedOn = updatedOn;
//        this.position = position;
//        this.listRole = listRole;
//        this.listOrder = listOrder;
//    }

    public User(Integer userId, String firstName, String lastName, String email, String phoneNumber, Date birthDay, String address, String password, String verificationCode, String forgetToken, String accessToken, String refreshToken, Integer accountStatus, Integer isEmployee, Timestamp createdOn, Timestamp updatedOn, Position position, Set<UserHasRole> listRole, Set<Order> listOrder) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.address = address;
        this.password = password;
        this.verificationCode = verificationCode;
        this.forgetToken = forgetToken;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accountStatus = accountStatus;
        this.isEmployee = isEmployee;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.position = position;
        this.listRole = listRole;
        this.listOrder = listOrder;
    }

    public User(Integer userId, @NotNull String firstName, @NotNull String lastName, String email, String phoneNumber, Date birthDay, String address, String password, String verificationCode, String forgetToken, String accessToken, String refreshToken, Integer accountStatus, Timestamp createdOn, Timestamp updatedOn, Set<UserHasRole> listRole) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.address = address;
        this.password = password;
        this.verificationCode = verificationCode;
        this.forgetToken = forgetToken;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accountStatus = accountStatus;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.listRole = listRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        listRole.forEach(i->authorities.add(new SimpleGrantedAuthority(i.getRole().getRoleName())));
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return accountStatus == 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getUserId() != null && Objects.equals(getUserId(), user.getUserId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }
}
