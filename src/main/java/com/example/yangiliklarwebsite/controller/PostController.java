package com.example.yangiliklarwebsite.controller;

import com.example.yangiliklarwebsite.entity.Post;
import com.example.yangiliklarwebsite.payload.ApiRespons;
import com.example.yangiliklarwebsite.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostService postService;

    @PreAuthorize(value = "hasAnyAuthority('ADDPOST')")
    @PostMapping("/add")
    public HttpEntity<?> Joylash(@RequestBody Post post){
        ApiRespons apiRespons = postService.add(post);
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }

    @PreAuthorize(value = "hasAnyAuthority('EDITPOST')")
    @PutMapping("/edit/{id}")
    public HttpEntity<?> Edit(@PathVariable Integer id,@RequestBody Post post){
        ApiRespons apiRespons = postService.edit(id,post);
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }

    @PreAuthorize(value = "hasAnyAuthority('EDITMYPOST')")
    @PutMapping("/editmypost/{id}")
    public HttpEntity<?> EditMyPost(@PathVariable Integer id,@RequestBody Post post){
        ApiRespons apiRespons = postService.editmypost(id,post);
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }

    @PreAuthorize(value = "hasAnyAuthority('READPOST')")
    @GetMapping("/readid/{id}")
    public HttpEntity<?> ReadId(@PathVariable Integer id){
        ApiRespons apiRespons = postService.readid(id);
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }

    @PreAuthorize(value = "hasAnyAuthority('READPOST')")
    @GetMapping("/readall")
    public HttpEntity<?> ReadAll(){
        ApiRespons apiRespons = postService.readall();
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }

    @PreAuthorize(value = "hasAnyAuthority('DELETEPOST')")
    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> Delete(@PathVariable Integer id){
        ApiRespons apiRespons = postService.delete(id);
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }
}
