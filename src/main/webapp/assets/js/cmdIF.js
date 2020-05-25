function ifObject(ifObj){
	console.log("It's iF!")
    var criterion = (ifObj.items[0].criterions[0]);
    var key=criterion["key"];
    var value=criterion["value"];
	if(criterion["equals"]==true){
		if(variables.get(key)==value){
			console.log("equals")
			arr= arr.slice(i+1);
		    i=-1;
		    arr=ifObj.items[0].commands.concat(arr);
		}
			
	}else{
		if(variables.get(key)!=value){
			console.log("not equals")
			arr= arr.slice(i+1);
		    i=-1;
		    arr=ifObj.items[0].commands.concat(arr);
		}
	}
    
}