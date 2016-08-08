/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


TESTER = document.getElementById('myDiv');

Plotly.plot(TESTER, [{
        x: [1, 2, 3, 4, 5],
        y: [1, 2, 4, 8, 16]}], {
    margin: {t: 0}});

/* Current Plotly.js version */
console.log(Plotly.BUILD);