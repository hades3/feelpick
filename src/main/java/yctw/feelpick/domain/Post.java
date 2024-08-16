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

    private String comment;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UploadFile> imageFiles = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOOD_ID")
    private Food food;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private LocalDateTime modifiedTime;

    // 생성 메서드
    public static Post createPost(Member member, Food food, List<UploadFile> imageFiles, String comment){
        Post post = new Post();
        post.setMember(member);
        post.setImageFiles(imageFiles);
        post.setComment(comment);
        post.setFood(food);
        post.setModifiedTime(LocalDateTime.now());
        for (UploadFile imageFile : imageFiles) {
            imageFile.setPost(post);
        }
        return post;
    }
}
