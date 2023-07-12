package TMDTBoBa.BoBaEcor.Models.ContactCustom;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

//@Table(name = "table_contact")
//@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Integer contactId;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "url_facebook")
    private String urlFacebook;

    @Column(name = "url_instagram")
    private String urlInstagram;

    @Column(name = "url_youtube")
    private String urlYoutube;

}
