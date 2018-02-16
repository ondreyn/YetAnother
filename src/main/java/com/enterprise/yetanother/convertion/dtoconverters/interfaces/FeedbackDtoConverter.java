package com.enterprise.yetanother.convertion.dtoconverters.interfaces;

import com.enterprise.yetanother.dto.feedback.FeedbackDto;
import com.enterprise.yetanother.entities.Feedback;
import com.enterprise.yetanother.dto.feedback.FeedbackDto;
import com.enterprise.yetanother.entities.Feedback;

import java.util.List;

public interface FeedbackDtoConverter {

    Feedback dtoToEntity(FeedbackDto feedbackDto);
    FeedbackDto entityToDto(Feedback feedback);
    List<FeedbackDto> entitiesToDtos(List<Feedback> feedbacks);
}
