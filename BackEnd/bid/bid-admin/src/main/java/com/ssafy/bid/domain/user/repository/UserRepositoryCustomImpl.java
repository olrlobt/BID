package com.ssafy.bid.domain.user.repository;

import static com.querydsl.core.types.ExpressionUtils.*;
import static com.ssafy.bid.domain.coupon.QCoupon.*;
import static com.ssafy.bid.domain.coupon.QUserCoupon.*;
import static com.ssafy.bid.domain.saving.QSaving.*;
import static com.ssafy.bid.domain.saving.QUserSaving.*;
import static com.ssafy.bid.domain.user.QAccount.*;
import static com.ssafy.bid.domain.user.QStudent.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.user.AccountType;
import com.ssafy.bid.domain.user.DealType;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.dto.AccountRequest;
import com.ssafy.bid.domain.user.dto.AccountResponse;
import com.ssafy.bid.domain.user.dto.AccountsResponse;
import com.ssafy.bid.domain.user.dto.StudentRequest;
import com.ssafy.bid.domain.user.dto.StudentResponse;
import com.ssafy.bid.domain.user.dto.StudentsResponse;
import com.ssafy.bid.domain.user.dto.UserCouponsResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<StudentsResponse> findStudents(int gradeNo) {
		return queryFactory.select(Projections.constructor(StudentsResponse.class,
					student.no,
					student.id,
					student.name,
					student.asset
				)
			)
			.from(student)
			.where(
				student.gradeNo.eq(gradeNo),
				student.deletedAt.isNull()
			)
			.fetch();
	}

	@Override
	public List<UserCouponsResponse> findUserCoupons(int userNo) {
		return queryFactory
			.select(Projections.constructor(UserCouponsResponse.class,
					coupon.name,
					count(userCoupon.no)
				)
			)
			.from(coupon)
			.innerJoin(userCoupon).on(userCoupon.couponNo.eq(coupon.no))
			.where(userCoupon.userNo.eq(userNo))
			.groupBy(userCoupon.couponNo)
			.fetch();
	}

	@Override
	public List<AccountsResponse> findAccounts(int userNo, StudentRequest studentRequest) {
		return queryFactory
			.select(Projections.constructor(AccountsResponse.class,
					account.createdAt.dayOfMonth(),
					account.price.sum(),
					account.accountType,
					account.dealType
				)
			)
			.from(account)
			.where(
				account.userNo.eq(userNo),
				account.createdAt.goe(studentRequest.getStartDate().atStartOfDay()),
				account.createdAt.loe(studentRequest.getEndDate().atStartOfDay())
			)
			.groupBy(
				account.createdAt.dayOfMonth(),
				account.accountType,
				account.dealType
			)
			.orderBy(account.createdAt.dayOfMonth().desc())
			.fetch();
	}

	@Override
	public Optional<StudentResponse> findStudent(int userNo) {
		return Optional.ofNullable(
			queryFactory
				.select(Projections.constructor(StudentResponse.class,
						saving.no,
						saving.depositPeriod,
						saving.interestRate,
						userSaving.startPeriod,
						saving.depositPrice,
						userSaving.resultPrice,
						student.attendance.monday,
						student.attendance.tuesday,
						student.attendance.wednesday,
						student.attendance.thursday,
						student.attendance.friday,
						student.ballCount,
						ExpressionUtils.as(
							JPAExpressions
								.select(
									account.price.sum()
								)
								.from(account)
								.where(
									account.userNo.eq(student.no),
									account.accountType.eq(AccountType.EXPENDITURE),
									account.dealType.notIn(DealType.REWARD, DealType.SALARY, DealType.SAVING),
									account.createdAt.goe(LocalDateTime.now().minusDays(14)),
									account.createdAt.loe(LocalDateTime.now())
								)
							, "totalCategorySum"
						),
						ExpressionUtils.as(
							JPAExpressions
								.select(
									account.price.sum()
								)
								.from(account)
								.where(
									account.userNo.eq(student.no),
									account.accountType.eq(AccountType.EXPENDITURE),
									account.createdAt.goe(LocalDateTime.now().minusDays(14)),
									account.createdAt.loe(LocalDateTime.now())
								)
								.groupBy(account.accountType, account.dealType)
								.having(account.dealType.eq(DealType.SNACK))
							, "snackSum"
						),
						ExpressionUtils.as(
							JPAExpressions
								.select(
									account.price.sum()
								)
								.from(account)
								.where(
									account.userNo.eq(student.no),
									account.accountType.eq(AccountType.EXPENDITURE),
									account.createdAt.goe(LocalDateTime.now().minusDays(14)),
									account.createdAt.loe(LocalDateTime.now())
								)
								.groupBy(account.accountType, account.dealType)
								.having(account.dealType.eq(DealType.LEARNING))
							, "learningSum"
						),
						ExpressionUtils.as(
							JPAExpressions
								.select(
									account.price.sum()
								)
								.from(account)
								.where(
									account.userNo.eq(student.no),
									account.accountType.eq(AccountType.EXPENDITURE),
									account.createdAt.goe(LocalDateTime.now().minusDays(14)),
									account.createdAt.loe(LocalDateTime.now())
								)
								.groupBy(account.accountType, account.dealType)
								.having(account.dealType.eq(DealType.COUPON))
							, "couponSum"
						),
						ExpressionUtils.as(
							JPAExpressions
								.select(
									account.price.sum()
								)
								.from(account)
								.where(
									account.userNo.eq(student.no),
									account.accountType.eq(AccountType.EXPENDITURE),
									account.createdAt.goe(LocalDateTime.now().minusDays(14)),
									account.createdAt.loe(LocalDateTime.now())
								)
								.groupBy(account.accountType, account.dealType)
								.having(account.dealType.eq(DealType.GAME))
							, "gameSum"
						),
						ExpressionUtils.as(
							JPAExpressions
								.select(
									account.price.sum()
								)
								.from(account)
								.where(
									account.userNo.eq(student.no),
									account.accountType.eq(AccountType.EXPENDITURE),
									account.createdAt.goe(LocalDateTime.now().minusDays(14)),
									account.createdAt.loe(LocalDateTime.now())
								)
								.groupBy(account.accountType, account.dealType)
								.having(account.dealType.eq(DealType.ETC))
							, "etcSum"
						),
						ExpressionUtils.as(
							JPAExpressions
								.select(
									account.price.sum()
								)
								.from(account)
								.where(
									account.userNo.eq(student.no),
									account.createdAt.goe(LocalDateTime.now().minusDays(14)),
									account.createdAt.loe(LocalDateTime.now())
								)
								.groupBy(account.accountType)
								.having(account.accountType.eq(AccountType.INCOME))
							, "totalIncome"
						),
						ExpressionUtils.as(
							JPAExpressions
								.select(
									account.price.sum()
								)
								.from(account)
								.where(
									account.userNo.eq(student.no),
									account.createdAt.goe(LocalDateTime.now().minusDays(14)),
									account.createdAt.loe(LocalDateTime.now())
								)
								.groupBy(account.accountType)
								.having(account.accountType.eq(AccountType.EXPENDITURE))
							, "totalExpense"
						)
					)
				)
				.from(student)
				.innerJoin(userSaving).on(userSaving.userNo.eq(student.no))
				.innerJoin(saving).on(saving.no.eq(userSaving.savingNo))
				.where(student.no.eq(userNo))
				.fetchOne()
		);
	}

	@Override
	public List<AccountResponse> findAccount(int userNo, AccountRequest accountRequest) {
		return queryFactory
			.select(Projections.constructor(AccountResponse.class,
					account.accountType,
					account.price,
					account.content,
					account.dealType,
					account.createdAt
				)
			)
			.from(account)
			.where(
				account.userNo.eq(userNo),
				account.createdAt.between(
					accountRequest.getDate().atStartOfDay(),
					accountRequest.getDate().plusDays(1).atStartOfDay()
				)
			)
			.orderBy(account.createdAt.desc())
			.fetch();
	}

	@Override
	public List<Student> findAllByIds(List<Integer> userNos) {
		return queryFactory
			.selectFrom(student)
			.where(student.no.in(userNos))
			.fetch();
	}
}
