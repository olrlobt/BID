package com.ssafy.bid.domain.user.repository;

import static com.ssafy.bid.domain.coupon.QCoupon.*;
import static com.ssafy.bid.domain.coupon.QUserCoupon.*;
import static com.ssafy.bid.domain.grade.QGrade.*;
import static com.ssafy.bid.domain.saving.QSaving.*;
import static com.ssafy.bid.domain.saving.QUserSaving.*;
import static com.ssafy.bid.domain.user.QAccount.*;
import static com.ssafy.bid.domain.user.QStudent.*;
import static com.ssafy.bid.domain.user.QUser.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.saving.dto.SavingTransferRequest;
import com.ssafy.bid.domain.user.AccountType;
import com.ssafy.bid.domain.user.User;
import com.ssafy.bid.domain.user.dto.AccountFindRequest;
import com.ssafy.bid.domain.user.dto.AccountFindResponse;
import com.ssafy.bid.domain.user.dto.AccountsFindResponse;
import com.ssafy.bid.domain.user.dto.StudentFindRequest;
import com.ssafy.bid.domain.user.dto.StudentFindResponse;
import com.ssafy.bid.domain.user.dto.StudentSalaryResponse;
import com.ssafy.bid.domain.user.dto.UserCouponsFindResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CoreUserRepositoryCustomImpl implements CoreUserRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<User> findById(String id) {
		return Optional.ofNullable(
			queryFactory
				.selectFrom(user)
				.where(user.id.eq(id))
				.fetchOne()
		);
	}

	@Override
	public Optional<StudentFindResponse> findStudentByUserNo(int userNo) {
		return Optional.ofNullable(
			queryFactory
				.select(Projections.constructor(StudentFindResponse.class,
						saving.no,
						saving.depositPeriod,
						saving.interestRate,
						userSaving.startPeriod,
						saving.depositPrice,
						userSaving.currentPrice,
						student.attendance.monday,
						student.attendance.tuesday,
						student.attendance.wednesday,
						student.attendance.thursday,
						student.attendance.friday,
						student.ballCount,
						student.expenditureStatistics.sumSnackExpenditure,
						student.expenditureStatistics.sumLearningExpenditure,
						student.expenditureStatistics.sumCouponExpenditure,
						student.expenditureStatistics.sumGameExpenditure,
						student.expenditureStatistics.sumEtcExpenditure,
						ExpressionUtils.as(
							JPAExpressions
								.select(account.price.sum())
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
								.select(account.price.sum())
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
				.leftJoin(userSaving).on(userSaving.userNo.eq(student.no))
				.leftJoin(saving).on(saving.no.eq(userSaving.savingNo))
				.where(student.no.eq(userNo))
				.fetchOne()
		);
	}

	@Override
	public List<UserCouponsFindResponse> findUserCouponsByUserNo(int userNo) {
		return queryFactory
			.select(Projections.constructor(UserCouponsFindResponse.class,
					coupon.name,
					userCoupon.no.count()
				)
			)
			.from(coupon)
			.leftJoin(userCoupon).on(userCoupon.couponNo.eq(coupon.no))
			.where(userCoupon.userNo.eq(userNo))
			.groupBy(userCoupon.couponNo)
			.fetch();
	}

	@Override
	public List<AccountsFindResponse> findAccountsByUserNo(int userNo, StudentFindRequest studentFindRequest) {
		return queryFactory
			.select(Projections.constructor(AccountsFindResponse.class,
					account.createdAt.dayOfMonth(),
					account.price.sum(),
					account.accountType,
					account.dealType
				)
			)
			.from(account)
			.where(
				account.userNo.eq(userNo),
				account.createdAt.goe(studentFindRequest.getStartDate().atStartOfDay()),
				account.createdAt.loe(studentFindRequest.getEndDate().atStartOfDay())
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
	public List<AccountFindResponse> findAccountByUserNo(int userNo, AccountFindRequest accountFindRequest) {
		return queryFactory
			.select(Projections.constructor(AccountFindResponse.class,
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
					accountFindRequest.getDate().atStartOfDay(),
					accountFindRequest.getDate().plusDays(1).atStartOfDay()
				)
			)
			.orderBy(account.createdAt.desc())
			.fetch();
	}

	@Override
	public List<SavingTransferRequest> findAllByIds(List<Integer> userNos) {
		return queryFactory
			.select(Projections.constructor(SavingTransferRequest.class,
					saving.depositPrice,
					student
				)
			)
			.from(student)
			.innerJoin(userSaving).on(userSaving.userNo.eq(student.no))
			.innerJoin(saving).on(saving.no.eq(userSaving.savingNo))
			.where(student.no.in(userNos))
			.fetch();
	}

	@Override
	public List<StudentSalaryResponse> findAllStudentsAndSalaries() {
		return queryFactory
			.select(Projections.constructor(StudentSalaryResponse.class,
					student,
					grade.salary
				)
			)
			.from(student)
			.innerJoin(grade).on(grade.no.eq(student.gradeNo))
			.fetch();
	}
}
