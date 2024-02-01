package com.ssafy.bid.domain.gradeperiod;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGradePeriod is a Querydsl query type for GradePeriod
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGradePeriod extends EntityPathBase<GradePeriod> {

    private static final long serialVersionUID = 133408528L;

    public static final QGradePeriod gradePeriod = new QGradePeriod("gradePeriod");

    public final TimePath<java.time.LocalTime> endPeriod = createTime("endPeriod", java.time.LocalTime.class);

    public final NumberPath<Integer> gradeNo = createNumber("gradeNo", Integer.class);

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final NumberPath<Integer> sequence = createNumber("sequence", Integer.class);

    public final TimePath<java.time.LocalTime> startPeriod = createTime("startPeriod", java.time.LocalTime.class);

    public QGradePeriod(String variable) {
        super(GradePeriod.class, forVariable(variable));
    }

    public QGradePeriod(Path<? extends GradePeriod> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGradePeriod(PathMetadata metadata) {
        super(GradePeriod.class, metadata);
    }

}

