import React from "react";
import styled from "./CouponList.module.css"
import Coupon from "./Coupon";
import { Droppable, Draggable } from "react-beautiful-dnd";

export default function CouponList(props){
	const { title, coupons } = props;
	
	return(
		<Droppable droppableId = {title}>
			{(provided, snapshot) => (
			<div
				className = {styled.couponListWrapper}
				ref={provided.innerRef}
				style = {{background: snapshot.isDraggingOver ? '#ececec' : 'white'}}
				{...provided.droppableProps}
			>
				{
					coupons.map((coupon) =>
						<Draggable
							draggableId={""+coupon.no}
							index={coupon.no}
							key={coupon.no}
						>
						{
							(provided) => (
								<div
								className = {styled.draggableWrapper}
									ref={provided.innerRef}
									{...provided.draggableProps}
									{...provided.dragHandleProps}
								>
									<Coupon
									key = {coupon.no}
									no = {coupon.no}
									name = {coupon.name}
									description = {coupon.description}
									startPrice = {coupon.startPrice}
									
								/>
								</div>
								
							)
						}
						</Draggable>
					)
				}
				{provided.placeholder}
			</div>
			)}
		</Droppable>
	);
}