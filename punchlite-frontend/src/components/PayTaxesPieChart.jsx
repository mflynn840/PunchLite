/** Use an external library (recharts)
 * to render a pie chart showing pay(green)/taxes(red)
 */

import React from 'react'
import {PieChart, Pie, Cell, Legend, Tooltip} from 'recharts';

const COLORS = ["green", "red"];

function PayTaxesPieChart({pay, taxes}) {

    //json pay and taxes
    const data = [
        { name: 'Pay', value: pay},
        { name: 'Taxes', value: taxes},
    ];

    //render a pie chart using recharts
    return(
        <div style={{ width: 200, height: 200, userSelect: 'none' }}>
            <PieChart width={200} height={200}>
                <Pie
                data={data}
                dataKey="value"
                nameKey="name"
                cx="50%"
                cy="50%"
                outerRadius={80}
                fill="#8884d8"
                label={({ name, percent }) =>
                    `${name}: ${(percent * 100).toFixed(0)}%`
                }
                >
                {data.map((entry, index) => (
                    <Cell key={`slice-${index}`} fill={COLORS[index]} />
                ))}
                </Pie>
                <Tooltip />
                <Legend />
            </PieChart>
        </div> 
    );
}

export default PayTaxesPieChart;