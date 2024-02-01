package com.ssafy.bid.domain.grade;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGrade is a Querydsl query type for Grade
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGrade extends EntityPathBase<Grade> {

    private static final long serialVersionUID = 1223275310L;

    public static final QGrade grade = new QGrade("grade");

    public final com.ssafy.bid.domain.common.QBaseEntity _super = new com.ssafy.bid.domain.common.QBaseEntity(this);

    public final NumberPath<Integer> asset = createNumber("asset", Integer.class);

    public final NumberPath<Integer> classRoom = createNumber("classRoom", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final NumberPath<Integer> salary = createNumber("salary", Integer.class);

    public final StringPath schoolCode = createString("schoolCode");

    public final TimePath<java.time.LocalTime> transferAlertPeriod = createTime("transferAlertPeriod", java.time.LocalTime.class);

    public final TimePath<java.time.LocalTime> transferPeriod = createTime("transferPeriod", java.time.LocalTime.class);

    public final NumberPath<Integer> userNo = createNumber("userNo", Integer.class);

    public final NumberPath<Integer> year = createNumber("year", Integer.class);

    public QGrade(String variable) {
        super(Grade.class, forVariable(variable));
    }

    public QGrade(Path<? extends Grade> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGrade(PathMetadata metadata) {
        super(Grade.class, metadata);
    }

}

