columneName = ['sts', 'sts_time', 'num']

def checkIsTestTag = {num ->
	if("".equals(num)){
		return "isTestTag=false"
	}
	int numValue = Integer.parseInt(num)
	if(numValue > 10){
		return "isTestTag=true";
	}else{
		return "isTestTag=false";
	}
}

def businessCheck = {sts, sts_time, num ->
	checkResult = new String[1];
	checkResult[0] = checkIsTestTag(num)
	return checkResult
}

saveTag=true