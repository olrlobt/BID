package com.ssafy.bid.domain.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBidding is a Querydsl query type for Bidding
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBidding extends EntityPathBase<Bidding> {

    private static final long serialVersionUID = -1514869471L;

    public static final QBidding bidding = new QBidding("bidding");

    public final com.ssafy.bid.domain.common.QBaseEntity _super = new com.ssafy.bid.domain.common.QBaseEntity(this);

    public final EnumPath<BiddingStatus> biddingStatus = createEnum("biddingStatus", BiddingStatus.class);

    public final NumberPath<Long> boardNo = createNumber("boardNo", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> no = createNumber("no", Long.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Integer> userNo = createNumber("userNo", Integer.class);

    public QBidding(String variable) {
        super(Bidding.class, forVariable(variable));
    }

    public QBidding(Path<? extends Bidding> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBidding(PathMetadata metadata) {
        super(Bidding.class, metadata);
    }

}

