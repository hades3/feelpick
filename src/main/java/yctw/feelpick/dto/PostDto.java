package yctw.feelpick.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class PostDto {

    private String content;

    private List<MultipartFile> imageFiles;

}
