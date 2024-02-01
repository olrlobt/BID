package com.ssafy.bid.domain.reward;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReward is a Querydsl query type for Reward
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReward extends EntityPathBase<Reward> {

    private static final long serialVersionUID = -1881946180L;

    public static final QReward reward = new QReward("reward");

    public final StringPath description = createString("description");

    public final NumberPath<Integer> gradeNo = createNumber("gradeNo", Integer.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public QReward(String variable) {
        super(Reward.class, forVariable(variable));
    }

    public QReward(Path<? extends Reward> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReward(PathMetadata metadata) {
        super(Reward.class, metadata);
    }

}

