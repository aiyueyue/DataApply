//�������뱣��С������λ
	 function toDecimal(x) {  
         var f = parseFloat(x);  
         if (isNaN(f)) {  
             return;  
         }  
         f = Math.round(x*100)/100;  
         return f;  
     }  
	 
	//�������뱣��С����Nλ 
	 function round(v,e){

			var t=1;

			for(;e>0;t*=10,e--);

			for(;e<0;t/=10,e++);

			return Math.round(v*t)/t;

			}
		 
	 
	 
	 // ����
	 //top�ȴ�ͼ�������Ļ�Ķ�����λ��
	//left�ȴ�ͼ�������Ļ���ұߵ�λ��
     function lock (top,left)
   {
       var imgPath = "../../js/klmap/img/WaitProcess.gif";				
       var sWidth  = 120;								
      	var sHeight = 30;									
       
       var bgObj=document.createElement("div");
       bgObj.setAttribute("id","divLock");
       bgObj.style.position="absolute";
      // bgObj.style.top="400";
      // bgObj.style.background="#F4F3F0"; background-color: rgb(244, 243, 240)					
       //bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";
       bgObj.style.opacity="0.6";
       bgObj.style.top=top;								
       bgObj.style.left=left;								
       bgObj.style.width=sWidth + "px";				
       bgObj.style.height=sHeight + "px";				
       bgObj.style.zIndex = "100000";							
       // ������ʾ��gif����
       var html = "<table border='0' width='"+sWidth+"' height='"+sHeight+"'> " +
       				" <tr><td valign='middle' align='center'> " +
       					" <image src='"+imgPath+"'> " +
       				" </td></tr> " +
       			"</table>";
       bgObj.innerHTML = html;
       document.body.appendChild(bgObj);
   };
   // ����
   function unlock()
   {
       var divLock = document.getElementById("divLock");
       if(divLock == null) return;
       document.body.removeChild(divLock);
   };
   
   
   //ΪIE���placeholder֧�ֹ���   
   function placeholder(){
       var doc=document,inputs=doc.getElementsByTagName('input'),supportPlaceholder='placeholder'in doc.createElement('input'),placeholder=function(input){var text=input.getAttribute('placeholder'),defaultValue=input.defaultValue;
       if(defaultValue==''){
           input.value=text}
           input.onfocus=function(){
               if(input.value===text){this.value=''}};
               input.onblur=function(){if(input.value===''){this.value=text}}};
               if(!supportPlaceholder){
                   for(var i=0,len=inputs.length;i<len;i++){var input=inputs[i],text=input.getAttribute('placeholder');
                   if(input.type==='text'&&text){placeholder(input)}}};
  }
   
   
   //js��ȡϵͳʱ��
   function sysTime(){
	   var myDate=new Date();   
		var s=myDate.getFullYear(); 
		var m= myDate.getMonth()+1; //��ȡ��ǰ�·�(0-11,0����1��) 
		var mm=  myDate.getDate(); //��ȡ��ǰ��(1-31) 
		var ss= myDate.getHours();       //��ȡ��ǰСʱ��(0-23)
		var ssss=myDate.getMinutes();     //��ȡ��ǰ������(0-59)
		var sssss=myDate.getSeconds();     //��ȡ��ǰ����(0-59)
		var h=s+"-"+m+"-"+mm+" "+ss+":"+ssss+":"+sssss+""
		var hh="����ʱ�䣺"+h;
		return hh;
   }
	/*����DIV��С*/
	function resizeDiv(widthOffset,mapDivId){
		var width,height;
		width = jQuery("body").width()-widthOffset;
		height = jQuery("body").height();
		jQuery("#"+mapDivId).width(width);
		jQuery("#"+mapDivId).height(height);
	}
   