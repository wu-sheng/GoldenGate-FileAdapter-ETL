columneName = ['sts', 'sts_time', 'num']

def businessCheck = {sts, sts_time, num ->
	int numValue = Integer.parseInt(num)
	if(numValue > 10){
		return true;
	}else{
		return false;
	}
}

tagTarget = "isTestTag"

saveTag=true