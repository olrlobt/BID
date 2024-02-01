package com.ssafy.bid.domain.saving;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserSaving is a Querydsl query type for UserSaving
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserSaving extends EntityPathBase<UserSaving> {

    private static final long serialVersionUID = 1118146567L;

    public static final QUserSaving userSaving = new QUserSaving("userSaving");

    public final DateTimePath<java.time.LocalDateTime> endPeriod = createDateTime("endPeriod", java.time.LocalDateTime.class);

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final NumberPath<Integer> resultPrice = createNumber("resultPrice", Integer.class);

    public final NumberPath<Integer> savingNo = createNumber("savingNo", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> startPeriod = createDateTime("startPeriod", java.time.LocalDateTime.class);

    public final NumberPath<Integer> userNo = createNumber("userNo", Integer.class);

    public QUserSaving(String variable) {
        super(UserSaving.class, forVariable(variable));
    }

    public QUserSaving(Path<? extends UserSaving> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserSaving(PathMetadata metadata) {
        super(UserSaving.class, metadata);
    }

}

