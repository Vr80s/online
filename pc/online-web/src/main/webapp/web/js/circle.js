function ccjindu(data1,data2){
	var doughnutData = [{
		value: data1,
		color: "#ff4012"
	}, {
		value: data2,
		color: "#fafafa"
	}

];
var c=document.getElementById("circle");
c.width=170;
c.height=170;
var myDoughnut = new Chart(document.getElementById("circle").getContext("2d")).Doughnut(doughnutData);
//	myDoughnut.defaults = {
//	//Boolean - Whether we should show a stroke on each segment
//	segmentShowStroke : true,
//	
//	//String - The colour of each segment stroke
//	segmentStrokeColor : "#fff",
//	
//	//Number - The width of each segment stroke
//	segmentStrokeWidth : 200,
//	
//	//The percentage of the chart that we cut out of the middle.
//	percentageInnerCutout : 1000,
//	
//	//Boolean - Whether we should animate the chart	
//	animation : true,
//	
//	//Number - Amount of animation steps
//	animationSteps : 100,
//	
//	//String - Animation easing effect
//	animationEasing : "easeOutBounce",
//	
//	//Boolean - Whether we animate the rotation of the Doughnut
//	animateRotate : true,
//
//	//Boolean - Whether we animate scaling the Doughnut from the centre
//	animateScale : false,
//	
//	//Function - Will fire on animation completion.
//	onAnimationComplete : null
//}
}


























//$(function() {
//	drawCircle("circle", [0.3, 0.3, 0.4], ["#fe9e30", "#a8d65a", "#478bd0"], ["a", "b", "c"]);
//
//});
//function drawCircle(canvasId, data_arr, color_arr, text_arr) {
//		var pi2 = Math.PI * 2;
//
//		var canvas = document.getElementById(canvasId);
//		canvas.width = 400;
//		canvas.height = 400;
//		var c= canvas.getContext("2d");
//		var startAgl = 30;
//		var agl;
//		for (var i = 0; i < data_arr.length; i++) {
//			//绘制饼图
//			agl = data_arr[i] * pi2 + startAgl;
//
//
//			c.beginPath();
//
//			c.strokeStyle = "#000000";
//			c.fillStyle = color_arr[i];
//			c.moveTo(200, 200);
//			c.arc(200, 200, 200, startAgl, agl, false);
//			c.lineTo(200, 200);
//			c.closePath();
//			c.stroke();
//			c.fill();
//			startAgl = agl;
//			//绘制图例
//
//			c.fillRect(360, 50 + 18 * i, 16, 16);
//
//			c.fillStyle = "#000000";
//			c.fillText(text_arr[i], 380, 62 + 18 * i);
//			//c.rotate(20*i*Math.PI/180);
//		}
//	}