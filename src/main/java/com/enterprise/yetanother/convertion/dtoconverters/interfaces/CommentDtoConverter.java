package com.enterprise.yetanother.convertion.dtoconverters.interfaces;

import com.enterprise.yetanother.dto.comment.CommentDto;
import com.enterprise.yetanother.entities.Comment;
import com.enterprise.yetanother.dto.comment.CommentDto;
import com.enterprise.yetanother.entities.Comment;

import java.util.List;

public interface CommentDtoConverter {

    Comment dtoToEntity(CommentDto commentDto);
    CommentDto entityToDto(Comment comment);
    List<CommentDto> entitiesToDtos(List<Comment> comments);
}
