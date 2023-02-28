package com.example.yangiliklarwebsite.service;

import com.example.yangiliklarwebsite.entity.Post;
import com.example.yangiliklarwebsite.entity.Users;
import com.example.yangiliklarwebsite.payload.ApiRespons;
import com.example.yangiliklarwebsite.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public ApiRespons add(Post post) {
        boolean b = postRepository.existsByMatni(post.getMatni());
        if (!b){
            Post post1 = new Post(
                    post.getSarlavha(),
                    post.getMatni(),
                    post.getUrl()
            );
            postRepository.save(post1);
            return new ApiRespons("Ma'lumot muvaffaqiyatli saqlandi",true);
        }
        return new ApiRespons("Bunday post mavjud",false);
    }

    public ApiRespons edit(Integer id, Post post) {
        Optional<Post> byId = postRepository.findById(id);
        if (byId.isPresent()){
            Post post1 = byId.get();
            post1.setSarlavha(post.getSarlavha());
            post1.setMatni(post.getMatni());
            post1.setUrl(post.getUrl());
            postRepository.save(post1);
            return new ApiRespons("Ma'lumot muvaffaqiyatli tahrirlandi",true);
        }
        return new ApiRespons("Bunday Idli ma'lumot mavjud emas",false);
    }

    public ApiRespons readid(Integer id) {
        Optional<Post> byId = postRepository.findById(id);
        if (byId.isPresent()){
            String S = byId.get().toString();
            return new ApiRespons(S,true);
        }
        return new ApiRespons("Bunday Idli ma'lumot mavjud emas",false);
    }

    public ApiRespons readall() {
        List<Post> all = postRepository.findAll();
        if (!all.isEmpty()){
            String S = "";
            for (Post post : all) {
                S += post.toString() + "\n";
            }
            return new ApiRespons(S,true);
        }
        return  new ApiRespons("Ma'lumotlar yo'q",false);
    }

    public ApiRespons delete(Integer id) {
        Optional<Post> byId = postRepository.findById(id);
        if (byId.isPresent()){
            postRepository.deleteById(id);
            return new ApiRespons("Ma'lumot muvaffaqiyatli o'chirildi",true);
        }
        return new ApiRespons("Bunday Idli ma'lumot mavjud emas",false);
    }

    public ApiRespons editmypost(Integer id, Post post) {
        Users users = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Post> byIdAndCreateBy = postRepository.findByIdAndCreateBy(id, users.getId());
        if (byIdAndCreateBy.isPresent()){
            Post post1 = byIdAndCreateBy.get();
            post1.setSarlavha(post.getSarlavha());
            post1.setMatni(post.getMatni());
            post1.setUrl(post.getUrl());
            postRepository.save(post1);
            return new ApiRespons("Post muvaffaqiyatli tahrirlandi",true);
        }
        return new ApiRespons("Siz ushbu postni ni tahrirlay olmaysiz!",false);
    }
}
