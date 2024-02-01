package com.ssafy.bid.domain.saving;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSaving is a Querydsl query type for Saving
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSaving extends EntityPathBase<Saving> {

    private static final long serialVersionUID = 1426390364L;

    public static final QSaving saving = new QSaving("saving");

    public final NumberPath<Integer> depositCycle = createNumber("depositCycle", Integer.class);

    public final NumberPath<Integer> depositPeriod = createNumber("depositPeriod", Integer.class);

    public final NumberPath<Integer> depositPrice = createNumber("depositPrice", Integer.class);

    public final NumberPath<Integer> gradeNo = createNumber("gradeNo", Integer.class);

    public final NumberPath<Integer> interestRate = createNumber("interestRate", Integer.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final StringPath terms = createString("terms");

    public QSaving(String variable) {
        super(Saving.class, forVariable(variable));
    }

    public QSaving(Path<? extends Saving> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSaving(PathMetadata metadata) {
        super(Saving.class, metadata);
    }

}

