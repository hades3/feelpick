package yctw.feelpick.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yctw.feelpick.domain.UploadFile;

@Repository
@RequiredArgsConstructor
@Transactional
public class UploadFileRepository {

    private final EntityManager em;

    public Long save(UploadFile uploadFile){
        em.persist(uploadFile);
        return uploadFile.getId();
    }

    public void remove(UploadFile uploadFile){
        em.remove(uploadFile);
    }

    public UploadFile findOne(Long id){
        UploadFile uploadFile = em.find(UploadFile.class, id);
        return uploadFile;
    }
}
