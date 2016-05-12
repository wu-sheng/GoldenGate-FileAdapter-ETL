import com.ai.edc.etl.transform.group.TagJudge

subscribeTag = ["isTestTag"]
subscribeData = ["num"]
subscribeOldData = true

def groupNum = {isTestTag, num ->
	if(TagJudge.isN2Y(isTestTag)){
		return Long.parseLong(num);
	}else if(TagJudge.isY2N(isTestTag)){
		return 0 - Long.parseLong(num);
	}else{
		return 0L
	}
}

def group = {isTestTag, num, old_num ->
	groupResult = new String[1];
	if("".equals(num)){
		long oldValue = 0L - Long.parseLong(old_num);
		groupResult[0] = "numSum=" + oldValue
	}else{
		groupResult[0] = "numSum=" + groupNum(isTestTag, num)
	}
	return groupResult
}