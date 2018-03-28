/* 
 *  MingGe_touch v2触摸、下拉刷新插件 基于MingGeJs
 *  
 *  开发时间2017/12/22
 *
 *  作者：明哥先生-QQ2945157617 QQ群：326692453   官网：www.shearphoto.com
 * 
 *  支持IE6及所有尘世间所有浏览器
 */

!function(a,b){var c=b(a),d=function(a,b){if(a&&a[0]){var d=a[0].getBoundingClientRect();return b?[d.left,d.top]:[d.left+c.scrollLeft(),d.top+c.scrollTop()]}},e=function(a,b){var c,d,e,f,g,h,i,j=b?a.changedTouches:a.touches,k=a.button,l=typeof k,m="number"==l;return j&&!m?(c=j.length,c>0&&(d=j[0],e=d.clientX,f=d.clientY,c>1&&(g=j[1],h=g.clientX,i=g.clientY)),{X:e,Y:f,X2:h,Y2:i,touchLen:c,dev:"mob"}):m&&2>k?{X:a.clientX,Y:a.clientY,touchLen:1,dev:"pc"}:!1},f=function(){a.selection&&a.selection.empty?a.selection.empty():window.getSelection().removeAllRanges()},g=function(a){a.stopPropagation?a.stopPropagation():a.cancelBubble=!0},h=function(a,b){var h,j,l,m,n,o,p,q,r,s,t=!0,u=!1,v=!1,w=b.pc,x=(b.mob,b.isClick),y=b.isRefresh,z=!1,A=function(){u=!1},B={stop:function(){t=!0,a.unbind(this.touchstart),c.unbind(this.touchmove),c.unbind(this.touchend),delete this.touchstart,delete this.touchmove,delete this.touchend,delete this.stop},pause:function(){v?v=!1:(t=v=!0,u=!1)},touchstart:function(c){var f,g,i;if(!v&&(z="fixed"==a.css("position"),f=e(c))){if(w)if("pc"==f.dev){if(u)return}else x&&(u=!0);h=f.X,j=f.Y,l=f.X2,m=f.Y2,t=!1,a[0].setCapture&&a[0].setCapture(),g=d(a,z),n=g[0],o=g[1],p=h-n,q=j-o,b.down&&(i=b.down.call(a,c,C,{X:h,Y:j,X2:l,Y2:m,left:n,top:o,disL:p,disT:q,touchLen:f.touchLen,dev:f.dev,isdown:!0}),x&&i!==!1||c.preventDefault())}},touchmove:function(c){var d,k,n;t||(d=e(c),w&&u&&"pc"==d.dev||(k=i(b.container),y?(r=(d.X-h)*y,s=(d.Y-j)*y):(r=d.X-p-b.offsetLeft-k[0],s=d.Y-q-b.offsetTop-k[1]),b.over&&(n=b.over.call(a,c,C,{X:d.X,Y:d.Y,downX:h,downY:j,X2:d.X2,Y2:d.Y2,downX2:l,downY2:m,left:r,top:s,disL:p,disT:q,touchLen:d.touchLen,dev:d.dev}),"pc"==d.dev&&f(),g(c),n===!1&&c.preventDefault())))},touchend:function(c){var d,f;if(!t){if(t=!0,d=e(c,!0),w)if("pc"==d.dev){if(u)return}else setTimeout(A,350);a[0].releaseCapture&&a[0].releaseCapture(),b.up&&(f=b.up.call(a,c,C,{X:d.X,Y:d.Y,X2:d.X2,Y2:d.Y2,downX:h,downY:j,downX2:l,downY2:m,disL:p,disT:q,left:r,top:s,touchLen:d.touchLen,dev:d.dev,isup:!0}),g(c),f===!1&&c.preventDefault())}}},C=new k(B);return B},i=function(a){return d(a)||[0,0]},j=function(a,b){for(var c,d=a.length,e=0;d>e;e++)c=a[e],c[b]&&c[b]()},k=function(a){this._OBJ_=b.isArray(a)?a:[a]};k.prototype={stop:function(){j(this._OBJ_,"stop")},pause:function(){j(this._OBJ_,"pause")}},b.fn.extend({refresh:function(a){a=b.extend({pc:!0,mobile:!0,maxX:300,maxY:300,ratio:.4,container:null,callback:null,up:null},a),b.isFunction(a.callback)||(a.callback=0),b.isFunction(a.up)||(a.up=0),b.isNumber(a.ratio)&&a.ratio<1&&a.ratio>0||(a.ratio=.4);var c=function(c,d,e){return a.callback&&a.callback.call(this,c,{X:e.left<(b.isNumber(a.maxX)?a.maxX:a.maxX())?0:1,Y:e.top<(b.isNumber(a.maxY)?a.maxY:a.maxY())?0:1,isup:!!e.isup,isdown:!!e.isdown},e),!1};return this.touch({mobile:a.mobile,pc:a.pc,isRefresh:a.ratio,container:a.container,down:c,up:c,over:c})},touch:function(a){var d,e;return a=b.extend({down:0,up:0,container:null,offsetLeft:0,pc:!0,mobile:!0,offsetTop:0,isClick:!0,isRefresh:!1,over:0},a),b.isFunction(a.over)||(a.over=0),b.isFunction(a.up)||(a.up=0),b.isFunction(a.down)||(a.down=0),a.offsetLeft=Number(a.offsetLeft)||0,a.offsetTop=Number(a.offsetTop)||0,d=[],this.each(function(){var e=b(this),f=h(e,a),g={},i={};a.pc&&(g.mousedown=f.touchstart,i.mousemove=f.touchmove,i.mouseup=f.touchend),a.mobile&&(g.touchstart=f.touchstart,i.touchmove=f.touchmove,i.touchend=f.touchend,i.touchcancel=f.touchend),e.bind(g),c.bind(i),d.push(f)}),e=new k(d)}})}(document,MingGe);