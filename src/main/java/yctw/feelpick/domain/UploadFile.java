package yctw.feelpick.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UploadFile {
    @Id
    @GeneratedValue
    @Column(name = "FILE_ID")
    private Long id;

    private String uploadFileName;

    private String storeFileName;

    protected UploadFile() {
    }

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

}
