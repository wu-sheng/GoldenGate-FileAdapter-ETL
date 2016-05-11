import com.ai.edc.etl.transform.group.TagJudge

subscribeTag = ["isTestTag"]
subscribeData = ["num"]

def groupNum = {isTestTag, num ->
	if(TagJudge.isN2Y(isTestTag)){
		return Long.parseLong(num);
	}else if(TagJudge.isY2N(isTestTag)){
		return 0 - Long.parseLong(num);
	}else{
		return 0L
	}
}

def group = {isTestTag, num ->
	groupResult = new String[1];
	groupResult[0] = "numSum=" + groupNum(isTestTag, num)
	return groupResult
}