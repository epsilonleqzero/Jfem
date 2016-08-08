/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

a=[]; b=[]; c=[];
for(i=0;i<50;i++)
  {
    var a_ = Math.random();
    a.push(a_);

    var b_ = Math.random();
    b.push(b_);

    var c_ = Math.random();
    c.push(c_);

  }
var data=[
	{
		opacity:0.8,
		color:'rgb(300,100,200)',
       type: 'mesh3d',
        x: a,
        y: b,
        z: c
    }
];
	  Plotly.newPlot('myDiv', data);