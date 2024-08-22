package yctw.feelpick.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "POST_ID")
    private Long id;

    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UploadFile> imageFiles = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOOD_ID")
    private Food food;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private LocalDateTime createdDateTime;

    private LocalDateTime modifiedDateTime;

    // 생성 메서드
    public static Post createPost(Member member, Food food, List<UploadFile> imageFiles, String content){
        Post post = new Post();
        post.setMember(member);
        for (UploadFile imageFile : imageFiles) {
            post.getImageFiles().add(imageFile);
            imageFile.setPost(post);
        }
        post.setContent(content);
        post.setFood(food);
        post.setCreatedDateTime(LocalDateTime.now());
        post.setModifiedDateTime(post.getCreatedDateTime());
        for (UploadFile imageFile : imageFiles) {
            imageFile.setPost(post);
        }
        return post;
    }

    // 수정 메서드
    public void modifyPost(List<UploadFile> imageFiles, String content) {
        if (!imageFiles.isEmpty()){
            this.getImageFiles().removeAll(getImageFiles());
            for (UploadFile imageFile : imageFiles) {
                this.getImageFiles().add(imageFile);
                imageFile.setPost(this);
            }
        }
        this.setModifiedDateTime(LocalDateTime.now());
        this.setContent(content);
    }
}
