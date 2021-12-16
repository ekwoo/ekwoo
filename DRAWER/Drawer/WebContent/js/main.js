let canvas
let ctx
//캔버스 중심
const center = {
	x:0,
	y:0,
}
//드래그된 위치
const trans = {
	x:0,
	y:0,
}
//드래그 한계
const transLimit = {
		minX:0,
		maxX:0,
		minY:0,
		maxY:0,
	}
//기본 드래드 툴..?
let tool = (function(){
	const mouseStart = {
		x:0,
		y:0,
	}
	const transStart = {
		x:0,
		y:0,
	}
	return {
		onmousedown : function(e){
			mouseStart.x = e.clientX
			mouseStart.y = e.clientY
			transStart.x = trans.x
			transStart.y = trans.y
			window.addEventListener('mousemove', this.onmousemove)
		},
		onmousemove : function(e){
			trans.x = transStart.x + e.clientX - mouseStart.x
			trans.y = transStart.y + e.clientY - mouseStart.y
			if(trans.x < transLimit.minX){
				mouseStart.x -= (transLimit.minX - trans.x)
				trans.x = transLimit.minX
			}
			if(transLimit.maxX < trans.x){
				mouseStart.x += (trans.x - transLimit.maxX)
				trans.x = transLimit.maxX
			}
			if(trans.y < transLimit.minY){
				mouseStart.y -= (transLimit.minY - trans.y)
				trans.y = transLimit.minY
			}
			if(transLimit.maxY < trans.y){
				mouseStart.y += (trans.y - transLimit.maxY)
				trans.y = transLimit.maxY
			}
			canvas.style.transform = 'translate('+trans.x+'px, '+trans.y+'px)'
		},
		onmouseup : function(e){
			window.removeEventListener('mousemove', this.onmousemove)
		},
	}
})()
window.onload = function(){
	canvas = document.getElementById("canvas")
	ctx = canvas.getContext("2d")
	
	canvas.width = window.innerWidth * 2
	canvas.height = window.innerHeight * 2
	canvas.style.width = canvas.width+"px"
	canvas.style.height = canvas.height+"px"
	canvas.style.left = -canvas.width/4+"px"
	canvas.style.top = -canvas.height/4+"px"
	center.x = canvas.width/2
	center.y = canvas.height/2
	
	drawAxis()
	calcLimit()
	
	window.onmousedown = function(e){
		tool.onmousedown(e)
	}
	window.onmouseup = function(e){
		tool.onmouseup(e)
	}
	window.onresize = function(){
		canvas.style.left = -(canvas.width - window.innerWidth)/2+"px"
		canvas.style.top = -(canvas.height - window.innerHeight)/2+"px"
		calcLimit()
	}
}
function drawAxis(){
	ctx.moveTo(0, center.y)
	ctx.lineTo(canvas.width, center.y)
	ctx.moveTo(center.x, 0)
	ctx.lineTo(center.x, canvas.height)
	ctx.stroke()
}
function calcLimit(){
	transLimit.maxX = Math.max(0, (canvas.width - window.innerWidth)/2)
	transLimit.minX = -transLimit.maxX
	transLimit.maxY =  Math.max(0, (canvas.height - window.innerHeight)/2)
	transLimit.minY = -transLimit.maxY
}

function line(startX, startY, endX, endY){
	ctx.moveTo(startX+center.x, startY+center.y)
	ctx.lineTo(endX+center.x, endY+center.y)
};


