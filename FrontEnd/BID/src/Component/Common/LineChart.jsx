import { ResponsiveLine } from '@nivo/line';
import styled from '../../Page/Main/Home.module.css';

// make sure parent container have a defined height when using
// responsive component, otherwise height will be 0 and
// no chart will be rendered.
// website examples showcase many properties,
// you'll often use just a few of them.
export default function LineChart({ data }) {
  const prettyData = data[0].data;
  const min = Math.min(...prettyData.map((v) => v.y));
  const max = Math.max(...prettyData.map((v) => v.y));

  return (
    <div className={styled.line}>
      <ResponsiveLine
        className={styled.graph}
        data={data}
        margin={{ top: 30, bottom: 30, left: 60 }}
        xScale={{ type: 'point' }}
        yScale={{
          type: 'linear',
          min: min,
          max: max,
          stacked: true,
          reverse: false,
        }}
        curve="monotoneX"
        axisTop={null}
        axisRight={null}
        axisBottom={{
          tickSize: 5,
          tickPadding: 5,
          tickRotation: 0,
          legendOffset: 36,
          legendPosition: 'middle',
        }}
        axisLeft={{
          tickSize: 5,
          tickPadding: 5,
          tickValues: 5,
          tickRotation: 0,
          legendOffset: -40,
          legendPosition: 'middle',
        }}
        enablePoints={false}
        pointSize={10}
        pointColor={{ theme: 'background' }}
        pointBorderWidth={2}
        pointBorderColor={{ from: 'serieColor' }}
        pointLabelYOffset={-12}
        useMesh={true}
      />
      <div className={styled.category}>거래량 추이</div>
    </div>
  );
}
