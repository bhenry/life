(function(){var a,b,c,d,e=[].slice;d=function(a,b,c){var d,f,g,h,i,j,k,l,m,n,o,p,q,r,s;for(l=function(a){return a.length>0},f=function(a){if(!(a instanceof jQuery||a instanceof Array))throw new Error("Value must be either a jQuery object or an Array of jQuery objects")},d=function(a){var b,d,e,f;if(a instanceof jQuery)return a;for(d=c(),e=0,f=a.length;f>e;e++)b=a[e],b instanceof jQuery&&(d=d.add(b));return d},m={indexOf:Array.prototype.indexOf?function(a,b){return a.indexOf(b)}:function(a,b){var c,d,e,f;for(c=e=0,f=a.length;f>e;c=++e)if(d=a[c],b===d)return c;return-1}},a.$.Model=a.Model,c.fn.asEventStream=a.$.asEventStream,a.$.textFieldValue=function(b,c){var d,e,f;return f=function(){return b.val()||""},d=function(){return a.interval(50).take(10).map(f).filter(l).take(1)},e=b.asEventStream("keyup input").merge(b.asEventStream("cut paste").delay(1)).merge(d()),a.Binding({initValue:c,get:f,events:e,set:function(a){return b.val(a)}})},a.$.checkBoxValue=function(b,c){return a.Binding({initValue:c,get:function(){return b.prop("checked")||!1},events:b.asEventStream("change"),set:function(a){return b.prop("checked",a)}})},a.$.selectValue=function(b,c){return a.Binding({initValue:c,get:function(){return b.val()},events:b.asEventStream("change"),set:function(a){return b.val(a)}})},a.$.radioGroupValue=function(b,e){return f(b),b=d(b),a.Binding({initValue:e,get:function(){return b.filter(":checked").first().val()},events:b.asEventStream("change"),set:function(a){return b.each(function(b,d){return c(d).prop("checked",d.value===a)})}})},a.$.intRadioGroupValue=function(b,c){var d;return d=a.$.radioGroupValue(b),a.Binding({initValue:c,get:function(){var a;return a=d.get(),a?parseInt(a):a},events:d.syncEvents(),set:function(a){var b;return b=a?Number(a).toString():a,d.set(b)}})},a.$.checkBoxGroupValue=function(b,e){return f(b),b=d(b),a.Binding({initValue:e,get:function(){return b.filter(":checked").map(function(a,b){return c(b).val()}).toArray()},events:b.asEventStream("change"),set:function(a){return b.each(function(b,d){return c(d).prop("checked",m.indexOf(a,c(d).val())>=0)})}})},a.$.ajax=function(b,d){return a.fromPromise(c.ajax(b),d)},a.$.ajaxGet=function(b,c,d,e){return a.$.ajax({url:b,dataType:d,data:c},e)},a.$.ajaxGetJSON=function(b,c,d){return a.$.ajax({url:b,dataType:"json",data:c},d)},a.$.ajaxPost=function(b,c,d,e){return a.$.ajax({url:b,dataType:d,data:c,type:"POST"},e)},a.$.ajaxGetScript=function(b,c){return a.$.ajax({url:b,dataType:"script"},c)},a.$.lazyAjax=function(b){return a.once(b).flatMap(a.$.ajax)},a.Observable.prototype.ajax=function(){return this.flatMapLatest(a.$.ajax)},a.Observable.prototype.toDeferred=function(){var a,b;return b=void 0,a=c.Deferred(),this.take(1).endOnError().subscribe(function(c){return c.hasValue()?(b=c.value(),a.notify(b)):c.isError()?a.reject(c.error):c.isEnd()?a.resolve(b):void 0}),a},j=["keydown","keyup","keypress","click","dblclick","mousedown","mouseup","mouseenter","mouseleave","mousemove","mouseout","mouseover","resize","scroll","select","change","submit","blur","focus","focusin","focusout","load","unload"],k={},n=function(a){return k[a+"E"]=function(){var b;return b=1<=arguments.length?e.call(arguments,0):[],this.asEventStream.apply(this,[a].concat(e.call(b)))}},p=0,r=j.length;r>p;p++)g=j[p],n(g);for(c.fn.extend(k),h=["animate","show","hide","toggle","fadeIn","fadeOut","fadeTo","fadeToggle","slideDown","slideUp","slideToggle"],i={},o=function(b){return i[b+"E"]=function(){var c;return c=1<=arguments.length?e.call(arguments,0):[],a.fromPromise(this[b].apply(this,c).promise())}},q=0,s=h.length;s>q;q++)g=h[q],o(g);return c.fn.extend(i),a.$},"undefined"!=typeof module&&null!==module?(b=require("baconjs"),c=require("bacon.model"),a=require("jquery"),module.exports=d(b,c,a)):"function"==typeof define&&define.amd?define(["bacon","bacon.model","jquery"],d):d(this.Bacon,this.BaconModel,this.$)}).call(this);