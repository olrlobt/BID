package com.ssafy.bid.domain.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = -55031284L;

    public static final QBoard board = new QBoard("board");

    public final com.ssafy.bid.domain.common.QBaseEntity _super = new com.ssafy.bid.domain.common.QBaseEntity(this);

    public final NumberPath<Integer> attendeeCount = createNumber("attendeeCount", Integer.class);

    public final EnumPath<BoardStatus> boardStatus = createEnum("boardStatus", BoardStatus.class);

    public final EnumPath<Category> category = createEnum("category", Category.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final StringPath description = createString("description");

    public final StringPath goodsImgUrl = createString("goodsImgUrl");

    public final NumberPath<Integer> gradePeriodNo = createNumber("gradePeriodNo", Integer.class);

    public final NumberPath<Long> no = createNumber("no", Long.class);

    public final NumberPath<Integer> resultPrice = createNumber("resultPrice", Integer.class);

    public final NumberPath<Integer> startPrice = createNumber("startPrice", Integer.class);

    public final StringPath title = createString("title");

    public final NumberPath<Integer> totalPrice = createNumber("totalPrice", Integer.class);

    public final NumberPath<Integer> userNo = createNumber("userNo", Integer.class);

    public QBoard(String variable) {
        super(Board.class, forVariable(variable));
    }

    public QBoard(Path<? extends Board> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoard(PathMetadata metadata) {
        super(Board.class, metadata);
    }

}

