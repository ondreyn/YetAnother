package com.enterprise.yetanother.convertion.dtovalidation.interfaces;

import com.enterprise.yetanother.dto.comment.CommentDto;

import java.util.Date;

/**
 *@author andrey
 */
public interface CommentValidator {

    boolean validate(CommentDto commentDto);
    boolean validate(String text, Date date);
}
