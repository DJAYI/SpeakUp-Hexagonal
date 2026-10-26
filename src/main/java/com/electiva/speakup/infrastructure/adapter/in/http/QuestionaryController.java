package com.electiva.speakup.infrastructure.adapter.in.http;

import com.electiva.speakup.application.port.in.AddQuestionToCategoryUseCase;
import com.electiva.speakup.application.port.in.ResponseQuestionUseCase;
import com.electiva.speakup.application.port.in.RetrieveAnswersFromQuestionUseCase;
import com.electiva.speakup.application.port.in.RetrieveQuestionsFromCategoryUseCase;
import com.electiva.speakup.application.port.in.VerifyAnswersUseCase;
import com.electiva.speakup.application.result.AnswerVerification;
import com.electiva.speakup.domain.model.Question;
import com.electiva.speakup.domain.model.answer.Answer;
import com.electiva.speakup.infrastructure.adapter.in.http.dto.CreateQuestionRequest;
import com.electiva.speakup.infrastructure.adapter.in.http.dto.QuestionResponse;
import com.electiva.speakup.infrastructure.adapter.in.http.dto.answer.AnswerRequest;
import com.electiva.speakup.infrastructure.adapter.in.http.dto.answer.AnswerResponse;
import com.electiva.speakup.infrastructure.adapter.in.http.mapper.AnswerRequestMapper;
import com.electiva.speakup.infrastructure.adapter.in.http.mapper.AnswerResponseMapper;
import com.electiva.speakup.infrastructure.adapter.in.http.mapper.QuestionResponseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/questionary")
public class QuestionaryController {

    private final RetrieveQuestionsFromCategoryUseCase retrieveQuestionsFromCategoryUseCase;
    private final RetrieveAnswersFromQuestionUseCase retrieveAnswersFromQuestionUseCase;
    private final VerifyAnswersUseCase verifyAnswersUseCase;
    private final ResponseQuestionUseCase responseQuestionUseCase;
    private final AddQuestionToCategoryUseCase addQuestionToCategoryUseCase;
    private final AnswerRequestMapper answerRequestMapper;
    private final AnswerResponseMapper answerResponseMapper;
    private final QuestionResponseMapper questionResponseMapper;


    @PostMapping("/questions/{questionId}")
    public ResponseEntity<Void> respondToQuestion(@PathVariable Long questionId, @Valid @RequestBody AnswerRequest answerRequest) {
        Answer answer = answerRequestMapper.toDomain(answerRequest);
        responseQuestionUseCase.respond(questionId, answer);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{categoryId}/questions")
    public ResponseEntity<Void> addQuestionToCategory(@PathVariable Long categoryId, @RequestBody CreateQuestionRequest question) {
        Question newQuestion = Question.builder()
                .statement(question.statement())
                .type(question.type())
                .categoryId(categoryId)
                .options(question.options())
                .build();

        addQuestionToCategoryUseCase.add(categoryId, newQuestion);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<List<QuestionResponse>> getQuestionaryByCategory(@PathVariable Long categoryId) {
        List<Question> questions = retrieveQuestionsFromCategoryUseCase.retrieve(categoryId);
        Map<Long, Double> percentagesByQuestionId = verifyAnswersUseCase.verifyByCategory(categoryId).stream()
                .collect(Collectors.toMap(
                        AnswerVerification::questionId,
                        AnswerVerification::percentage,
                        (existing, replacement) -> existing));

        return ResponseEntity.ok(questionResponseMapper.toResponse(questions, percentagesByQuestionId));
    }

    @GetMapping("/questions/{questionId}/answers")
    public ResponseEntity<List<AnswerResponse>> retrieveAnswersFromQuestion(@PathVariable Long questionId) {
        List<Answer> answers = retrieveAnswersFromQuestionUseCase.retrieve(questionId);
        Map<Long, Double> percentagesByAnswerId = verifyAnswersUseCase.verifyByQuestion(questionId).stream()
                .collect(Collectors.toMap(
                        AnswerVerification::answerId,
                        AnswerVerification::percentage,
                        (existing, replacement) -> existing));

        return ResponseEntity.ok(answerResponseMapper.toResponse(answers, percentagesByAnswerId));
    }
}
