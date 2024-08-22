package yctw.feelpick.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import yctw.feelpick.domain.UploadFile;

import java.util.List;

@Getter
@Setter
public class ModifyDto {

    private String content;

    private List<UploadFile> oldImageFiles;

    private List<MultipartFile> newImageFiles;

}
