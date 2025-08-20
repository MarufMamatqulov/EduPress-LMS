package com.edupress.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class LessonCriteriaTest {

    @Test
    void newLessonCriteriaHasAllFiltersNullTest() {
        var lessonCriteria = new LessonCriteria();
        assertThat(lessonCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void lessonCriteriaFluentMethodsCreatesFiltersTest() {
        var lessonCriteria = new LessonCriteria();

        setAllFilters(lessonCriteria);

        assertThat(lessonCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void lessonCriteriaCopyCreatesNullFilterTest() {
        var lessonCriteria = new LessonCriteria();
        var copy = lessonCriteria.copy();

        assertThat(lessonCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(lessonCriteria)
        );
    }

    @Test
    void lessonCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var lessonCriteria = new LessonCriteria();
        setAllFilters(lessonCriteria);

        var copy = lessonCriteria.copy();

        assertThat(lessonCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(lessonCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var lessonCriteria = new LessonCriteria();

        assertThat(lessonCriteria).hasToString("LessonCriteria{}");
    }

    private static void setAllFilters(LessonCriteria lessonCriteria) {
        lessonCriteria.id();
        lessonCriteria.title();
        lessonCriteria.videoUrl();
        lessonCriteria.duration();
        lessonCriteria.type();
        lessonCriteria.isFree();
        lessonCriteria.sortOrder();
        lessonCriteria.isPublished();
        lessonCriteria.courseId();
        lessonCriteria.distinct();
    }

    private static Condition<LessonCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getTitle()) &&
                condition.apply(criteria.getVideoUrl()) &&
                condition.apply(criteria.getDuration()) &&
                condition.apply(criteria.getType()) &&
                condition.apply(criteria.getIsFree()) &&
                condition.apply(criteria.getSortOrder()) &&
                condition.apply(criteria.getIsPublished()) &&
                condition.apply(criteria.getCourseId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<LessonCriteria> copyFiltersAre(LessonCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getTitle(), copy.getTitle()) &&
                condition.apply(criteria.getVideoUrl(), copy.getVideoUrl()) &&
                condition.apply(criteria.getDuration(), copy.getDuration()) &&
                condition.apply(criteria.getType(), copy.getType()) &&
                condition.apply(criteria.getIsFree(), copy.getIsFree()) &&
                condition.apply(criteria.getSortOrder(), copy.getSortOrder()) &&
                condition.apply(criteria.getIsPublished(), copy.getIsPublished()) &&
                condition.apply(criteria.getCourseId(), copy.getCourseId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
