package com.example.yangiliklarwebsite.controller;

import com.example.yangiliklarwebsite.payload.ApiRespons;
import com.example.yangiliklarwebsite.payload.CommentDto;
import com.example.yangiliklarwebsite.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PreAuthorize(value = "hasAnyAuthority('ADDCOMMENT')")
    @PostMapping("/add/{id}")
    public HttpEntity<?> Joylash(@RequestBody CommentDto commentDto,@PathVariable Integer id){
        ApiRespons apiRespons = commentService.add(commentDto,id);
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }

    @PreAuthorize(value = "hasAnyAuthority('EDITMYCOMMENT')")
    @PutMapping("/editmycomment/{id}")
    public HttpEntity<?> EditMyComment(@PathVariable Integer id,@RequestBody CommentDto commentDto){
        ApiRespons apiRespons = commentService.editmycomment(id,commentDto);
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }

    @PreAuthorize(value = "hasAnyAuthority('EDITCOMMENT')")
    @PutMapping("/edit/{id}")
    public HttpEntity<?> Tahrir(@PathVariable Integer id,@RequestBody CommentDto commentDto){
        ApiRespons apiRespons = commentService.edit(id,commentDto);
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }

    @PreAuthorize(value = "hasAnyAuthority('READCOMMENT')")
    @GetMapping("/readall")
    public HttpEntity<?> ReadAll(){
        ApiRespons apiRespons = commentService.readall();
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }

    @PreAuthorize(value = "hasAnyAuthority('READCOMMENT')")
    @GetMapping("/readid/{id}")
    public HttpEntity<?> ReadId(@PathVariable Integer id){
        ApiRespons apiRespons = commentService.readid(id);
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }

    @PreAuthorize(value = "hasAnyAuthority('DELETECOMMENT')")
    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> Delete(@PathVariable Integer id){
        ApiRespons apiRespons = commentService.delete(id);
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }

    @PreAuthorize(value = "hasAnyAuthority('DELETEMYCOMMENT')")
    @DeleteMapping("/deletemycomment/{id}")
    public HttpEntity<?> DeleteMyComment(@PathVariable Integer id){
        ApiRespons apiRespons = commentService.deletemycomment(id);
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }
}
