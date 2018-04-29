package com.enterprise.yetanother.convertion.dtoconverters.implementations;

import com.enterprise.yetanother.convertion.dtovalidation.interfaces.CommentValidator;
import com.enterprise.yetanother.dto.comment.CommentDto;
import com.enterprise.yetanother.entities.Comment;
import com.enterprise.yetanother.convertion.dtoconverters.interfaces.CommentDtoConverter;
/*import com.enterprise.yetanother.dto.comment.CommentDto;
import com.enterprise.yetanother.entities.Comment;
import com.enterprise.yetanother.convertion.dtovalidation.interfaces.CommentValidator;*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *@author andrey
 */
@Component
public class CommentDtoConverterImpl implements CommentDtoConverter {

    final static Logger LOGGER = LoggerFactory
                                 .getLogger(CommentDtoConverterImpl.class);

    @Autowired
    private CommentValidator commentValidator;

    @Override
    public Comment dtoToEntity(CommentDto commentDto) {
        if (commentDto != null) {
            try {
                String text = commentDto.getText();
                if (commentValidator.validate(commentDto)) {
                    Comment comment = new Comment();
                    comment.setText(text);
                    comment.setDate(new Date());
                    return comment;
                }
            } catch (Exception e) {
                LOGGER.error("[dtoToEntity: Exception thrown!]", e);
            }
        } else {
            LOGGER.warn("[dtoToEntity: commentDto is null!]");
        }
        return null;
    }

    @Override
    public CommentDto entityToDto(Comment comment) {
        if (comment != null) {
            try {
                String text = comment.getText();
                Date date = comment.getDate();
                if (commentValidator.validate(text, date)) {
                    CommentDto commentDto = new CommentDto();
                    commentDto.setId(comment.getId());
                    commentDto.setDate(date);
                    commentDto.setText(text);
                    commentDto.setUser(comment.getUser());
                    commentDto.setTicket(comment.getTicket());
                    return commentDto;
                }
            } catch (Exception e) {
                LOGGER.error("[entityToDto: Exception thrown!]");
            }
        } else {
            LOGGER.warn("[entityToDto: comment is null!]");
        }
        return null;
    }

    @Override
    public List<CommentDto> entitiesToDtos(List<Comment> comments) {
        if (comments != null) {
            try {
                List<CommentDto> commentDtos = new ArrayList<>();
                for (Comment comment: comments) {
                    commentDtos.add(entityToDto(comment));
                }
                return commentDtos;
            } catch (Exception e) {
                LOGGER.error("[entitiesToDtos: Exception thrown!]", e);
            }
        } else {
            LOGGER.warn("[entitiesToDtos: comments is null!]");
        }
        return null;
    }
}
