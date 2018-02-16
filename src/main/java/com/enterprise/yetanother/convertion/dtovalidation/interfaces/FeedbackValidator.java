package com.enterprise.yetanother.convertion.dtovalidation.interfaces;

import com.enterprise.yetanother.dto.feedback.FeedbackDto;
import com.enterprise.yetanother.entities.Feedback;
import com.enterprise.yetanother.dto.feedback.FeedbackDto;
import com.enterprise.yetanother.entities.Feedback;

/**
 *@author andrey
 */
public interface FeedbackValidator {

    boolean validate(FeedbackDto feedbackDto);
    boolean validate(Feedback feedback);
}
