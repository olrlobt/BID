import { ResponsivePie } from "@nivo/pie";
import styled from "../../Page/Main/Home.module.css";

export default function PieChart({ data }) {
  const [totalCategorySum, couponSum, etcSum, gameSum, learningSum, snackSum] =
    data;

  const dataFormat = [
    {
      id: "간식",
      label: "간식",
      value: snackSum,
      color: "hsl(150, 70%, 50%)",
    },
    {
      id: "학습",
      label: "학습",
      value: learningSum,
      color: "hsl(66, 70%, 50%)",
    },
    {
      id: "쿠폰",
      label: "쿠폰",
      value: couponSum,
      color: "hsl(329, 70%, 50%)",
    },
    {
      id: "대포",
      label: "대포",
      value: gameSum,
      color: "hsl(166, 70%, 50%)",
    },
    {
      id: "기타",
      label: "기타",
      value: etcSum,
      color: "hsl(0, 100%, 80%)",
    },
  ];
  return (
    <div className={styled.line}>
      <ResponsivePie
        className={styled.graph}
        data={dataFormat}
        margin={{ top: 30, bottom: 10 }}
        fit={false}
        activeOuterRadiusOffset={11}
        colors={{ scheme: "orange_red" }}
        borderColor={{
          from: "color",
          modifiers: [["darker", 0.1]],
        }}
        enableArcLinkLabels={false}
        arcLinkLabelsSkipAngle={10}
        arcLinkLabelsTextColor="#333333"
        arcLinkLabelsThickness={2}
        arcLinkLabelsColor={{ from: "color" }}
        arcLabelsSkipAngle={10}
        arcLabel={(e) =>
          e.id + parseInt((e.value / totalCategorySum) * 100) + "%"
        }
        defs={[
          {
            id: "dots",
            type: "patternDots",
            background: "inherit",
            color: "rgba(255, 255, 255, 0.3)",
            size: 4,
            padding: 1,
            stagger: true,
          },
          {
            id: "lines",
            type: "patternLines",
            background: "inherit",
            color: "rgba(255, 255, 255, 0.3)",
            rotation: -45,
            lineWidth: 6,
            spacing: 10,
          },
        ]}
      />
      <div className={styled.category}>소비 카테고리</div>
    </div>
  );
}
