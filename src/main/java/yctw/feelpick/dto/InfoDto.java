package yctw.feelpick.dto;

import lombok.Getter;
import lombok.Setter;
import yctw.feelpick.domain.Post;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class InfoDto {

    private List<Post> posts = new ArrayList<>();


}
