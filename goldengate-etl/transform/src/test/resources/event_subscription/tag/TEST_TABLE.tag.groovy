columneName = ['sts', 'sts_time', 'num']

def businessCheck = {sts, sts_time, num ->
	checkResult = new String[1];
	checkResult[0] = checkIsTestTag(num)
	return checkResult
}

def checkIsTestTag = {num ->
	int numValue = Integer.parseInt(num)
	if(numValue > 10){
		return "isTestTag=true";
	}else{
		return "isTestTag=false";
	}
}

tagTarget = "isTestTag"

saveTag=true