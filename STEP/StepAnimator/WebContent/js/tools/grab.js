//기본 캔버스 드래그 툴..?
const grabTool = (function(){
	const mouseStart = {
		x:0,
		y:0,
	}
	const transStart = {
		x:0,
		y:0,
	}
	function onmousedown(e){
		canvas.wrapEl.style.cursor = 'grabbing'
		mouseStart.x = e.clientX
		mouseStart.y = e.clientY
		transStart.x = canvas.trans.x
		transStart.y = canvas.trans.y
		window.addEventListener('mousemove', onmousemove)
		window.addEventListener('mouseup', onmouseup)
	}
	function onmousemove(e){
		canvas.trans.x = transStart.x + e.clientX - mouseStart.x
		canvas.trans.y = transStart.y + e.clientY - mouseStart.y
		if(canvas.trans.x < canvas.transLimit.minX){
			mouseStart.x -= (canvas.transLimit.minX - canvas.trans.x)
			canvas.trans.x = canvas.transLimit.minX
		}
		if(canvas.transLimit.maxX < canvas.trans.x){
			mouseStart.x += (canvas.trans.x - canvas.transLimit.maxX)
			canvas.trans.x = canvas.transLimit.maxX
		}
		if(canvas.trans.y < canvas.transLimit.minY){
			mouseStart.y -= (canvas.transLimit.minY - canvas.trans.y)
			canvas.trans.y = canvas.transLimit.minY
		}
		if(canvas.transLimit.maxY < canvas.trans.y){
			mouseStart.y += (canvas.trans.y - canvas.transLimit.maxY)
			canvas.trans.y = canvas.transLimit.maxY
		}
		canvas.wrapEl.style.transform = 'translate('+canvas.trans.x+'px, '+canvas.trans.y+'px)'
	}
	function onmouseup(e){
		console.log('aaa')
		canvas.wrapEl.style.cursor = 'grab'
		window.removeEventListener('mousemove', onmousemove)
		window.removeEventListener('mouseup', onmouseup)
	}
	return {
		onmousedown : onmousedown
	}
})()

function setGrab(){
	canvas.wrapEl.style.cursor = 'grab'
	tool = grabTool
}

