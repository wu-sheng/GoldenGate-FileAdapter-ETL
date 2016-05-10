subscribeTag = ["isTestTag"]
subscribeData = ["num"]

def group = {isTestTag, num ->
	groupResult = new String[1];
	groupResult[0] = "numSum=" + groupNum(num)
	return checkResult
}

def groupNum = {isTestTag, num ->
	String[] oldNewTag = TagAssist.getChangeFlow(isTestTag);
	String olgTag = oldNewTag[0]
	String newTag = oldNewTag[1]
	
	if("Y".equals(olgTag) && "N".equals(olgTag)){
		return Long.parseLong(num);
	}else if("Y".equals(olgTag) && "N".equals(olgTag)){
		return 0 - Long.parseLong(num);
	}else{
		return 0L
	}
}
