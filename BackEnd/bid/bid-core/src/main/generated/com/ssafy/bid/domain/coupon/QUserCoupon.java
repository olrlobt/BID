package com.ssafy.bid.domain.coupon;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserCoupon is a Querydsl query type for UserCoupon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserCoupon extends EntityPathBase<UserCoupon> {

    private static final long serialVersionUID = 1796004999L;

    public static final QUserCoupon userCoupon = new QUserCoupon("userCoupon");

    public final NumberPath<Integer> couponNo = createNumber("couponNo", Integer.class);

    public final NumberPath<Long> no = createNumber("no", Long.class);

    public final NumberPath<Integer> userNo = createNumber("userNo", Integer.class);

    public final EnumPath<UsageStatus> useStatus = createEnum("useStatus", UsageStatus.class);

    public QUserCoupon(String variable) {
        super(UserCoupon.class, forVariable(variable));
    }

    public QUserCoupon(Path<? extends UserCoupon> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserCoupon(PathMetadata metadata) {
        super(UserCoupon.class, metadata);
    }

}

