//캔버스 선긋기

/**
 * 선긋기
 * @param startX 시작점 x
 * @param startY 시작점 y
 * @param endX 끝점 x
 * @param endY 끝점 y
 * @returns
 */
function line(startX, startY, endX, endY){
	canvas.ctx.moveTo(startX+canvas.center.x, startY+canvas.center.y)
	canvas.ctx.lineTo(endX+canvas.center.x, endY+canvas.center.y)
};
const lineTool = (function(){
	const lineInfo = {
			startX : 0,
			startY : 0,
			endX : 0,
			endY : 0,
	}
	function onmousedown(e){
		window.addEventListener('mousemove', onmousemove)
		window.addEventListener('mouseup', onmouseup)
		lineInfo.startX = canvas.getX(e.clientX)
		lineInfo.startY = canvas.getY(e.clientY)
	}
	function onmousemove(e){
		canvas.drawCtx.clearRect(Math.min(lineInfo.startX,lineInfo.endX)-10
				,Math.min(lineInfo.startY,lineInfo.endY)-10
				,Math.abs(lineInfo.startX-lineInfo.endX)+20
				,Math.abs(lineInfo.startY-lineInfo.endY)+20)
		lineInfo.endX = canvas.getX(e.clientX)
		lineInfo.endY = canvas.getY(e.clientY)
		canvas.drawCtx.beginPath()
		canvas.drawCtx.moveTo(lineInfo.startX, lineInfo.startY)
		canvas.drawCtx.lineTo(lineInfo.endX, lineInfo.endY)
		canvas.drawCtx.closePath()
		canvas.drawCtx.stroke()
	}
	function onmouseup(e){
		canvas.drawCtx.clearRect(0, 0, canvas.drawEl.width, canvas.drawEl.height)
		canvas.ctx.beginPath()
		canvas.ctx.moveTo(lineInfo.startX, lineInfo.startY)
		canvas.ctx.lineTo(canvas.getX(e.clientX), canvas.getY(e.clientY))
		canvas.ctx.closePath()
		canvas.ctx.stroke()
		window.removeEventListener('mousemove', onmousemove)
		window.removeEventListener('mouseup', onmouseup)
	}
	return {
		onmousedown:onmousedown,
	}
})()
function setLine(){
	canvas.wrapEl.style.cursor = 'crosshair'
	tool = lineTool
}