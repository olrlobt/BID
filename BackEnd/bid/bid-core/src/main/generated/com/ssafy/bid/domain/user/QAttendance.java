package com.ssafy.bid.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAttendance is a Querydsl query type for Attendance
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QAttendance extends BeanPath<Attendance> {

    private static final long serialVersionUID = 1675956826L;

    public static final QAttendance attendance = new QAttendance("attendance");

    public final BooleanPath friday = createBoolean("friday");

    public final BooleanPath monday = createBoolean("monday");

    public final BooleanPath thursday = createBoolean("thursday");

    public final BooleanPath tuesday = createBoolean("tuesday");

    public final BooleanPath wednesday = createBoolean("wednesday");

    public QAttendance(String variable) {
        super(Attendance.class, forVariable(variable));
    }

    public QAttendance(Path<? extends Attendance> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAttendance(PathMetadata metadata) {
        super(Attendance.class, metadata);
    }

}

