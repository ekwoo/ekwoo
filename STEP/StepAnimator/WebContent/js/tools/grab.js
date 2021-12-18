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
	return {
		onmousedown : function(e){
			canvas.wrapEl.style.cursor = 'grabbing'
			mouseStart.x = e.clientX
			mouseStart.y = e.clientY
			transStart.x = canvas.trans.x
			transStart.y = canvas.trans.y
			window.addEventListener('mousemove', this.onmousemove)
		},
		onmousemove : function(e){
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
		},
		onmouseup : function(e){
			canvas.wrapEl.style.cursor = 'grab'
			window.removeEventListener('mousemove', this.onmousemove)
		},
	}
})()

function setGrab(){
	canvas.wrapEl.style.cursor = 'grab'
	tool = grabTool
}

