package com.example.yangiliklarwebsite.service;

import com.example.yangiliklarwebsite.entity.Comment;
import com.example.yangiliklarwebsite.entity.Post;
import com.example.yangiliklarwebsite.entity.Users;
import com.example.yangiliklarwebsite.payload.ApiRespons;
import com.example.yangiliklarwebsite.payload.CommentDto;
import com.example.yangiliklarwebsite.repository.CommentRepository;
import com.example.yangiliklarwebsite.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;


    public ApiRespons add(CommentDto commentDto,Integer id) {
        Optional<Post> b = postRepository.findById(id);
        if (b.isPresent()){
            Comment comment = new Comment(
                    commentDto.getMatni(),
                    b.get()
            );
            commentRepository.save(comment);
            return new ApiRespons("Comment muvaffaqiyatli saqlandi",true);
        }
        return new ApiRespons("Bunday post mavjud emas",false);
    }

    public ApiRespons edit(Integer id, CommentDto commentDto) {
        Optional<Comment> byId = commentRepository.findById(id);
        if (byId.isPresent()){
            Comment comment = byId.get();
            comment.setMatni(commentDto.getMatni());
            comment.setPost(byId.get().getPost());
            commentRepository.save(comment);
            return new ApiRespons("Comment muvaffaqiyatli tahrirlandi",true);
        }
        return new ApiRespons("Bunday post mavjud emas",false);
    }

    public ApiRespons readall() {
        List<Comment> all = commentRepository.findAll();
        if (!all.isEmpty()){
            String S = "";
            for (Comment comment : all) {
                S += comment.toString() + "\n";
            }
            return new ApiRespons(S,true);
        }
        return new ApiRespons("Ma'lumotlar mavjud emas",false);
    }

    public ApiRespons readid(Integer id) {
        Optional<Comment> byId = commentRepository.findById(id);
        if (byId.isPresent()){
            String S = byId.get().toString();
            return new ApiRespons(S,true);
        }
        return new ApiRespons("Bunday Comment mavjud emas",false);
    }

    public ApiRespons delete(Integer id) {
        Optional<Comment> byId = commentRepository.findById(id);
        if (byId.isPresent()){
            commentRepository.deleteById(id);
            return new ApiRespons("Ma'lumot muvaffaqiyatli o'chirildi",true);
        }
        return new ApiRespons("Bunday Comment mavjud emas",false);
    }

    public ApiRespons editmycomment(Integer id, CommentDto commentDto) {
        Users users = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Comment> byIdAndKimTomonidanYaratilgan = commentRepository.findByIdAndCreateBy(id, users.getId());
        if (byIdAndKimTomonidanYaratilgan.isPresent()){
            Comment comment = byIdAndKimTomonidanYaratilgan.get();
            comment.setMatni(commentDto.getMatni());
            commentRepository.save(comment);
            return new ApiRespons("Comment tahrirlandi",true);
        }
        return new ApiRespons("Siz ushbu commentary ni tahrirlay olmaysiz!",false);
    }

    public ApiRespons deletemycomment(Integer id) {
        Users users = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Comment> byIdAndCreateBy = commentRepository.findByIdAndCreateBy(id, users.getId());
        if (byIdAndCreateBy.isPresent()){
            commentRepository.deleteById(id);
            return new ApiRespons("Ma'lumot muvaffaqiyatli o'chirildi",true);
        }
        return new ApiRespons("Siz ushbu commentaryani o'chira olmaysiz!",false);
    }
}
