import React from 'react';
import { ResponsivePie } from '@nivo/pie';
import styled from './PieChart.module.css';


const Piechart = () => {
    const handle = {
        padClick: (data) => {
            console.log(data);
        },

        legendClick: (data) => {
            console.log(data);
        },
    };


    return (
        <div className={styled.PieContainer}>
        <div style={{ width: '20vw', height: '20vh' }}>
            <ResponsivePie
                data={[
                    { id: 'cola', value: 324 },
                    { id: 'cidar', value: 88 },
                    { id: 'fanta', value: 221 },
                ]}
                margin={{ top: 30, right: 100, bottom: 30, left: 30 }}
                innerRadius={0.04}
                colors={['olive', 'brown', 'orange']}
                borderWidth={2}
                arcLinkLabelsSkipAngle={0}
                arcLinkLabelsColor={{ from: 'color' }}
                arcLabelsSkipAngle={10}
                theme={{
                    labels: {
                        text: {
                            fontSize: 10,
                            fill: '#000000',
                        },
                    },
                    legends: {
                        text: {
                            fontSize: 10,
                            fill: '#000000',
                        },
                    },
                }}
                onClick={handle.padClick}
                legends={[
                    {
                        anchor: 'bottom',
                        direction: 'row',
                        justify: false,
                        translateX: 0,
                        translateY: 26,
                        itemsSpacing: 0,
                        itemWidth: 50,
                        itemHeight: 20,
                        itemDirection: 'left-to-right',
                        itemOpacity: 1,
                        symbolSize: 18,
                        symbolShape: 'circle',
                        effects: [
                            {
                                on: 'hover',
                                style: {
                                    itemTextColor: 'olive',
                                },
                            },
                        ],
                        onClick: handle.legendClick,
                    },
                ]}
            />
            <div>소비 카테고리 통계</div>
        </div>
        </div>
    );
};

export default Piechart;
