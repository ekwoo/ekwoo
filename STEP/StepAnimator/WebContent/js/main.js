/**
 * 캔버스 자체는 글쎄..
 */
const canvas = {
		wrapEl : null,
		el : null,
		drawEl : null,
		ctx : null,
		drawCtx : null,
		center : {	//중심좌표...
			x:0,
			y:0,
		},
		fix : {		//중심을 맞추기 위한 이동
			x:0,
			y:0,
		},
		trans : {	//드래그로 이동한 거리
			x:0,
			y:0,
		},
		transLimit : {
			minX:0,
			maxX:0,
			minY:0,
			maxY:0,
		},
		getX : function(x){return x-canvas.trans.x-canvas.fix.x},	//화면좌표에서 캔버스좌표획득
		getY : function(y){return y-canvas.trans.y-canvas.fix.y},
}
//기본 드래그 툴..?
let tool = {
		onmousedown : ()=>{},
		onmouseup : ()=>{},
}

{
	//드래그 한계
	window.onload = function(){
		//캔버스 세팅
		canvas.wrapEl = document.getElementById("canvasWrap")
		canvas.el = document.getElementById("canvas")
		canvas.drawEl = document.getElementById("drawCanvas")
		canvas.ctx = canvas.el.getContext("2d")
		canvas.drawCtx = canvas.drawEl.getContext("2d")
		
		//기본 캔버스 크기는 초화면의 2배?
		canvas.el.width = canvas.drawEl.width = window.innerWidth * 2
		canvas.el.height = canvas.drawEl.height = window.innerHeight * 2
		canvas.wrapEl.style.width = canvas.el.width+"px"
		canvas.wrapEl.style.height = canvas.el.height+"px"
		canvas.wrapEl.style.left = (canvas.fix.x = -canvas.el.width/4)+"px"
		canvas.wrapEl.style.top = (canvas.fix.y = -canvas.el.height/4)+"px"
		canvas.center.x = canvas.el.width/2
		canvas.center.y = canvas.el.height/2
		
		drawAxis()
		calcDragLimit()
		
		window.onmousedown = function(e){
			tool.onmousedown(e)
		}
		window.onresize = function(){
			canvas.wrapEl.style.left = (canvas.fix.x =  -(canvas.el.width - window.innerWidth)/2)+"px"
			canvas.wrapEl.style.top = (canvas.fix.y = -(canvas.el.height - window.innerHeight)/2)+"px"
			calcDragLimit()
			//TODO: 여백이 보일시 안쪽으로 trans 조절
		}
		
		//툴박스 세팅
		let toolbox = document.getElementById("toolbox")
		toolbox.onmousedown = e => e.stopPropagation()
		
	}
	/**
	 * 십자축 그리기
	 */
	function drawAxis(){
		canvas.ctx.moveTo(0, canvas.center.y)
		canvas.ctx.lineTo(canvas.el.width, canvas.center.y)
		canvas.ctx.moveTo(canvas.center.x, 0)
		canvas.ctx.lineTo(canvas.center.x, canvas.el.height)
		canvas.ctx.stroke()
	}
	/**
	 * 캔버스 드래그 한계 설정
	 */
	function calcDragLimit(){
		canvas.transLimit.maxX = Math.max(0, (canvas.el.width - window.innerWidth)/2)
		canvas.transLimit.minX = -canvas.transLimit.maxX
		canvas.transLimit.maxY =  Math.max(0, (canvas.el.height - window.innerHeight)/2)
		canvas.transLimit.minY = -canvas.transLimit.maxY
	}
}

