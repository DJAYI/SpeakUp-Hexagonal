package com.electiva.speakup.domain.model.answer;

import com.electiva.speakup.domain.model.Option;
import com.electiva.speakup.domain.model.Question;

import java.util.Set;
import java.util.stream.Collectors;

public final class AnswerScorer {

    private AnswerScorer() {
    }

    public static double percentage(Question question, Answer answer) {
        if (question == null || answer == null || question.getType() == null) {
            return 0.0;
        }

        return switch (question.getType()) {
            case FREE_TEXT -> freeTextPercentage(answer);
            case SINGLE_CHOICE -> singleChoicePercentage(question, answer);
            case MULTIPLE_CHOICE -> multipleChoicePercentage(question, answer);
        };
    }

    private static double freeTextPercentage(Answer answer) {
        if (answer instanceof FreeTextAnswer freeText
                && freeText.getText() != null
                && !freeText.getText().isBlank()) {
            return 100.0;
        }

        return 0.0;
    }

    private static double singleChoicePercentage(Question question, Answer answer) {
        if (!(answer instanceof SingleOptionAnswer singleOption)) {
            return 0.0;
        }

        Set<Long> correctOptionIds = correctOptionIds(question);
        return correctOptionIds.contains(singleOption.getOptionId()) ? 100.0 : 0.0;
    }

    private static double multipleChoicePercentage(Question question, Answer answer) {
        if (!(answer instanceof MultpleOptionAnswer multipleOption)) {
            return 0.0;
        }

        Set<Long> correctOptionIds = correctOptionIds(question);
        if (correctOptionIds.isEmpty() || multipleOption.getOptionIds() == null) {
            return 0.0;
        }

        long matchedOptions = multipleOption.getOptionIds().stream()
                .filter(correctOptionIds::contains)
                .distinct()
                .count();

        return round((matchedOptions * 100.0) / correctOptionIds.size());
    }

    private static Set<Long> correctOptionIds(Question question) {
        if (question.getOptions() == null) {
            return Set.of();
        }

        return question.getOptions().stream()
                .filter(Option::isCorrect)
                .map(Option::getId)
                .filter(id -> id != null)
                .collect(Collectors.toSet());
    }

    private static double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
