package info.derek.relieffinder.webflow;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
class ShiftCreationForm {

    @NotBlank
    private String email;

    @NotBlank
    private String name;

    private String phoneNumber;
}
