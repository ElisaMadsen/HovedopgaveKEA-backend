package com.example.hovedopgavekea.service;

import com.example.hovedopgavekea.model.Post;
import com.example.hovedopgavekea.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PostService implements IPostService{

    private PostRepository postRepository;

    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @Override
    public Set<Post> findAll() {
        Set<Post> posts = new HashSet<>();
        postRepository.findAll().forEach(posts::add);
        return posts;
    }

    @Override
    public Post save(Post object) {
        postRepository.save(object);
        return object;
    }

    @Override
    public void delete(Post object) {
        postRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        postRepository.deleteById(aLong);
    }

    @Override
    public Optional<Post> findById(Long aLong) {
        return postRepository.findById(aLong);
    }
}
