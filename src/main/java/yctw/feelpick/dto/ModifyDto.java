package yctw.feelpick.dto;

import lombok.Getter;
import lombok.Setter;
import yctw.feelpick.domain.UploadFile;

import java.util.List;

@Getter
@Setter
public class ModifyDto {

    private String comment;

    private List<UploadFile> imageFiles;

}
