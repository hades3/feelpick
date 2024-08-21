package yctw.feelpick.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yctw.feelpick.domain.Member;
import yctw.feelpick.domain.Post;
import yctw.feelpick.repository.PostRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public Post findPost(Long id){
        return postRepository.findOne(id);
    }

    public List<Post> findPosts(){
        List<Post> posts = postRepository.findAll();
        return posts;
    }

    public void remove(Long id){
        Post post = postRepository.findOne(id);
        postRepository.remove(post);
    }

    public void write(Post post){
        postRepository.save(post);
    }

    public List<Post> findPostByMemberId(Long memberId){
        return postRepository.findAllByMemberId(memberId);
    }

    public List<Post> findPostByFoodId(Long foodId){
        return postRepository.findAllByFoodId(foodId);
    }
}
