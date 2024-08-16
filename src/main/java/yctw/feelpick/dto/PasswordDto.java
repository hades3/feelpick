package yctw.feelpick.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDto {

    private String currentPassword;

    private String newPassord;

    private String confirmPassord;

}
