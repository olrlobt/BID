import React from 'react';
import { ResponsivePie } from '@nivo/pie';

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
        <div style={{ width: '30vw', height: '50vh', margin: '0 auto' }}>
            <ResponsivePie
                data={[
                    { id: 'cola', value: 324 },
                    { id: 'cidar', value: 88 },
                    { id: 'fanta', value: 221 },
                ]}
                margin={{ top: 40, right: 80, bottom: 80, left: 80 }}
                innerRadius={0.5}
                padAngle={1.8}
                cornerRadius={8}
                colors={['olive', 'brown', 'orange']}
                borderWidth={2}
                arcLinkLabelsSkipAngle={0}
                arcLinkLabelsTextColor="#000000"
                arcLinkLabelsThickness={2}
                arcLinkLabelsColor={{ from: 'color' }}
                arcLabelsSkipAngle={10}
                theme={{
                    labels: {
                        text: {
                            fontSize: 14,
                            fill: '#000000',
                        },
                    },
                    legends: {
                        text: {
                            fontSize: 12,
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
                        translateY: 56,
                        itemsSpacing: 0,
                        itemWidth: 100,
                        itemHeight: 18,
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
        </div>
    );
};

export default Piechart;
